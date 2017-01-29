package com.toolformoney.investarget.tradesignals.mql5.model;

/**
 * Author Dmitriy Liandres
 * Date 18.12.2016
 */
public class EquityChartBalance {
    private Long time;
    private Double value;

    public EquityChartBalance(Long time, Double value) {
        this.time = time;
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public Double getValue() {
        return value;
    }
}
