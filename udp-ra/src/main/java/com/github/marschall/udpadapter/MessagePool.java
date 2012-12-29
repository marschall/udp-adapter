package com.github.marschall.udpadapter;

import java.net.DatagramPacket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

final class MessagePool {
  
  private UdpActivationSpec spec;
  
  private final Queue<DatagramMessage> pool;

  MessagePool(UdpActivationSpec spec) {
    this.spec = spec;
    // automatically enforces maximum pool size
    this.pool = new ArrayBlockingQueue<>(spec.getDatagramPoolSize());
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
    byte[] data = new byte[this.spec.getDataLength()];
    DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
    return new DatagramMessage(datagramPacket);
  }
  
}
