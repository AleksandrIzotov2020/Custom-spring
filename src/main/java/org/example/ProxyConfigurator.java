package org.example;

public interface ProxyConfigurator {
    Object replaceWithProxy(Object t, Class implClass);
}
