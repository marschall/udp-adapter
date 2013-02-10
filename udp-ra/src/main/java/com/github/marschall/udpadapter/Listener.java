package com.github.marschall.udpadapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.resource.ResourceException;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;

final class Listener implements Work, MessageSender {

  private static final ClassLoader THIS_CLASS_LOADER = Listener.class.getClassLoader();

  private static final Class<?>[] PROXY_CLASSES = new Class<?>[]{BytesMessage.class, StreamMessage.class, ObjectMessage.class};

  private volatile boolean cancel = false;
  
  private final List<MessageEndpointFactory> endpointFactories;
  
  private static final Method ON_MESSAGE_METHOD;

  private final UdpConfiguration configuration;

  private final MessagePool pool;

  private final WorkManager workManager;

  private final DatagramSocket socket;

  static {
    try {
      ON_MESSAGE_METHOD = MessageListener.class.getMethod("onMessage", Message.class);
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }
  
  Listener(WorkManager workManager, UdpConfiguration configuration) throws SocketException {
    this.workManager = workManager;
    this.configuration = configuration;
    this.endpointFactories = new CopyOnWriteArrayList<>();
    this.pool = new MessagePool(configuration);
    this.socket = new DatagramSocket(this.configuration.port);
  }
  
  void configureSocket() throws SocketException {
    this.socket.setSoTimeout((int) TimeUnit.SECONDS.toMillis(1L));
    // TODO bind?
  }
  
  void addMessageEndpointFactory(MessageEndpointFactory endpointFactory) {
    this.endpointFactories.add(endpointFactory);
  }
  
  void removeMessageEndpointFactory(MessageEndpointFactory endpointFactory) {
    boolean changed = this.endpointFactories.remove(endpointFactory);
    if (!changed) {
      UdpAdapter.LOG.warning("message endpoint factory: " + endpointFactory + " was not registered");
    }
  }

  @Override
  public void run() {
    if (!this.cancel) {
      DatagramMessage message = this.pool.getMessage();
      boolean dispatch = false;
      while (!dispatch) {
        try {
          this.socket.receive(message.getPacket());
          dispatch = true;
        } catch (SocketTimeoutException e) {
          // normal
        } catch (IOException e) {
          UdpAdapter.LOG.log(Level.SEVERE, "reading failed", e);
          this.release();
          return;
        }
      }
      this.dispatch(message);
    }
    this.redispatch();
  }
  
  private void redispatch() {
    try {
      if (!this.cancel) {
        this.workManager.scheduleWork(this);
      }
    } catch (WorkException e) {
      UdpAdapter.LOG.log(Level.SEVERE, "redispatching failed", e);
      this.release();
    }
  }
  
  @Override
  public void sendMessage(DatagramMessage message) throws JMSException {
    try {
      message.syncPosition();
      DatagramPacket packet = message.getPacket();
      this.socket.send(packet);
    } catch (IOException e) {
      JMSException jmsException = new JMSException("could not send message");
      jmsException.setLinkedException(jmsException);
      throw jmsException;
    }
  }
  
  private void dispatch(DatagramMessage message) {
    for (MessageEndpointFactory endpointFactory : this.endpointFactories) {
      this.dispatch(message, endpointFactory);
    }
  }
  
  private void dispatch(DatagramMessage message, MessageEndpointFactory endpointFactory) {
    // TODO JMSXDeliveryCount
    // TODO redelivery
    // dispatch in same thread to avoid context switch
    MessageEndpoint endpoint;
    try {
      endpoint = endpointFactory.createEndpoint(null);
    } catch (UnavailableException e) {
      UdpAdapter.LOG.log(Level.SEVERE, "endpoint creation failed", e);
      return;
    }
    try {
      endpoint.beforeDelivery(ON_MESSAGE_METHOD);
      if (endpoint instanceof MessageListener) {
        final MessageListener listener = (MessageListener) endpoint;
        // TODO cache
        ReadOnlyMessageWrapper wrapper = new ReadOnlyMessageWrapper(message);
        MessageInvalidator invalidator = new MessageInvalidator(wrapper);
        final Message proxy = (Message) Proxy.newProxyInstance(THIS_CLASS_LOADER, PROXY_CLASSES, invalidator);
        try {
          CurrentDatagramMessage.useDuring(message, new Runnable() {
            @Override
            public void run() {
              listener.onMessage(proxy);
            }
          });
        } finally {
          invalidator.invalidate();
        }
      }
      endpoint.afterDelivery();
    } catch (ResourceException | NoSuchMethodException | RuntimeException e) {
      UdpAdapter.LOG.log(Level.SEVERE, "message deliver failed", e);
    } finally {
      endpoint.release();
    }
  }

  @Override
  public void release() {
    // TODO remove from adapter when called by container?
    // covered by configuration?
    this.cancel = true;
    this.socket.close();
    this.pool.clear();
  }


}
