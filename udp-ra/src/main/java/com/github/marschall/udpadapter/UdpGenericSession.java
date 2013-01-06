package com.github.marschall.udpadapter;

import javax.jms.JMSException;
import javax.jms.Session;

final class UdpGenericSession extends UdpSession implements Session {

  private final MessagePool pool;

  UdpGenericSession(boolean transacted, int acknowledgeMode, MessagePool pool) {
    super(transacted, acknowledgeMode);
    this.pool = pool;
  }

  @Override
  DatagramMessage createDatagramMessage() throws JMSException {
    return this.pool.getMessage();
  }

}
