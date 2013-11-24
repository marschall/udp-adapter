package com.github.marschall.udpadapter;

import javax.jms.ConnectionMetaData;
import javax.jms.JMSContext;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;

abstract class UdpJmsContext implements JMSContext, ResourceAdapterAssociation {

  private volatile String clientID;

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
