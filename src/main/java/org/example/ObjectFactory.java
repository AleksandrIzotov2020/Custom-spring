package org.example;

/*Фабрика должна уметь содзавать объект*/


import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory {

    private static ObjectFactory ourInstance = new ObjectFactory();
    private List<BeanPostProcessor> configurators = new ArrayList<>();
    private Config config;

    public static ObjectFactory getInstance(){return ourInstance;}

    @SneakyThrows
    private ObjectFactory(){
        config = new JavaConfig("org.example", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        for (Class<? extends BeanPostProcessor> aClass : config.getScanner().getSubTypesOf(BeanPostProcessor.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T getBean(Class<T> type){
        Class<? extends T> implClass = type;
        if(type.isInterface()){
            implClass = config.getImplClass(type);
        }

        T t = implClass.getDeclaredConstructor().newInstance();

        //Настройка класса
        configurators.forEach(objectConfigurator->objectConfigurator.configure(t));

        return t;
    }
}
