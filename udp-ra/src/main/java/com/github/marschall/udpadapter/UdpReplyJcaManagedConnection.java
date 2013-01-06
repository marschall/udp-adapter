package com.github.marschall.udpadapter;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.jms.JMSException;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

class UdpReplyJcaManagedConnection implements ManagedConnection {
  
  private final List<ConnectionEventListener> listeners;
  private volatile PrintWriter out;
  private final ConnectionRequestInfo connectionRequestInfo;
  private volatile UdpReplyJmsConnection connection;
  
  UdpReplyJcaManagedConnection(ConnectionRequestInfo connectionRequestInfo) {
    this.connectionRequestInfo = connectionRequestInfo;
    this.listeners = new CopyOnWriteArrayList<>();
  }
  
  ConnectionRequestInfo getConnectionRequestInfo() {
    return this.connectionRequestInfo;
  }

  @Override
  public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
    return this.connection;
  }

  @Override
  public void destroy() throws ResourceException {
    try {
      this.connection.close();
    } catch (JMSException e) {
      throw new ResourceException("Could not close connection", e);
    }
  }

  @Override
  public void cleanup() throws ResourceException {
    // TODO
    // nothing so far
  }

  @Override
  public void associateConnection(Object connection) throws ResourceException {
    if (connection instanceof UdpReplyJmsConnection) {
      this.connection = (UdpReplyJmsConnection) connection;
    } else {
      throw new ResourceException("invalid connection type: " + connection);
    }
  }

  @Override
  public void addConnectionEventListener(ConnectionEventListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void removeConnectionEventListener(ConnectionEventListener listener) {
    this.listeners.remove(listener);
  }

  @Override
  public XAResource getXAResource() throws ResourceException {
    throw new NotSupportedException("XA resources not supported");
  }

  @Override
  public LocalTransaction getLocalTransaction() throws ResourceException {
    // TODO
    throw new NotSupportedException("local transactions not supported");
  }

  @Override
  public ManagedConnectionMetaData getMetaData() throws ResourceException {
    return UdpManagedConnectionMetaData.INSTANCE;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws ResourceException {
    this.out = out;

  }

  @Override
  public PrintWriter getLogWriter() throws ResourceException {
    return this.out;
  }
  
  enum UdpManagedConnectionMetaData implements ManagedConnectionMetaData {
    
    INSTANCE;

    @Override
    public String getEISProductName() throws ResourceException {
      return "UPD JMS";
    }

    @Override
    public String getEISProductVersion() throws ResourceException {
      return "0.2.0";
    }

    @Override
    public int getMaxConnections() throws ResourceException {
      return 0;
    }

    @Override
    public String getUserName() throws ResourceException {
      // TODO Auto-generated method stub
      return null;
    }
    
  }

}
