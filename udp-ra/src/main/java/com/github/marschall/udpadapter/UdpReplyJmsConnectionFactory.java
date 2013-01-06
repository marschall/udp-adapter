package com.github.marschall.udpadapter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionFactory;

public class UdpReplyJmsConnectionFactory implements ConnectionFactory {
  
  private final ConnectionManager connectionManager;
  private final ManagedConnectionFactory connectionFactory;
  
  
  UdpReplyJmsConnectionFactory(ConnectionManager connectionManager, ManagedConnectionFactory connectionFactory) {
    this.connectionManager = connectionManager;
    this.connectionFactory = connectionFactory;
  }

  @Override
  public Connection createConnection() throws JMSException {
    Object connection;
    try {
      connection = this.connectionManager.allocateConnection(this.connectionFactory, new EmptyConnectionRequestInfo());
    } catch (ResourceException e) {
      // TODO cause
      throw new JMSException("could not allocate connection");
    }
    return (Connection) connection;
  }

  @Override
  public Connection createConnection(String userName, String password) throws JMSException {
    return this.createConnection();
  }
  
  static final class EmptyConnectionRequestInfo implements ConnectionRequestInfo {
    
  }

}
