package com.github.marschall.udpadapter.sample;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.ejb3.annotation.ResourceAdapter;

// if you don't want this you have to switch the default jboss resource adapter
// from hornetq
@ResourceAdapter("udp-rar.rar")
public class SampleBean implements MessageListener {
	
	static final Logger LOG = Logger.getLogger(SampleBean.class.getName());

	@Override
	public void onMessage(Message message) {
		LOG.info("onMessage");
	}

}
