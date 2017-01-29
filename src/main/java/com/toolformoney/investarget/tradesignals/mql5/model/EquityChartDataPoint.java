package com.toolformoney.investarget.tradesignals.mql5.model;

/**
 * Author Dmitriy Liandres
 * Date 18.12.2016
 */
public class EquityChartDataPoint {
    private Long time;
    private Double balance;
    private Double equity;

    public EquityChartDataPoint(Long time, Double balance, Double equity) {
        this.time = time;
        this.balance = balance;
        this.equity = equity;
    }

    public Long getTime() {
        return time;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getEquity() {
        return equity;
    }
}
