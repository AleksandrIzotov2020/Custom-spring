package org.example;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    @Getter
    private Reflections scanner;
    private Map<Class, Class> ifc2Implclass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2Implclass){
        this.ifc2Implclass = ifc2Implclass;
        this.scanner = new Reflections(packageToScan);
    }
    
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        /*  computeIfAbsent - метод появился в Java 9.
            На вход принимается ключ и лямбда. Если ключ присутствует в мапе, то позвращается значение, иначе
            выполняется лямбда. значение из лямбды сетится в мапу и возвращаеся методом computeIfAbsent.
         * */
        return ifc2Implclass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if(classes.size() != 1){
                throw new RuntimeException(ifc.toString().concat("has 0 or more than one impl please update your config"));
            }

            return classes.iterator().next();
        });
    }
}
