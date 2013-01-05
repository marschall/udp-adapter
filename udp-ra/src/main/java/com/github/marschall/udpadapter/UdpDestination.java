package com.github.marschall.udpadapter;

import java.net.SocketAddress;

import javax.jms.Destination;

abstract class UdpDestination implements Destination {
  
  private final SocketAddress socketAddress;
  
  final String name;

  UdpDestination(SocketAddress socketAddress, String name) {
    this.socketAddress = socketAddress;
    this.name = name;
  }

}
