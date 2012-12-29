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
	
	@Size(min = 0, max = 65507)
	@ConfigProperty(defaultValue = "65507",
	  description = "Maximum length of datagramm bodies. Setting this to the correct value can help reducing memory usage a great bit.")
	// TODO IPv6 Jumbograms?
	private int dataLength;
	
	@Size(min = 1, max = 0xFFFF)
	@ConfigProperty(defaultValue = "64",
	description = "Maximum size of the datagram pool.")
	private int datagramPoolSize;
	
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
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getDataLength() {
    return this.dataLength;
  }

  public void setDataLength(int dataLength) {
    this.dataLength = dataLength;
  }

  public int getDatagramPoolSize() {
    return datagramPoolSize;
  }

  public void setDatagramPoolSize(int datagramPoolSize) {
    this.datagramPoolSize = datagramPoolSize;
  }
  
}
