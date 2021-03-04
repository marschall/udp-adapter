package com.github.marschall.udpadapter.sample.ejb;

import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

// if you don't want this you have to switch the default wildfly resource adapter
// from hornetq to udp
@ResourceAdapter("udp-rar.rar")
@TransactionAttribute(SUPPORTS)
public class SampleBean implements MessageListener {

  static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Resource
  private ConnectionFactory connectionFactory;

  private JmsTemplate jmsTemplate;

  @PostConstruct
  public void ejbCreate() {
    this.jmsTemplate = new JmsTemplate(this.connectionFactory);
    // TODO really
    this.jmsTemplate.setSessionTransacted(false);
    this.jmsTemplate.setSessionAcknowledgeMode(AUTO_ACKNOWLEDGE);
  } 

  @Override
  public void onMessage(Message message) {
    LOG.info("onMessage");
    Destination destination;
    try {
      destination = message.getJMSReplyTo();
    } catch (JMSException e) {
      throw new EJBException("reply failed", e);
    }
    this.jmsTemplate.send(destination, (MessageCreator) session -> {
      BytesMessage incommingMessage = (BytesMessage) message;
      BytesMessage replyMessage = session.createBytesMessage();
      int messageLength = Math.toIntExact(incommingMessage.getBodyLength());
      if (messageLength < 0) {
        throw new EJBException("negative message length: " + messageLength);
      }
      byte[] content = new byte[messageLength];
      int read = incommingMessage.readBytes(content);
      if (read != messageLength) {
        throw new EJBException("read: " + read + " bytes but expected: " + messageLength);
      }
      replyMessage.writeBytes(content);
      return replyMessage;
    });
  }

}
