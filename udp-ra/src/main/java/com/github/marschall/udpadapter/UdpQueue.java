package com.github.marschall.udpadapter;

import java.net.SocketAddress;

import javax.jms.JMSException;
import javax.jms.Queue;

final class UdpQueue extends UdpDestination implements Queue {

  UdpQueue(SocketAddress socketAddress, String name) {
    super(socketAddress, name);
  }

  @Override
  public String getQueueName() throws JMSException {
    return this.name;
  }

}
