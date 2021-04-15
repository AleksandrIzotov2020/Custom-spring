package org.example;

@Singleton
public class RecommendatorImpl implements Recommendator {

    @InjectProperty
    private String drink;

    @Override
    public void recommend() {
        System.out.println("to protect from covid-2019, drink ".concat(drink));
    }
}
