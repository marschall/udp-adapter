package com.github.marschall.udpadapter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
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
      JMSException jmsException = new JMSException("could not allocate connection");
      jmsException.setLinkedException(e);
      throw jmsException;
    }
    return (Connection) connection;
  }

  @Override
  public Connection createConnection(String userName, String password) throws JMSException {
    return this.createConnection();
  }
  
  @Override
  public JMSContext createContext() {
    Object connection;
    try {
      connection = this.connectionManager.allocateConnection(this.connectionFactory, new EmptyConnectionRequestInfo());
    } catch (ResourceException e) {
      throw new JMSRuntimeException("could not allocate connection", null, e);
    }
    return (JMSContext) connection;
  }
  
  @Override
  public JMSContext createContext(String userName, String password) {
    return createContext();
  }

  @Override
  public JMSContext createContext(String userName, String password, int sessionMode) {
    return createContext();
  }

  @Override
  public JMSContext createContext(int sessionMode) {
    return createContext();
  }


  static final class EmptyConnectionRequestInfo implements ConnectionRequestInfo {
    
  }

}
