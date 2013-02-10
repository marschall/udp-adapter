package com.github.marschall.udpadapter;


import javax.jms.JMSException;

class UdpReplySession extends UdpSession {

  UdpReplySession(boolean transacted, int acknowledgeMode, MessageSender sender) {
    super(transacted, acknowledgeMode, sender);
  }

  @Override
  DatagramMessage createDatagramMessage() throws JMSException {
    return CurrentDatagramMessage.get();
  }

}
