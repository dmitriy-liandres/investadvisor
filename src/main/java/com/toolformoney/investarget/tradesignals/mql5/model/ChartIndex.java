package com.toolformoney.investarget.tradesignals.mql5.model;

import java.util.Map;

/**
 * Author Dmitriy Liandres
 * Date 11.12.2016
 */
public class ChartIndex {
    Map<String, ChartIndexData> tradesPerMonth;

    public Map<String, ChartIndexData> getTradesPerMonth() {
        return tradesPerMonth;
    }

    public void setTradesPerMonth(Map<String, ChartIndexData> tradesPerMonth) {
        this.tradesPerMonth = tradesPerMonth;
    }
}
