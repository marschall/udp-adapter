package com.github.marschall.udpadapter;

import javax.jms.JMSException;

interface MessageSender {
  
  void sendMessage(DatagramMessage message) throws JMSException;

}
