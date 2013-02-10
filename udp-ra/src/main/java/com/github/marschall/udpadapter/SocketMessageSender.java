package com.github.marschall.udpadapter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.jms.JMSException;

final class SocketMessageSender implements MessageSender {

  private final DatagramSocket socket;
  
  SocketMessageSender(DatagramSocket socket) {
    this.socket = socket;
  }

  @Override
  public void sendMessage(DatagramMessage message) throws JMSException {
    try {
      message.syncPosition();
      DatagramPacket packet = message.getPacket();
      this.socket.send(packet);
    } catch (IOException e) {
      JMSException jmsException = new JMSException("could not send message");
      jmsException.setLinkedException(jmsException);
      throw jmsException;
    }
  }
  

}
