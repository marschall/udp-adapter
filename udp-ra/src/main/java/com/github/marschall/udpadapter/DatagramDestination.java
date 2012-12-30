package com.github.marschall.udpadapter;

import javax.jms.Destination;

final class DatagramDestination implements Destination {
  
  private final DatagramMessage message;

  DatagramDestination(DatagramMessage message) {
    this.message = message;
  }
  
  DatagramMessage getMessage() {
    return this.message;
  }

}
