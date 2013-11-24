package com.github.marschall.udpadapter;

import javax.jms.ConnectionMetaData;
import javax.jms.ExceptionListener;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;

abstract class UdpJmsContext implements JMSContext, ResourceAdapterAssociation {

  private volatile String clientID;
  
  private volatile boolean autoStart;
  
  UdpJmsContext() {
    this.autoStart = true;
  }

  private volatile ResourceAdapter ra;
  
  @Override
  public void setClientID(String clientID) {
    this.clientID = clientID;
  }
  
  @Override
  public String getClientID() {
    return this.clientID;
  }
  
  @Override
  public ConnectionMetaData getMetaData() {
    return UdpConnectionMetaData.INSTANCE;
  }
  
  @Override
  public ExceptionListener getExceptionListener() {
    return null;
  }
  
  @Override
  public void setExceptionListener(ExceptionListener listener) {
    throw new JMSRuntimeException("setExceptionListener not allowed");
    // TODO IllegalStateRuntimeException
  }

  @Override
  public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
    if (!(ra instanceof UdpAdapter)) {
      throw new ResourceException("unsupported resource adatper type: " + ra);
    }
    this.setResourceAdapter((UdpAdapter) ra);
    this.ra = ra;
  }
  
  @Override
  public ResourceAdapter getResourceAdapter() {
    return this.ra;
  }
  
  abstract void setResourceAdapter(UdpAdapter ra);

}
