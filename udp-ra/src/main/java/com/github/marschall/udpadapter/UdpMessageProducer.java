package com.github.marschall.udpadapter;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

final class UdpMessageProducer implements MessageProducer {

  private boolean disableMessageID;
  private boolean disableMessageTimestamp;
  private int deliveryMode;
  private int defaultPriority;
  private long timeToLive;
  private final Destination destination;

  UdpMessageProducer(Destination destination) {
    this.destination = destination;
  }

  @Override
  public void setDisableMessageID(boolean value) throws JMSException {
    this.disableMessageID = value;
  }

  @Override
  public boolean getDisableMessageID() throws JMSException {
    return this.disableMessageID;
  }

  @Override
  public void setDisableMessageTimestamp(boolean value) throws JMSException {
    this.disableMessageTimestamp = value;
  }

  @Override
  public boolean getDisableMessageTimestamp() throws JMSException {
    return this.disableMessageTimestamp;
  }

  @Override
  public void setDeliveryMode(int deliveryMode) throws JMSException {
    // TODO validate
    this.deliveryMode = deliveryMode;
  }

  @Override
  public int getDeliveryMode() throws JMSException {
    return this.deliveryMode;
  }

  @Override
  public void setPriority(int defaultPriority) throws JMSException {
    // TODO validate
    this.defaultPriority = defaultPriority;
  }

  @Override
  public int getPriority() throws JMSException {
    return this.defaultPriority;
  }

  @Override
  public void setTimeToLive(long timeToLive) throws JMSException {
    this.timeToLive = timeToLive;
  }

  @Override
  public long getTimeToLive() throws JMSException {
    return this.timeToLive;
  }

  @Override
  public Destination getDestination() throws JMSException {
    return this.destination;
  }

  @Override
  public void close() throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void send(Message message) throws JMSException {
    this.send(message, this.getDeliveryMode(), this.getPriority(), this.getTimeToLive());
  }

  @Override
  public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
    // TODO Auto-generated method stub
  }

  @Override
  public void send(Destination destination, Message message) throws JMSException {
    this.send(destination, message, this.getDeliveryMode(), this.getPriority(), this.getTimeToLive());
  }

  @Override
  public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
    // TODO Auto-generated method stub

  }

}
