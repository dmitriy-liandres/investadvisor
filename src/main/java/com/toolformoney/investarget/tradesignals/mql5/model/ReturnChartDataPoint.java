package com.toolformoney.investarget.tradesignals.mql5.model;

/**
 * Author Dmitriy Liandres
 * Date 11.12.2016
 */
public class ReturnChartDataPoint {
    private Integer tradeNumber;
    private Double profit;

    public ReturnChartDataPoint(Integer tradeNumber, Double profit) {
        this.tradeNumber = tradeNumber;
        this.profit = profit;
    }

    public Integer getTradeNumber() {
        return tradeNumber;
    }

    public Double getProfit() {
        return profit;
    }
}
