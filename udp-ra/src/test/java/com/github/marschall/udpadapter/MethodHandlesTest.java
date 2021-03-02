package com.github.marschall.udpadapter;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MethodHandlesTest {

  private boolean validatorCalled;
  private boolean listenerCalled;
  private MethodHandle onMessage;

  @BeforeEach
  public void setUp() throws ReflectiveOperationException {
    this.validatorCalled = false;
    this.listenerCalled = false;
    this.onMessage = MethodHandles.lookup().unreflect(MessageListener.class.getMethod("onMessage", Message.class));
  }
  
  @Test
  public void noException() {
    
  }
  
  public void check() {
    
  }
  
  final class SampleMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
      listenerCalled = true;
      
    }
    
  }

}
