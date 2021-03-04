package com.github.marschall.udpadapter;

import java.util.Collections;
import java.util.Enumeration;

import javax.jms.ConnectionMetaData;

enum UdpConnectionMetaData implements ConnectionMetaData {

  INSTANCE;

  @Override
  public String getJMSVersion() {
    return "2.0";
  }

  @Override
  public int getJMSMajorVersion() {
    return 2;
  }

  @Override
  public int getJMSMinorVersion() {
    return 0;
  }

  @Override
  public String getJMSProviderName() {
    return "UDP JMS";
  }

  @Override
  public String getProviderVersion() {
    return "0.1";
  }

  @Override
  public int getProviderMajorVersion() {
    return 0;
  }

  @Override
  public int getProviderMinorVersion() {
    return 1;
  }

  @Override
  public Enumeration getJMSXPropertyNames() {
    // TODO JMSXDeliveryCount
    return Collections.emptyEnumeration();
  }

}
