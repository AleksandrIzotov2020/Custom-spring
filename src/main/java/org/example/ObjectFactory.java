package org.example;

/*Фабрика должна уметь содзавать объект*/


import lombok.Setter;
import lombok.SneakyThrows;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {
    private final ApplicationContext context;
    private List<BeanPostProcessor> configurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context){
        this.context = context;
        for (Class<? extends BeanPostProcessor> aClass : context.getConfig().getScanner().getSubTypesOf(BeanPostProcessor.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass){

        T t = implClass.getDeclaredConstructor().newInstance();

        //Настройка класса
        configurators.forEach(objectConfigurator->objectConfigurator.configure(t, context));

        return t;
    }
}
