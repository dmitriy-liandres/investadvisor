package com.investadvisor.exchangerates.model;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class YahooExchangeRateResponse {
    private YahooExchangeRateResponseQuery query;

    public YahooExchangeRateResponseQuery getQuery() {
        return query;
    }

    public void setQuery(YahooExchangeRateResponseQuery query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "YahooExchangeRateResponse{" +
                "query=" + query +
                '}';
    }
}


