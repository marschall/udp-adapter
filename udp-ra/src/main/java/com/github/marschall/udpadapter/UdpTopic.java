package com.github.marschall.udpadapter;

import java.net.SocketAddress;

import javax.jms.JMSException;
import javax.jms.Topic;

final class UdpTopic extends UdpDestination implements Topic {

  UdpTopic(SocketAddress socketAddress, String name, MessageSender sender) {
    super(socketAddress, name, sender);
  }

  @Override
  public String getTopicName() throws JMSException {
    return this.name;
  }

}
