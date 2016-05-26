package com.investadvisor.exchangerates.model;

import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class YahooExchangeRateResponseResults {

    private List<YahooExchangeRateResponseRate> rate;

    public List<YahooExchangeRateResponseRate> getRate() {
        return rate;
    }

    public void setRate(List<YahooExchangeRateResponseRate> rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "YahooExchangeRateResponseResults{" +
                "rate=" + rate +
                '}';
    }
}
