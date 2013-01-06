package com.github.marschall.udpadapter;

import javax.jms.JMSException;
import javax.jms.Session;

class UdpReplyJmsConnection extends UdpJmsConnection {

  @Override
  public Session createSession(boolean transacted, int acknowledgeMode) throws JMSException {
    return new UdpReplySession(transacted, acknowledgeMode);
  } 

}
