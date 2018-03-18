package com.toolformoney.exchangerates.model.fixerIO;

import java.util.Map;

/**
 * Author Dmitriy Liandres
 * Date 18.03.2018
 */
public class FixerIOResponse {
    private Map<String, Double> rates;

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    //    private FixerIORates rates;
//
//    public FixerIORates getRates() {
//        return rates;
//    }
//
//    public void setRates(FixerIORates rates) {
//        this.rates = rates;
//    }
//
//    @Override
//    public String toString() {
//        return "FixerIOResponse{" +
//                "rates=" + rates +
//                '}';
//    }
}
