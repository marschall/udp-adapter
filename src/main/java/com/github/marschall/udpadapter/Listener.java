package com.github.marschall.udpadapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;

final class Listener implements Work {

  private volatile boolean cancel = false;
  
  private final MessageEndpointFactory endpointFactory;
  
  private static final Method ON_MESSAGE_METHOD;

  static {
    try {
      ON_MESSAGE_METHOD = MessageListener.class.getMethod("onMessage", Message.class);
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }
  
  Listener(MessageEndpointFactory endpointFactory) {
    this.endpointFactory = endpointFactory;
  }

  @Override
  public void run() {
    try {
      DatagramPacket datagramPacket = new DatagramPacket(null, 0);
      DatagramSocket socket = new DatagramSocket(0);
      while (!this.cancel) {
        socket.receive(datagramPacket);
      }
      
      MessageEndpoint endpoint = this.endpointFactory.createEndpoint(null);
      try {
        endpoint.beforeDelivery(ON_MESSAGE_METHOD);
        endpoint.afterDelivery();
      } finally {
        endpoint.release();
      }
      
    } catch (ResourceException | NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Override
  public void release() {
    // TODO remove from adapter when called by container?
    // covered by spec?
    this.cancel = true;
  }


}
