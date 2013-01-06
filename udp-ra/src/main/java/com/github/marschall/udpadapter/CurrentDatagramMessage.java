package com.github.marschall.udpadapter;

final class CurrentDatagramMessage {

  private static final ThreadLocal<DatagramMessage> MESSAGE = new ThreadLocal<>();
  
  static void useDuring(DatagramMessage message, Runnable r) {
    try {
      MESSAGE.set(message);
      r.run();
    } finally {
      MESSAGE.remove();
    }
  }
  
  static DatagramMessage get() {
    return MESSAGE.get();
  }
  
}
