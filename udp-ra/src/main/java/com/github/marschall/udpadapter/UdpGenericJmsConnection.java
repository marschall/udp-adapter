package com.github.marschall.udpadapter;

import javax.jms.JMSException;
import javax.jms.Session;

public final class UdpGenericJmsConnection extends UdpJmsConnection {

  private volatile MessagePool pool;

  @Override
  public Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
    // TODO cache?
    return new UdpGenericSession(transacted, acknowledgeMode, this.pool, this.sender);
  }
  
  @Override
  void setResourceAdapter(UdpAdapter ra) {
    this.sender = ra.getMessageSender();
  }

}
