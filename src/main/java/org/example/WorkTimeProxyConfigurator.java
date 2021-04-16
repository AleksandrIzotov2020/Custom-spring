package org.example;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WorkTimeProxyConfigurator implements ProxyConfigurator{
    @Override
    public Object replaceWithProxy(Object t, Class implClass) {
        if (implClass.isAnnotationPresent(WorkTime.class)){
            if (implClass.getInterfaces().length == 0){
                return Enhancer.create(implClass, (net.sf.cglib.proxy.InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(t, method, args));
            }
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(t, method, args));
        }else return t;
    }

    private Object getInvocationHandlerLogic(Object t, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        if(!method.isAnnotationPresent(WorkTime.class)) return method.invoke(t, args);
        Object response;

        Long start = System.nanoTime();
        response = method.invoke(t, args);
        Long elapsed = System.nanoTime() - start;
        System.out.println("Время выполнения, nano: ".concat(elapsed.toString()).concat(". Method: ".concat(method.getName())));

        return response;
    }
}
