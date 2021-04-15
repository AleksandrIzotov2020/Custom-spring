package org.example;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("org.example", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        CoronaDesinfector coronaDesinfector = context.getBean(CoronaDesinfector.class);
        coronaDesinfector.start(new Room());
    }
}
