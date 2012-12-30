package com.github.marschall.udpadapter;


final class UdpConfiguration {
	
  final int port;
  final int dataLength;
  final int datagramPoolSize;

  UdpConfiguration(int port, int dataLength, int datagramPoolSize) {
    this.port = port;
    this.dataLength = dataLength;
    this.datagramPoolSize = datagramPoolSize;
  }
  
}
