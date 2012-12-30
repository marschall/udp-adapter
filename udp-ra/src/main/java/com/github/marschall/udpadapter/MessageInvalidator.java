package com.github.marschall.udpadapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.jms.JMSException;
import javax.jms.Message;

final class MessageInvalidator implements InvocationHandler {
  
  private volatile boolean valid;
  
  private final Message delegate;
  
  MessageInvalidator(Message delegate) {
    this.delegate = delegate;
    this.valid = true;
  }
  
  void invalidate() {
    this.valid = false;
  }
  
  private void check() throws JMSException {
    if (!this.valid) {
      throw new JMSException("message no longer valid");
    }
  }
  
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String methodName = method.getName();
    switch (methodName) {
      case "toString":
        if (args != null && args.length > 0) {
          throw new AssertionError("#toString() not expected to have any arguments");
        }
        return this.getClass().getName() + '@' + Integer.toHexString(this.hashCode());
      case "equals":
        if (args == null || args.length != 1) {
          throw new AssertionError("#equals() expected to exactly one argument");
        }
        return proxy == args[0];
      case "hashCode":
        if (args != null && args.length > 0) {
          throw new AssertionError("#hashCode() not expected to have any arguments");
        }
        return System.identityHashCode(proxy);
      default:
        try {
          this.check();
          return method.invoke(this.delegate, args);
        } catch (InvocationTargetException e) {
          throw e.getCause();
        }
    }
  }
  

}
