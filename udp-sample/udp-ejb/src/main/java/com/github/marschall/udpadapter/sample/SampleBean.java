package com.github.marschall.udpadapter.sample;

import java.util.logging.Logger;

import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven
public class SampleBean implements MessageListener {
	
	static final Logger LOG = Logger.getLogger(SampleBean.class.getName());

	@Override
	public void onMessage(Message message) {
		LOG.info("onMessage");
	}

}
