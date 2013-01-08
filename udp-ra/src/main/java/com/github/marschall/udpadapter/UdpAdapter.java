package com.github.marschall.udpadapter;

import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
    description = "The maximum size of the datagram pool.")
  @NotNull
  private int datagramPoolSize;

  private volatile WorkManager workManager;
  
  private ConcurrentMap<ActivationSpec, Listener> activations;
  
  public UdpAdapter() {
    this.activations = new ConcurrentHashMap<>();
  }

  @Override
  public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
    workManager = ctx.getWorkManager();
    LOG.fine("started");
  }

  @Override
  public void stop() {
    workManager = null;
    LOG.fine("stopped");
  }

  @Override
  public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
    // spec section 5.3.3
    if (!this.equals(spec.getResourceAdapter())) {
      throw new ResourceException("Activation spec not initialized with this ResourceAdapter instance (" + spec.getResourceAdapter() + " != " + this + ")");
    }

    if (this.listenPort != null) {
      this.startListener(endpointFactory, spec);
    }
    LOG.fine("endpointActivation");
  }

  void startListener(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException, WorkException {
    UdpConfiguration configuration = new UdpConfiguration(this.listenPort, this.dataLength, this.datagramPoolSize);
    Listener listener;
    try {
      listener = new Listener(this.workManager, endpointFactory, configuration);
    } catch (SocketException e) {
      throw new ResourceException("could not create socket", e);
    }
    
    try {
      listener.configureSocket();
    } catch (SocketException e) {
      throw new ResourceException("could not configure socket", e);
    }
    
    this.workManager.scheduleWork(listener);
    this.activations.put(spec, listener);
  }

  @Override
  public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
    Listener listener = this.activations.get(spec);
    if (listener != null) {
      listener.release();
    }
    LOG.fine("endpointDeactivation");
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

  public Integer getDatagramPoolSize() {
    return datagramPoolSize;
  }

  public void setDatagramPoolSize(Integer datagramPoolSize) {
    this.datagramPoolSize = datagramPoolSize;
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
