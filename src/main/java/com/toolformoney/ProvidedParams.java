package com.toolformoney;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class ProvidedParams {
    private Double summ;
    private Double periodInDays;
    private Currency currency;
    private Integer maxAllowedRisk;

    public ProvidedParams() {
    }

    public ProvidedParams(Double summ, Double periodInDays, Currency currency, Integer maxAllowedRisk) {
        this.summ = summ;
        this.periodInDays = periodInDays;
        this.currency = currency;
        this.maxAllowedRisk = maxAllowedRisk;
    }

    public Integer getMaxAllowedRisk() {
        return maxAllowedRisk;
    }

    public void setMaxAllowedRisk(Integer maxAllowedRisk) {
        this.maxAllowedRisk = maxAllowedRisk;
    }

    public Double getSumm() {
        return summ;
    }

    public void setSumm(Double summ) {
        this.summ = summ;
    }

    public Double getPeriodInDays() {
        return periodInDays;
    }

    public void setPeriodInDays(Double periodInDays) {
        this.periodInDays = periodInDays;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
