package com.github.marschall.udpadapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Proxy;

final class ClassLoaderObjectInputStream extends ObjectInputStream {

  private final ClassLoader classLoader;

  ClassLoaderObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
    super(in);
    this.classLoader = classLoader;
  }

  @Override
  protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
    String className = desc.getName();
    return this.classLoader.loadClass(className);
  }

  @Override
  protected Class<?> resolveProxyClass(String[] interfaceNames) throws IOException, ClassNotFoundException {
    Class<?>[] interfaceClasses = new Class[interfaceNames.length];
    for (int i = 0; i < interfaceNames.length; i++) {
      String interfaceName = interfaceNames[i];
      interfaceClasses[i] = this.classLoader.loadClass(interfaceName);
    }
    return Proxy.getProxyClass(this.classLoader, interfaceClasses);
  }

}
