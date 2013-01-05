package com.github.marschall.udpadapter;

import java.net.SocketAddress;

import javax.jms.JMSException;
import javax.jms.Topic;

final class UdpTopic extends UdpDestination implements Topic {

  UdpTopic(SocketAddress socketAddress, String name) {
    super(socketAddress, name);
  }

  @Override
  public String getTopicName() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

}
