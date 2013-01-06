package com.github.marschall.udpadapter;


import javax.jms.JMSException;
import javax.jms.Session;

class UdpReplySession extends UdpSession implements Session {

  UdpReplySession(boolean transacted, int acknowledgeMode) {
    super(transacted, acknowledgeMode);
  }

  @Override
  DatagramMessage createDatagramMessage() throws JMSException {
    return CurrentDatagramMessage.get();
  }

}
