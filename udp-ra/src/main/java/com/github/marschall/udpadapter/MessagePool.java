package com.github.marschall.udpadapter;

import java.net.DatagramPacket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

final class MessagePool {
  
  private UdpConfiguration configuration;
  
  private final Queue<DatagramMessage> pool;

  MessagePool(UdpConfiguration spec) {
    this.configuration = spec;
    // automatically enforces maximum pool size
    this.pool = new ArrayBlockingQueue<>(spec.datagramPoolSize);
  }

  
  DatagramMessage getMessage() {
    // non-blocking version
    DatagramMessage message = this.pool.poll();
    if (message == null) {
      message = this.newMessage();
    }
    return message;
  }
  
  void returnMessage(DatagramMessage message) {
    // non-blocking version
    this.pool.offer(message);
  }

  void clear() {
    this.pool.clear();
  }

  private DatagramMessage newMessage() {
    byte[] data = new byte[this.configuration.dataLength];
    DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
    return new DatagramMessage(datagramPacket);
  }
  
}
