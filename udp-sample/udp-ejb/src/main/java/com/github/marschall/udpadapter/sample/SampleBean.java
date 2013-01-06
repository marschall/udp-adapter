package com.github.marschall.udpadapter.sample;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.jboss.ejb3.annotation.ResourceAdapter;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

// if you don't want this you have to switch the default jboss resource adapter
// from hornetq to udp
@ResourceAdapter("udp-rar.rar")
public class SampleBean implements MessageListener {
	
	static final Logger LOG = Logger.getLogger(SampleBean.class.getName());
	
	@Resource
	private ConnectionFactory connectionFactory;

	@Override
	public void onMessage(Message message) {
		LOG.info("onMessage");
		//TODO Spring JMS Template
		try {
      Connection connection = connectionFactory.createConnection();
      try {
        Session session = connection.createSession(false, AUTO_ACKNOWLEDGE);
        try {
          MessageProducer producer = session.createProducer(message.getJMSReplyTo());
          try {
            BytesMessage incommingMessage = (BytesMessage) message;
            BytesMessage replyMessage = session.createBytesMessage();
            byte[] content = new byte[(int) incommingMessage.getBodyLength()];
            // TODO verify
            incommingMessage.readBytes(content);
            replyMessage.writeBytes(content);
            
            producer.send(replyMessage);
          } finally {
            producer.close();
          }
        } finally {
          session.close();
        }
      } finally {
        connection.close();
      }
    } catch (JMSException e) {
      throw new EJBException("reply failed", e);
    }
	}

}
