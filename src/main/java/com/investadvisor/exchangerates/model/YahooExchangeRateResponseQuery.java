package com.investadvisor.exchangerates.model;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class YahooExchangeRateResponseQuery {

    private YahooExchangeRateResponseResults results;

    public YahooExchangeRateResponseResults getResults() {
        return results;
    }

    public void setResults(YahooExchangeRateResponseResults results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "YahooExchangeRateResponseQuery{" +
                "results=" + results +
                '}';
    }
}
