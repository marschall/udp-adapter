package com.github.marschall.udpadapter;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Connector
public class UdpAdapter implements ResourceAdapter {
  
  static final Logger LOG = Logger.getLogger(UdpAdapter.class.getPackage().getName());
  
  @Size(min = 1, max = 0xFFFF)
  @ConfigProperty(description = "The port number on which to listen for UPD messages.")
  private Integer listenPort;
  
  @Size(min = 0, max = 65507)
  @ConfigProperty(defaultValue = "65507",
    description = "The maximum length of datagramm bodies. Setting this to the correct value can help reducing memory usage a great bit.")
  // TODO IPv6 Jumbograms?
  @NotNull
  private int dataLength;
  
  @Size(min = 1, max = 0xFFFF)
  @ConfigProperty(defaultValue = "64",
    description = "The maximum size of the receive datagram pool.")
  private int receiveDatagramPoolSize;
  
  @Size(min = 1, max = 0xFFFF)
  @ConfigProperty(defaultValue = "64",
  description = "The maximum size of the send datagram pool.")
  private Integer sendDatagramPoolSize;
  
  // TODO
//  @Size(min = 1, max = 0xFFFF)
//  @ConfigProperty(defaultValue = "1000",
//  description = "The socket timeout when waiting for UDP datagrams.")
//  private Integer socketTimeout;

  private volatile WorkManager workManager;

  private volatile DatagramSocket sendSocket;

  private volatile MessagePool sendPool;

  private volatile SocketMessageSender messageSender;

  private volatile Listener listener;
  
  public UdpAdapter() {
    super();
  }

  @Override
  public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
    workManager = ctx.getWorkManager();
    
    if (this.sendDatagramPoolSize != null) {
      this.startSender();
    }
    
    if (this.listenPort != null) {
      this.startListener();
    }
    
    LOG.fine("started");
  }

  @Override
  public void stop() {
    // TODO close socket
    this.workManager = null;
    if (this.sendSocket != null) {
      this.sendSocket.close();
      this.sendSocket = null;
      this.messageSender = null;
    }
    if (this.listener != null) {
      this.listener.release();
    }
    LOG.fine("stopped");
  }

  @Override
  public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
    // spec section 5.3.3
    if (!this.equals(spec.getResourceAdapter())) {
      throw new ResourceException("Activation spec not initialized with this ResourceAdapter instance (" + spec.getResourceAdapter() + " != " + this + ")");
    }
    this.listener.addMessageEndpointFactory(endpointFactory);
    LOG.fine("endpointActivation");
  }
  

  @Override
  public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
    this.listener.removeMessageEndpointFactory(endpointFactory);
    LOG.fine("endpointDeactivation");
  }
  

  private void startSender() throws ResourceAdapterInternalException {
    UdpConfiguration configuration = new UdpConfiguration(-1, dataLength, this.sendDatagramPoolSize);
    try {
      this.sendSocket = new DatagramSocket();
      this.messageSender = new SocketMessageSender(this.sendSocket);
    } catch (SocketException e) {
      throw new ResourceAdapterInternalException("could not send socket", e);
    }
    this.sendPool = new MessagePool(configuration);
  }
  
  MessageSender getMessageSender() {
    return this.messageSender;
  }
  
  MessageSender getReplyMessageSender() {
    return this.listener;
  }
  
  private void startListener() throws ResourceAdapterInternalException {
    UdpConfiguration configuration = new UdpConfiguration(this.listenPort, this.dataLength, this.receiveDatagramPoolSize);
    
    try {
      this.listener = new Listener(this.workManager, configuration);
    } catch (SocketException e) {
      throw new ResourceAdapterInternalException("could not create receive socket", e);
    }

    try {
      this.listener.configureSocket();
    } catch (SocketException e) {
      throw new ResourceAdapterInternalException("could not configure socket", e);
    }

    try {
      this.workManager.scheduleWork(this.listener);
    } catch (WorkException e) {
      throw new ResourceAdapterInternalException("could not start listen loop", e);
    }
  }
  
  public Integer getListenPort() {
    return this.listenPort;
  }

  public void setListenPort(Integer port) {
    this.listenPort = port;
  }

  public Integer getDataLength() {
    return this.dataLength;
  }

  public void setDataLength(Integer dataLength) {
    this.dataLength = dataLength;
  }

  public void setReceiveDatagramPoolSize(Integer datagramPoolSize) {
    this.receiveDatagramPoolSize = datagramPoolSize;
  }
  
  public Integer getReceiveDatagramPoolSize() {
    return this.receiveDatagramPoolSize;
  }

  public void setSendDatagramPoolSize(Integer datagramPoolSize) {
    this.sendDatagramPoolSize = datagramPoolSize;
  }
  
  public Integer getSendDatagramPoolSize() {
    return this.sendDatagramPoolSize;
  }

  @Override
  public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException {
    // spec says it's OK to return null if XA not supported
    return null;
  }
  
  @Override
  public boolean equals(Object obj) {
    // spec requires this
    return super.equals(obj);
  }
  
  @Override
  public int hashCode() {
    // spec requires this
    return super.hashCode();
  }
  
}
