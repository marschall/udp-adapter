package com.github.marschall.udpadapter;

import java.net.DatagramPacket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.resource.spi.AdministeredObject;

//TODO, define interface
//@AdministeredObject
final class MessagePool {
  
  private UdpConfiguration configuration;
  
  private final Queue<DatagramMessage> pool;
  
  private final AtomicInteger abovePool;

  MessagePool(UdpConfiguration spec) {
    this.configuration = spec;
    // automatically enforces maximum pool size
    this.pool = new ArrayBlockingQueue<>(spec.datagramPoolSize);
    this.abovePool = new AtomicInteger(0);
  }

  
  DatagramMessage getMessage() {
    // non-blocking version
    DatagramMessage message = this.pool.poll();
    if (message == null) {
      message = this.newMessage();
      this.abovePool.incrementAndGet();
    }
    return message;
  }
  
  void returnMessage(DatagramMessage message) {
    // non-blocking version
    if (!this.pool.offer(message)) {
      this.abovePool.decrementAndGet();
    }
  }
  
  public int getPoolSize() {
    return this.pool.size();
  }
  
  public int getMessageCount() {
    return this.pool.size() + this.abovePool.get();
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
