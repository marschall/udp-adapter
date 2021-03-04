package com.github.marschall.udpadapter.sample.client;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class UdpClient {

  public static void main(String[] args) throws IOException {
    int port = Integer.parseInt(args[0]);
    try (DatagramSocket socket = new DatagramSocket()) {
      socket.setSoTimeout(Math.toIntExact(TimeUnit.SECONDS.toMillis(10L)));
      InetAddress localhost = InetAddress.getByName("localhost");
      sendMessage(socket, localhost, port);
      
      DatagramPacket response = receive(socket, localhost, port);
      System.out.println(new String(response.getData(), 0, response.getLength(), UTF_8));
    }
  }

  private static void sendMessage(DatagramSocket socket, InetAddress address, int port) throws IOException {
    byte[] message = "hello world".getBytes(UTF_8);
    DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
    socket.send(packet);
  }
  
  private static DatagramPacket receive(DatagramSocket socket, InetAddress address, int port) throws IOException {
    byte[] buffer = new byte[65507];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
    socket.receive(packet);
    return packet;
  }

}
