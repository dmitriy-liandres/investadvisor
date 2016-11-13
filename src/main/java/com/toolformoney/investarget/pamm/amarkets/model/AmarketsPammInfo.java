package com.toolformoney.investarget.pamm.amarkets.model;

import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 11.11.2016
 */
public class AmarketsPammInfo {

    private String id;
    private String name;
    private List<List<Double>> percent_rates;
    private Double payment_period;
    private Double capital;//investments
    private Double current_mt_balance;//current master capital
    private List<List<String>> graph_profits;

    private Integer trading_days;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Double>> getPercent_rates() {
        return percent_rates;
    }

    public void setPercent_rates(List<List<Double>> percent_rates) {
        this.percent_rates = percent_rates;
    }

    public Double getPayment_period() {
        return payment_period;
    }

    public void setPayment_period(Double payment_period) {
        this.payment_period = payment_period;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Double getCurrent_mt_balance() {
        return current_mt_balance;
    }

    public void setCurrent_mt_balance(Double current_mt_balance) {
        this.current_mt_balance = current_mt_balance;
    }

    public Integer getTrading_days() {
        return trading_days;
    }

    public void setTrading_days(Integer trading_days) {
        this.trading_days = trading_days;
    }

    public List<List<String>> getGraph_profits() {
        return graph_profits;
    }

    public void setGraph_profits(List<List<String>> graph_profits) {
        this.graph_profits = graph_profits;
    }

    @Override
    public String toString() {
        return "AmarketsPammInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", percent_rates=" + percent_rates +
                ", payment_period=" + payment_period +
                ", capital=" + capital +
                ", current_mt_balance=" + current_mt_balance +
                ", trading_days=" + trading_days +
                ", graph_profits=" + graph_profits +
                '}';
    }
}
