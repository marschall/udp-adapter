package com.github.marschall.udpadapter;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;

public abstract class UdpJmsConnection implements Connection, ResourceAdapterAssociation {

  private volatile String clientID;
  volatile MessageSender sender;
  private volatile ResourceAdapter ra;

  @Override
  public String getClientID() throws JMSException {
    return this.clientID;
  }

  @Override
  public void setClientID(String clientID) throws JMSException {
    this.clientID = clientID;
  }

  @Override
  public ConnectionMetaData getMetaData() throws JMSException {
    return UdpConnectionMetaData.INSTANCE;
  }

  @Override
  public ExceptionListener getExceptionListener() throws JMSException {
    return null;
  }

  @Override
  public void setExceptionListener(ExceptionListener listener) throws JMSException {
    throw new JMSException("setExceptionListener not allowed");
  }
  
  @Override
  public Session createSession() throws JMSException {
    return this.createSession(true, Session.AUTO_ACKNOWLEDGE);
  }
  
  @Override
  public Session createSession(int sessionMode) throws JMSException {
    // TODO disallow Session.TRANSACTED and Session.CLIENT_ACKNOWLEDGE?
    return this.createSession(false, Session.AUTO_ACKNOWLEDGE);
  }

  @Override
  public void start() throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public void stop() throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector,
      ServerSessionPool sessionPool, int maxMessages) throws JMSException {
    throw new JMSException("unsupported operation");
  }

  @Override
  public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String subscriptionName,
      String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
    throw new JMSException("unsupported operation");
  }
  
  @Override
  public ConnectionConsumer createSharedConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
    throw new JMSException("unsupported operation");
  }
  
  @Override
  public ConnectionConsumer createSharedDurableConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool sessionPool, int maxMessages) throws JMSException {
    throw new JMSException("unsupported operation");
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

  enum NullExceptionListener implements ExceptionListener {
    INSTANCE;

    @Override
    public void onException(JMSException exception) {
      // null means ignore
    }

  }

}