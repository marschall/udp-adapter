package com.github.marschall.udpadapter;

import java.io.IOException;

interface MessageSender {
  
  void sendMessage(DatagramMessage message) throws IOException;

}
