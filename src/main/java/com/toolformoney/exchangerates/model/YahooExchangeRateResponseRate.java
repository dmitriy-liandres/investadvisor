package com.toolformoney.exchangerates.model;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class YahooExchangeRateResponseRate {

    private String rate;
    private String name;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "YahooExchangeRateResponseRate{" +
                "name='" + name + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}

