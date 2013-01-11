package com.github.marschall.udpadapter;

import javax.jms.JMSException;
import javax.jms.Session;

final class UdpGenericJmsConnection extends UdpJmsConnection {

  private final MessagePool pool;

  UdpGenericJmsConnection(MessagePool pool) {
    this.pool = pool;
  }
  
  @Override
  public Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
    // TODO cache?
    return new UdpGenericSession(transacted, acknowledgeMode, this.pool);
  }

}
