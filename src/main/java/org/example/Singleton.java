package org.example;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) /*Кэширует объекты*/
public @interface Singleton {
}
