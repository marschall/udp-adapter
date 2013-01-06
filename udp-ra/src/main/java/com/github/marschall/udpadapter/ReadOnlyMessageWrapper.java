package com.github.marschall.udpadapter;

import java.io.Serializable;
import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;

final class ReadOnlyMessageWrapper implements BytesMessage, StreamMessage, ObjectMessage {
  
  private final DatagramMessage delegate;

  ReadOnlyMessageWrapper(DatagramMessage delegate) {
    this.delegate = delegate;
  }

  public String getJMSMessageID() throws JMSException {
    return delegate.getJMSMessageID();
  }

  public void setJMSMessageID(String id) throws JMSException {
    throw new MessageNotWriteableException("setJMSMessageID");
  }

  public long getJMSTimestamp() throws JMSException {
    return delegate.getJMSTimestamp();
  }

  public void setJMSTimestamp(long timestamp) throws JMSException {
    throw new MessageNotWriteableException("setJMSTimestamp");
  }

  public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
    return delegate.getJMSCorrelationIDAsBytes();
  }

  public void setJMSCorrelationIDAsBytes(byte[] correlationID) throws JMSException {
    throw new MessageNotWriteableException("setJMSCorrelationIDAsBytes");
  }

  public void setJMSCorrelationID(String correlationID) throws JMSException {
    throw new MessageNotWriteableException("setJMSCorrelationID");
  }

  public String getJMSCorrelationID() throws JMSException {
    return delegate.getJMSCorrelationID();
  }

  public Destination getJMSReplyTo() throws JMSException {
    return delegate.getJMSReplyTo();
  }

  public void setJMSReplyTo(Destination replyTo) throws JMSException {
    throw new MessageNotWriteableException("setJMSReplyTo");
  }

  public Destination getJMSDestination() throws JMSException {
    return delegate.getJMSDestination();
  }

  public void setJMSDestination(Destination destination) throws JMSException {
    throw new MessageNotWriteableException("setJMSDestination");
  }

  public int getJMSDeliveryMode() throws JMSException {
    return delegate.getJMSDeliveryMode();
  }

  public void setJMSDeliveryMode(int deliveryMode) throws JMSException {
    throw new MessageNotWriteableException("setJMSDeliveryMode");
  }

  public boolean getJMSRedelivered() throws JMSException {
    return delegate.getJMSRedelivered();
  }

  public void setJMSRedelivered(boolean redelivered) throws JMSException {
    throw new MessageNotWriteableException("setJMSRedelivered");
  }

  public String getJMSType() throws JMSException {
    return delegate.getJMSType();
  }

  public void setJMSType(String type) throws JMSException {
    throw new MessageNotWriteableException("setJMSType");
  }

  public long getJMSExpiration() throws JMSException {
    return delegate.getJMSExpiration();
  }

  public void setJMSExpiration(long expiration) throws JMSException {
    throw new MessageNotWriteableException("setJMSExpiration");
  }

  public int getJMSPriority() throws JMSException {
    return delegate.getJMSPriority();
  }

  public void setJMSPriority(int priority) throws JMSException {
    throw new MessageNotWriteableException("setJMSPriority");
  }

  public void clearProperties() throws JMSException {
    throw new MessageNotWriteableException("clearProperties");
  }

  public boolean propertyExists(String name) throws JMSException {
    return delegate.propertyExists(name);
  }

  public boolean getBooleanProperty(String name) throws JMSException {
    return delegate.getBooleanProperty(name);
  }

  public byte getByteProperty(String name) throws JMSException {
    return delegate.getByteProperty(name);
  }

  public short getShortProperty(String name) throws JMSException {
    return delegate.getShortProperty(name);
  }

  public int getIntProperty(String name) throws JMSException {
    return delegate.getIntProperty(name);
  }

  public long getLongProperty(String name) throws JMSException {
    return delegate.getLongProperty(name);
  }

  public float getFloatProperty(String name) throws JMSException {
    return delegate.getFloatProperty(name);
  }

  public double getDoubleProperty(String name) throws JMSException {
    return delegate.getDoubleProperty(name);
  }

  public String getStringProperty(String name) throws JMSException {
    return delegate.getStringProperty(name);
  }

  public Object getObjectProperty(String name) throws JMSException {
    return delegate.getObjectProperty(name);
  }

  public Enumeration<String> getPropertyNames() throws JMSException {
    return delegate.getPropertyNames();
  }

  public void setBooleanProperty(String name, boolean value) throws JMSException {
    throw new MessageNotWriteableException("setBooleanProperty");
  }

  public void setByteProperty(String name, byte value) throws JMSException {
    throw new MessageNotWriteableException("setShortProperty");
  }

  public void setShortProperty(String name, short value) throws JMSException {
    throw new MessageNotWriteableException("setShortProperty");
  }

  public void setIntProperty(String name, int value) throws JMSException {
    throw new MessageNotWriteableException("setIntProperty");
  }

  public void setLongProperty(String name, long value) throws JMSException {
    throw new MessageNotWriteableException("setLongProperty");
  }

  public void setFloatProperty(String name, float value) throws JMSException {
    throw new MessageNotWriteableException("setFloatProperty");
  }

  public void setDoubleProperty(String name, double value) throws JMSException {
    throw new MessageNotWriteableException("setDoubleProperty");
  }

  public void setStringProperty(String name, String value) throws JMSException {
    throw new MessageNotWriteableException("setStringProperty");
  }

  public void setObjectProperty(String name, Object value) throws JMSException {
    throw new MessageNotWriteableException("setObjectProperty");
  }

  public void acknowledge() throws JMSException {
    delegate.acknowledge();
  }

  public void clearBody() throws JMSException {
    throw new MessageNotWriteableException("clearBody");
  }

  public String readString() throws JMSException {
    return delegate.readString();
  }

  public Object readObject() throws JMSException {
    return delegate.readObject();
  }

  public void writeString(String value) throws JMSException {
    throw new MessageNotWriteableException("writeString");
  }

  public long getBodyLength() throws JMSException {
    return delegate.getBodyLength();
  }

  public boolean readBoolean() throws JMSException {
    return delegate.readBoolean();
  }

  public byte readByte() throws JMSException {
    return delegate.readByte();
  }

  public int readUnsignedByte() throws JMSException {
    return delegate.readUnsignedByte();
  }

  public short readShort() throws JMSException {
    return delegate.readShort();
  }

  public int readUnsignedShort() throws JMSException {
    return delegate.readUnsignedShort();
  }

  public char readChar() throws JMSException {
    return delegate.readChar();
  }

  public int readInt() throws JMSException {
    return delegate.readInt();
  }

  public long readLong() throws JMSException {
    return delegate.readLong();
  }

  public float readFloat() throws JMSException {
    return delegate.readFloat();
  }

  public double readDouble() throws JMSException {
    return delegate.readDouble();
  }

  public String readUTF() throws JMSException {
    return delegate.readUTF();
  }

  public int readBytes(byte[] value) throws JMSException {
    return delegate.readBytes(value);
  }

  public int readBytes(byte[] value, int length) throws JMSException {
    return delegate.readBytes(value, length);
  }
  
  @Override
  public Serializable getObject() throws JMSException {
    return delegate.getObject();
  }

  public void writeBoolean(boolean value) throws JMSException {
    throw new MessageNotWriteableException("writeBoolean");
  }

  public void writeByte(byte value) throws JMSException {
    throw new MessageNotWriteableException("writeByte");
  }

  public void writeShort(short value) throws JMSException {
    throw new MessageNotWriteableException("writeShort");
  }

  public void writeChar(char value) throws JMSException {
    throw new MessageNotWriteableException("writeChar");
  }

  public void writeInt(int value) throws JMSException {
    throw new MessageNotWriteableException("writeInt");
  }

  public void writeLong(long value) throws JMSException {
    throw new MessageNotWriteableException("writeLong");
  }

  public void writeFloat(float value) throws JMSException {
    throw new MessageNotWriteableException("writeFloat");
  }

  public void writeDouble(double value) throws JMSException {
    throw new MessageNotWriteableException("writeDouble");
  }

  public void writeUTF(String value) throws JMSException {
    throw new MessageNotWriteableException("writeUTF");
  }

  public void writeBytes(byte[] value) throws JMSException {
    throw new MessageNotWriteableException("writeBytes");
  }

  public void writeBytes(byte[] value, int offset, int length) throws JMSException {
    throw new MessageNotWriteableException("writeBytes");
  }

  public void writeObject(Object value) throws JMSException {
    throw new MessageNotWriteableException("writeObject");
  }
  
  @Override
  public void setObject(Serializable object) throws JMSException {
    throw new MessageNotWriteableException("setObject");
  }

  public void reset() throws JMSException {
    delegate.reset();
  }
  
}
