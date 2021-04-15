package org.example;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for(Field field: t.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(InjectByType.class)){
                field.setAccessible(true);
                Object object = context.getBean(field.getType());
                field.set(t, object);
            }
        }
    }
}
