package com.github.marschall.udpadapter.sample;

import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.jms.Session.AUTO_ACKNOWLEDGE;

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
import javax.jms.Session;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

// if you don't want this you have to switch the default jboss resource adapter
// from hornetq to udp
@ResourceAdapter("udp-rar.rar")
@TransactionAttribute(SUPPORTS)
public class SampleBean implements MessageListener {
	
	static final Logger LOG = Logger.getLogger(SampleBean.class.getName());
	
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
	public void onMessage(final Message message) {
		LOG.info("onMessage");
		Destination destination;
    try {
      destination = message.getJMSReplyTo();
    } catch (JMSException e) {
      throw new EJBException("reply failed", e);
    }
    this.jmsTemplate.send(destination, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        BytesMessage incommingMessage = (BytesMessage) message;
        BytesMessage replyMessage = session.createBytesMessage();
        byte[] content = new byte[(int) incommingMessage.getBodyLength()];
        // TODO verify
        incommingMessage.readBytes(content);
        replyMessage.writeBytes(content);
        return replyMessage;
      }
    });
	}

}
