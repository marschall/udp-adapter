package com.github.marschall.udpadapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.resource.ResourceException;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;

final class Listener implements Work {

  private volatile boolean cancel = false;
  
  private final MessageEndpointFactory endpointFactory;
  
  private static final Method ON_MESSAGE_METHOD;

  private final UdpActivationSpec spec;

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
  
  Listener(WorkManager workManager, MessageEndpointFactory endpointFactory, UdpActivationSpec spec) throws SocketException {
    this.workManager = workManager;
    this.endpointFactory = endpointFactory;
    this.spec = spec;
    this.pool = new MessagePool(spec);
    this.socket = new DatagramSocket(this.spec.getPort());
  }
  
  void configureSocket() throws SocketException {
    this.socket.setSoTimeout((int) TimeUnit.SECONDS.toMillis(1L));
    // TODO bind?
  }

  @Override
  public void run() {
    if (!this.cancel) {
      DatagramIncommingMessage message = this.pool.getMessage();
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
  
  private void dispatch(DatagramIncommingMessage message) {
    // dispatch in same thread to avoid context switch
    MessageEndpoint endpoint;
    try {
      endpoint = this.endpointFactory.createEndpoint(null);
    } catch (UnavailableException e) {
      UdpAdapter.LOG.log(Level.SEVERE, "endpoint creation failed", e);
      return;
    }
    try {
      endpoint.beforeDelivery(ON_MESSAGE_METHOD);
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
    // covered by spec?
    this.cancel = true;
    this.socket.close();
    this.pool.clear();
  }


}
