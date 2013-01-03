package com.github.marschall.udpadapter;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.Set;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.security.auth.Subject;

public class UdpReplyManagedConnectionFactory implements ManagedConnectionFactory {

  private volatile PrintWriter out;

  @Override
  public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
    return new UdpReplyConnectionFactory(cxManager, this);
  }

  @Override
  public Object createConnectionFactory() throws ResourceException {
    // my understanding is this only happens in an unmanaged environment
    throw new NotSupportedException("need connection manager");
  }

  @Override
  public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
    return new UdpReplyManagedConnection(cxRequestInfo);
  }

  @Override
  public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
    if (connectionSet == null) {
      throw new ResourceAdapterInternalException("not matching connection found");
    }
    for (Object each : connectionSet) {
      if (each instanceof UdpReplyManagedConnection) {
        UdpReplyManagedConnection connection = (UdpReplyManagedConnection) each;
        ConnectionRequestInfo info = connection.getConnectionRequestInfo();
        if (Objects.equals(info, cxRequestInfo)) {
          return connection;
        }
      }
    }
    throw new ResourceAdapterInternalException("not matching connection found");
  }

  @Override
  public void setLogWriter(PrintWriter out) throws ResourceException {
    this.out = out;
  }

  @Override
  public PrintWriter getLogWriter() throws ResourceException {
    return this.out;
  }
  
  // the spec wants these
  
  @Override
  public int hashCode() {
    return super.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

}
