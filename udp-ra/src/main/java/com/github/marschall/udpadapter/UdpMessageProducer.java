package com.github.marschall.udpadapter;

import javax.jms.CompletionListener;
import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

final class UdpMessageProducer implements MessageProducer {

  private boolean disableMessageID;
  private boolean disableMessageTimestamp;
  private int deliveryMode;
  private int defaultPriority;
  private long timeToLive = Message.DEFAULT_TIME_TO_LIVE;
  private final Destination destination;
  private final MessageSender sender;
  private long deliveryDelay = Message.DEFAULT_DELIVERY_DELAY;

  UdpMessageProducer(Destination destination, MessageSender sender) {
    this.destination = destination;
    this.sender = sender;
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
  public void setDeliveryDelay(long deliveryDelay) throws JMSException {
    this.deliveryDelay = deliveryDelay;
  }
  
  @Override
  public long getDeliveryDelay() throws JMSException {
    return this.deliveryDelay;
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
    if (!(message instanceof DatagramMessage)) {
      throw new JMSException("unsupported message type: " + message);
    }
    this.sender.sendMessage((DatagramMessage) message);
  }

  @Override
  public void send(Destination destination, Message message) throws JMSException {
    this.send(destination, message, this.getDeliveryMode(), this.getPriority(), this.getTimeToLive());
  }
  
  @Override
  public void send(Destination destination, Message message, CompletionListener completionListener) throws JMSException {
    throw new JMSException("unsupported");
  }
  
  @Override
  public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
    throw new JMSException("unsupported");
  }
  
  @Override
  public void send(Message message, CompletionListener completionListener) throws JMSException {
    throw new JMSException("unsupported");
  }
  
  @Override
  public void send(Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
    if (this.destination != null && this.destination != destination) {
      throw new InvalidDestinationException("message producer with specific destination can only send to this destination");
    }
    if (!(message instanceof DatagramMessage)) {
      throw new JMSException("unsupported message type: " + message);
    }
    
    DatagramMessage datagramMessage = (DatagramMessage) message;

    if (destination instanceof UdpDestination) {
      UdpDestination udpDestination = (UdpDestination) destination;
      udpDestination.sendMessage(datagramMessage);
    }
    if (destination instanceof DatagramDestination) {
      DatagramDestination datagramDestination = (DatagramDestination) destination;
      if (datagramDestination.getMessage() != message) {
        // TODO check for wrappers 'n stuff
        throw new JMSException("can not reply to destination of an other message");
      }
      this.sender.sendMessage(datagramMessage);
    }
    throw new JMSException("unsupported destination type: " + destination);
  }

}
