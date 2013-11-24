package com.github.marschall.udpadapter;

import java.util.Collections;
import java.util.Enumeration;

import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;

enum UdpConnectionMetaData implements ConnectionMetaData {

  INSTANCE;
  
  @Override
  public String getJMSVersion() throws JMSException {
    return "2.0";
  }

  @Override
  public int getJMSMajorVersion() throws JMSException {
    return 2;
  }

  @Override
  public int getJMSMinorVersion() throws JMSException {
    return 0;
  }

  @Override
  public String getJMSProviderName() throws JMSException {
    return "UDP JMS";
  }

  @Override
  public String getProviderVersion() throws JMSException {
    return "0.1";
  }

  @Override
  public int getProviderMajorVersion() throws JMSException {
    return 0;
  }

  @Override
  public int getProviderMinorVersion() throws JMSException {
    return 1;
  }

  @Override
  public Enumeration getJMSXPropertyNames() throws JMSException {
    // TODO JMSXDeliveryCount
    return Collections.emptyEnumeration();
  }

}
