package com.github.marschall.udpadapter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;

@Connector
public class UdpAdapter implements ResourceAdapter {

  private volatile WorkManager workManager;
  
  private ConcurrentMap<ActivationSpec, Listener> activations;
  
  public UdpAdapter() {
    this.activations = new ConcurrentHashMap<>();
  }

  @Override
  public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
    workManager = ctx.getWorkManager();
  }

  @Override
  public void stop() {
    workManager = null;
  }

  @Override
  public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
    // spec section 5.3.3
    if (!this.equals(spec.getResourceAdapter())) {
      throw new ResourceException("Activation spec not initialized with this ResourceAdapter instance (" + spec.getResourceAdapter() + " != " + this + ")");
    }

    if (!(spec instanceof UdpActivationSpec)) {
      throw new NotSupportedException("That type of ActivationSpec not supported: " + spec.getClass());
    }
    Listener listener = new Listener(endpointFactory);
    this.workManager.scheduleWork(listener);
    this.activations.put(spec, listener);
  }

  @Override
  public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
    Listener listener = this.activations.get(spec);
    if (listener != null) {
      listener.release();
    }

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
