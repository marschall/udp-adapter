package com.github.marschall.udpadapter;

import java.net.DatagramPacket;
import java.util.Collections;
import java.util.Enumeration;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public class DatagramMessage implements Message {

  private DatagramPacket packet;
  private Destination replyTo;
  private Destination destination;

  DatagramMessage(DatagramPacket packet) {
    this.packet = packet;
    this.replyTo = new ReplyTo();
    this.destination = new Self();
  }
  
  DatagramPacket getPacket() {
    return this.packet;
  }

  @Override
  public String getJMSMessageID() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setJMSMessageID(String id) throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public long getJMSTimestamp() throws JMSException {
    return 0;
  }

  @Override
  public void setJMSTimestamp(long timestamp) throws JMSException {
    throw new JMSException("can not set JMS timestamp");
  }

  @Override
  public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setJMSCorrelationIDAsBytes(byte[] correlationID) throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public void setJMSCorrelationID(String correlationID) throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public String getJMSCorrelationID() throws JMSException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Destination getJMSReplyTo() throws JMSException {
    return this.replyTo;
  }

  @Override
  public void setJMSReplyTo(Destination replyTo) throws JMSException {
    throw new JMSException("can not set JMS reply to");
  }

  @Override
  public Destination getJMSDestination() throws JMSException {
    return this.destination;
  }

  @Override
  public void setJMSDestination(Destination destination) throws JMSException {
    throw new JMSException("can not set JMS destination");
  }

  @Override
  public int getJMSDeliveryMode() throws JMSException {
    return DeliveryMode.NON_PERSISTENT;
  }

  @Override
  public void setJMSDeliveryMode(int deliveryMode) throws JMSException {
    throw new JMSException("can not set JMS delivery mode");
  }

  @Override
  public boolean getJMSRedelivered() throws JMSException {
    return false;
  }

  @Override
  public void setJMSRedelivered(boolean redelivered) throws JMSException {
    throw new JMSException("can not set JMS redelivered");
  }

  @Override
  public String getJMSType() throws JMSException {
    return "udp-incomming";
  }

  @Override
  public void setJMSType(String type) throws JMSException {
    throw new JMSException("can not set JMS type");
  }

  @Override
  public long getJMSExpiration() throws JMSException {
    return 0;
  }

  @Override
  public void setJMSExpiration(long expiration) throws JMSException {
    throw new JMSException("can not set JMS expiration");
  }

  @Override
  public int getJMSPriority() throws JMSException {
    return 2;
  }

  @Override
  public void setJMSPriority(int priority) throws JMSException {
    throw new JMSException("can not set JMS priority");
  }

  @Override
  public void clearProperties() throws JMSException {
    // TODO Auto-generated method stub

  }

  private static void validatePropertyName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("property name must not be null");
    }
    if (name.isEmpty()) {
      throw new IllegalArgumentException("property name must not be empty");
    }
  }

  @Override
  public boolean propertyExists(String name) throws JMSException {
    return false;
  }

  @Override
  public boolean getBooleanProperty(String name) throws JMSException {
    return Boolean.valueOf(null);
  }

  @Override
  public byte getByteProperty(String name) throws JMSException {
    return Byte.valueOf(null);
  }

  @Override
  public short getShortProperty(String name) throws JMSException {
    return Short.valueOf(null);
  }

  @Override
  public int getIntProperty(String name) throws JMSException {
    return Integer.valueOf(null);
  }

  @Override
  public long getLongProperty(String name) throws JMSException {
    return Long.valueOf(null);
  }

  @Override
  public float getFloatProperty(String name) throws JMSException {
    return Float.valueOf(null);
  }

  @Override
  public double getDoubleProperty(String name) throws JMSException {
    return Double.valueOf(null);
  }

  @Override
  public String getStringProperty(String name) throws JMSException {
    return null;
  }

  @Override
  public Object getObjectProperty(String name) throws JMSException {
    return null;
  }

  @Override
  public Enumeration<String> getPropertyNames() throws JMSException {
    return Collections.emptyEnumeration();
  }

  @Override
  public void setBooleanProperty(String name, boolean value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setByteProperty(String name, byte value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setShortProperty(String name, short value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setIntProperty(String name, int value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setLongProperty(String name, long value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setFloatProperty(String name, float value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setDoubleProperty(String name, double value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setStringProperty(String name, String value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void setObjectProperty(String name, Object value) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void acknowledge() throws JMSException {
    // ignore
  }

  @Override
  public void clearBody() throws JMSException {
    // TODO Auto-generated method stub
  }

  class ReplyTo implements Destination {

  }

  class Self implements Destination {

  }

}
