package com.github.marschall.udpadapter;

import javax.jms.MessageListener;
import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.validation.constraints.Size;

@Activation(messageListeners = MessageListener.class)
public class UdpActivationSpec implements ActivationSpec {
	
	@Size(min = 1, max = 0xFFFF)
	@ConfigProperty
	private int port;
	
  private ResourceAdapter ra;

  @Override
  public ResourceAdapter getResourceAdapter() {
    return this.ra;
  }

  @Override
  public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
    this.ra = ra;
    
  }

  @Override
  public void validate() throws InvalidPropertyException {
    // TODO Auto-generated method stub
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
  
  

}
