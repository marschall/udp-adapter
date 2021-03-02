package com.github.marschall.udpadapter;

import static java.lang.invoke.MethodType.methodType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MethodHandlesTest {

  private static final MethodHandle ON_MESSAGE;

  static {
    try {
      ON_MESSAGE = MethodHandles.lookup().findVirtual(MessageListener.class, "onMessage", methodType(Void.TYPE, Message.class));
    } catch (ReflectiveOperationException e) {
      throw new AssertionError("could not look up MessageListener#onMessage");
    }
  }

  private boolean validatorCalled;
  private boolean listenerCalled;

  @BeforeEach
  public void setUp() throws ReflectiveOperationException {
    this.validatorCalled = false;
    this.listenerCalled = false;
  }

  @Test
  public void noException() throws Throwable {
    assertFalse(listenerCalled);

    MessageListener messageListener = new SampleMessageListener();
    Message mesage = mock(Message.class);
    ON_MESSAGE.invokeExact(messageListener, mesage);

    assertTrue(listenerCalled);
  }

  final class SampleMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
      listenerCalled = true;
    }

  }

}
