package org.example;

/*Фабрика должна уметь содзавать объект*/


import lombok.Setter;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class ObjectFactory {
    private final ApplicationContext context;
    private List<BeanPostProcessor> configurators = new ArrayList<>();
    private  List<ProxyConfigurator> proxy = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context){
        this.context = context;
        for (Class<? extends BeanPostProcessor> aClass : context.getConfig().getScanner().getSubTypesOf(BeanPostProcessor.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxy.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass){
        //Создаем объект
        T t = create(implClass);
        //Настройка объекта
        configure(t);
        //Запускаем все init методы (Вторичный конструктор)
        invokeInit(implClass, t);
        //Проктируем класс при необходимости
        t = wrapWithProxy(implClass, t);

        return t;
    }

    private <T> T wrapWithProxy(Class<T> implClass, T t) {
        for (ProxyConfigurator proxyConfigurator : proxy) {
            t = (T) proxyConfigurator.replaceWithProxy(t, implClass);
        }
        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method: implClass.getMethods())
            if (method.isAnnotationPresent(PostConstruct.class)){
                method.invoke(t);
            }
    }

    private  <T> void configure(T t){
        configurators.forEach(objectConfigurator->objectConfigurator.configure(t, context));
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}
