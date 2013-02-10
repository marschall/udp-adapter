package com.github.marschall.udpadapter;

import java.net.DatagramPacket;
import java.net.SocketAddress;

import javax.jms.Destination;
import javax.jms.JMSException;

abstract class UdpDestination implements Destination, MessageSender {
  
  private final SocketAddress socketAddress;
  
  final String name;

  private final MessageSender sender;

  UdpDestination(SocketAddress socketAddress, String name, MessageSender sender) {
    this.socketAddress = socketAddress;
    this.name = name;
    this.sender = sender;
  }

  @Override
  public void sendMessage(DatagramMessage message) throws JMSException {
    DatagramPacket packet = message.getPacket();
    packet.setSocketAddress(this.socketAddress);
    this.sender.sendMessage(message);
  }
  
}
