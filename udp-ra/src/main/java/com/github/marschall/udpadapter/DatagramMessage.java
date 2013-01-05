package com.github.marschall.udpadapter;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageEOFException;
import javax.jms.MessageFormatException;
import javax.jms.StreamMessage;

final class DatagramMessage implements Message, BytesMessage, StreamMessage {

  private DatagramPacket packet;
  private Destination replyTo;
  private Destination destination;
  private int position;

  DatagramMessage(DatagramPacket packet) {
    this.packet = packet;
    this.position = 0;
    this.replyTo = new DatagramDestination(this);
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
    return DEFAULT_PRIORITY;
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
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setByteProperty(String name, byte value) throws JMSException {
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setShortProperty(String name, short value) throws JMSException {
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setIntProperty(String name, int value) throws JMSException {
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setLongProperty(String name, long value) throws JMSException {
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setFloatProperty(String name, float value) throws JMSException {
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setDoubleProperty(String name, double value) throws JMSException {
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setStringProperty(String name, String value) throws JMSException {
    validatePropertyName(name);
    // TODO Auto-generated method stub
  }

  @Override
  public void setObjectProperty(String name, Object value) throws JMSException {
    validatePropertyName(name);
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

  class Self implements Destination {

  }

  @Override
  public String readString() throws JMSException {
    return this.readUTF();
  }

  @Override
  public Object readObject() throws JMSException {
    throw new MessageFormatException("unsupported");
  }

  @Override
  public void writeString(String value) throws JMSException {
    this.writeUTF(value);
  }
  
  @Override
  public long getBodyLength() throws JMSException {
    return this.packet.getLength();
  }
  
  private byte read() throws JMSException {
    if (this.position >= this.packet.getLength()) {
      throw new MessageEOFException("reached end of message");
    }
    return this.packet.getData()[this.packet.getOffset() + this.position++];
  }
  
  private void write(byte value) throws JMSException {
    if (this.position >= this.packet.getLength()) {
      throw new MessageEOFException("reached end of message");
    }
    this.packet.getData()[this.packet.getOffset() + this.position++] = value;
  }
  
  private int capacity() {
    return this.packet.getLength() - this.position;
  }

  @Override
  public boolean readBoolean() throws JMSException {
    return this.read() != 0;
  }

  @Override
  public byte readByte() throws JMSException {
    return this.read();
  }

  @Override
  public int readUnsignedByte() throws JMSException {
    return this.readByte() & 0xff;
  }

  @Override
  public short readShort() throws JMSException {
    return (short) ((this.readUnsignedByte() << 8) | this.readUnsignedByte());
  }

  @Override
  public int readUnsignedShort() throws JMSException {
    return (this.readUnsignedByte() << 8) | this.readUnsignedByte();
  }

  @Override
  public char readChar() throws JMSException {
    return (char) ((this.readUnsignedByte() << 8) | this.readUnsignedByte());
  }

  @Override
  public int readInt() throws JMSException {
    return (this.readUnsignedByte() << 24)
         | (this.readUnsignedByte() << 16)
         | (this.readUnsignedByte() << 8)
         |  this.readUnsignedByte();
  }

  @Override
  public long readLong() throws JMSException {
    return (this.readUnsignedByte() << 56)
         | (this.readUnsignedByte() << 48)
         | (this.readUnsignedByte() << 40)
         | (this.readUnsignedByte() << 32)
         | (this.readUnsignedByte() << 24)
         | (this.readUnsignedByte() << 16)
         | (this.readUnsignedByte() <<  8)
         |  this.readUnsignedByte();
  }

  @Override
  public float readFloat() throws JMSException {
    return Float.intBitsToFloat(this.readInt());
  }

  @Override
  public double readDouble() throws JMSException {
    return Double.longBitsToDouble(this.readLong());
  }

  @Override
  public String readUTF() throws JMSException {
    int length = this.readUnsignedShort();
    byte[] buffer = new byte[length];
    int read = this.readBytes(buffer);
    if (read < length) {
      throw new MessageEOFException("reached end of message");
    }
    return new String(buffer, StandardCharsets.UTF_8);
  }

  @Override
  public int readBytes(byte[] value) throws JMSException {
    return this.readBytes(value, value.length);
  }

  @Override
  public int readBytes(byte[] value, int length) throws JMSException {
    if (length <= 0) {
      throw new IndexOutOfBoundsException("invalid length (negative): " + length);
    }
    if (length == 0) {
      return 0;
    }
    if (length > value.length) {
      throw new IndexOutOfBoundsException("invalid length: " + length + " bigger than: " + value.length);
    }
    int toRead = Math.min(this.capacity(), length);
    if (toRead == 0) {
      return -1;
    }
    System.arraycopy(this.packet.getData(), this.packet.getOffset() + this.position, value, 0, toRead);
    this.position += toRead;
    return toRead;
  }

  @Override
  public void writeBoolean(boolean value) throws JMSException {
    this.writeByte(value ? (byte) 1 : (byte) 0);
  }

  @Override
  public void writeByte(byte value) throws JMSException {
    this.write(value);
  }

  @Override
  public void writeShort(short value) throws JMSException {
    this.write((byte) ((value >>> 8) & 0xFF));
    this.write((byte) (value & 0xFF));
  }

  @Override
  public void writeChar(char value) throws JMSException {
    this.write((byte) ((value >>> 8) & 0xFF));
    this.write((byte) (value & 0xFF));
  }

  @Override
  public void writeInt(int value) throws JMSException {
    this.write((byte) ((value >>> 24) & 0xFF));
    this.write((byte) ((value >>> 16) & 0xFF));
    this.write((byte) ((value >>>  8) & 0xFF));
    this.write((byte) (value & 0xFF));
  }

  @Override
  public void writeLong(long value) throws JMSException {
    this.write((byte) ((value >>> 56) & 0xFF));
    this.write((byte) ((value >>> 48) & 0xFF));
    this.write((byte) ((value >>> 40) & 0xFF));
    this.write((byte) ((value >>> 32) & 0xFF));
    this.write((byte) ((value >>> 24) & 0xFF));
    this.write((byte) ((value >>> 16) & 0xFF));
    this.write((byte) ((value >>>  8) & 0xFF));
    this.write((byte) (value & 0xFF));
  }

  @Override
  public void writeFloat(float value) throws JMSException {
    this.writeInt(Float.floatToIntBits(value));
  }

  @Override
  public void writeDouble(double value) throws JMSException {
    this.writeLong(Double.doubleToLongBits(value));
  }

  @Override
  public void writeUTF(String value) throws JMSException {
    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
    int dataLength = bytes.length;
    if (dataLength > Short.MAX_VALUE) {
      throw new JMSException("encoded string too long: " + dataLength + " bytes");
    }
    this.writeShort((short) dataLength);
    this.writeBytes(bytes);
  }

  @Override
  public void writeBytes(byte[] value) throws JMSException {
    this.writeBytes(value, 0, value.length);
  }

  @Override
  public void writeBytes(byte[] value, int offset, int length) throws JMSException {
    if (length < 0) {
      throw new IllegalArgumentException("invalid length: " + length);
    }
    if (length > this.capacity()) {
      throw new JMSException("end of stream reached");
    }
    System.arraycopy(this.packet.getData(), this.packet.getOffset() + this.position, value, offset, length);
    this.position += length;
  }

  @Override
  public void writeObject(Object value) throws JMSException {
    if (value == null) {
      throw new NullPointerException("value is null");
    }
    if (value instanceof String) {
      this.writeUTF((String) value);
      return;
    }
    if (value instanceof Long) {
      this.writeLong((Long) value);
      return;
    }
    if (value instanceof Integer) {
      this.writeInt((Integer) value);
      return;
    }
    if (value instanceof Short) {
      this.writeShort((Short) value);
      return;
    }
    if (value instanceof Character) {
      this.writeChar((Character) value);
      return;
    }
    if (value instanceof Byte) {
      this.writeByte((Byte) value);
      return;
    }
    if (value instanceof Double) {
      this.writeDouble((Double) value);
      return;
    }
    if (value instanceof Float) {
      this.writeFloat((Float) value);
      return;
    }
    if (value instanceof Boolean) {
      this.writeBoolean((Boolean) value);
      return;
    }
    if (value instanceof byte[]) {
      this.writeBytes((byte[]) value);
      return;
    }
    throw new MessageFormatException("unsupported type: " + value.getClass());
  }

  @Override
  public void reset() throws JMSException {
    // TODO readonly
    this.position = 0;
  }

}
