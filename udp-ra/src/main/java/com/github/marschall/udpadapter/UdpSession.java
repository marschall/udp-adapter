package com.github.marschall.udpadapter;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

abstract class UdpSession implements Session {

  private volatile MessageListener messageListener;

  final boolean transacted;
  final int acknowledgeMode;

  private final MessageSender sender;

  UdpSession(boolean transacted, int acknowledgeMode, MessageSender sender) {
    this.transacted = transacted;
    this.acknowledgeMode = acknowledgeMode;
    this.sender = sender;
  }
  
  abstract DatagramMessage createDatagramMessage() throws JMSException;
  
  @Override
  public StreamMessage createStreamMessage() throws JMSException {
    return createDatagramMessage();
  }
  
  @Override
  public BytesMessage createBytesMessage() throws JMSException {
    return createDatagramMessage();
  }
  
  @Override
  public ObjectMessage createObjectMessage() throws JMSException {
    return createDatagramMessage();
  }
  
  @Override
  public Message createMessage() throws JMSException {
    return createDatagramMessage();
  }
  
  @Override
  public ObjectMessage createObjectMessage(Serializable object) throws JMSException {
    DatagramMessage message = createDatagramMessage();
    // TODO read only
    message.setObject(object);
    return message;
  }

  @Override
  public MapMessage createMapMessage() throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public TextMessage createTextMessage() throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public TextMessage createTextMessage(String text) throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public boolean getTransacted() throws JMSException {
    return this.transacted;
  }

  @Override
  public int getAcknowledgeMode() throws JMSException {
    return this.acknowledgeMode;
  }

  @Override
  public void commit() throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public void rollback() throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public void recover() throws JMSException {
    // TODO Auto-generated method stub

  }

  @Override
  public MessageListener getMessageListener() throws JMSException {
    return this.messageListener;
  }

  @Override
  public void setMessageListener(MessageListener listener) throws JMSException {
    this.messageListener = listener;
  }

  @Override
  public void run() {
    // TODO dafuq
  }

  @Override
  public MessageProducer createProducer(Destination destination) throws JMSException {
    // TODO cache?
    return new UdpMessageProducer(destination, this.sender);
  }

  @Override
  public MessageConsumer createConsumer(Destination destination) throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public MessageConsumer createConsumer(Destination destination, String messageSelector) throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean NoLocal) throws JMSException {
    throw new JMSException("unsupported");
  }
  

  private static SocketAddress parse(String queueName) throws JMSException {
    if (queueName == null || queueName.isEmpty()) {
      throw new JMSException("invalid queue name");
    }
    if (isIpv6(queueName)) {
      return parseIpv6(queueName);
    } else {
      return parseIpv4(queueName);
    }
  }

  static SocketAddress parseIpv4(String queueName) throws JMSException {
    int colonIndex = queueName.indexOf(':');
    if (colonIndex == -1 || colonIndex == 0 || colonIndex == queueName.length() -1) {
      throw new JMSException("invalid destination name '" + queueName + "' format must be host:port");
    }
    String host = queueName.substring(0, colonIndex);
    int port;
    try {
      port = Integer.parseInt(queueName.substring(colonIndex + 1));
    } catch (NumberFormatException e) {
      JMSException jmsException = new JMSException("invalid destination name '" + queueName + "' format must be host:port");
      jmsException.setLinkedException(e);
      throw jmsException;
    }
    return InetSocketAddress.createUnresolved(host, port);
  }

  static SocketAddress parseIpv6(String queueName) throws JMSException {
    int colonIndex = queueName.lastIndexOf(':'); // IPv6 contains :
    if (colonIndex == -1 || colonIndex == queueName.length() -1) {
      throw new JMSException("invalid destination name '" + queueName + "' format must be [host]:port");
    }
    if (queueName.charAt(colonIndex - 1) != ']') {
      throw new JMSException("invalid destination name '" + queueName + "' format must be [host]:port");
    }
    String host = queueName.substring(1, colonIndex - 1);
    int port;
    try {
      port = Integer.parseInt(queueName.substring(colonIndex + 1));
    } catch (NumberFormatException e) {
      JMSException jmsException = new JMSException("invalid destination name '" + queueName + "' format must be [host]:port");
      jmsException.setLinkedException(e);
      throw jmsException;
    }
    return InetSocketAddress.createUnresolved(host, port);
  }

  private static boolean isIpv6(String queueName) {
    return queueName.charAt(0) == '[';
  }

  @Override
  public Queue createQueue(String queueName) throws JMSException {
    return new UdpQueue(parse(queueName), queueName, this.sender);
  }

  @Override
  public Topic createTopic(String topicName) throws JMSException {
    return new UdpTopic(parse(topicName), topicName, this.sender);
  }

  @Override
  public TopicSubscriber createDurableSubscriber(Topic topic, String name)
      throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector,
      boolean noLocal) throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public QueueBrowser createBrowser(Queue queue) throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
    throw new JMSException("unsupported");
  }

  @Override
  public TemporaryQueue createTemporaryQueue() throws JMSException {
    throw new JMSException("createTemporaryQueue");
  }

  @Override
  public TemporaryTopic createTemporaryTopic() throws JMSException {
    throw new JMSException("createTemporaryTopic");
  }

  @Override
  public void unsubscribe(String name) throws JMSException {
    throw new JMSException("unsupported");
  }

}