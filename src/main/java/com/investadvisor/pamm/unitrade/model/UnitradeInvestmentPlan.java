package com.investadvisor.pamm.unitrade.model;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class UnitradeInvestmentPlan {
    private String name;
    private Integer days;
    private Integer monthsNumber;
    private Double percentagePerMonth;
    private Double minimalInvestment;

    public UnitradeInvestmentPlan(String name, Integer days, Double percentagePerMonth, Integer monthsNumber, Double minimalInvestment) {
        this.name = name;
        this.days = days;
        this.monthsNumber = monthsNumber;
        this.percentagePerMonth = percentagePerMonth;
        this.minimalInvestment = minimalInvestment;
    }

    public Double getMinimalInvestment() {
        return minimalInvestment;
    }

    public void setMinimalInvestment(Double minimalInvestment) {
        this.minimalInvestment = minimalInvestment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Double getPercentagePerMonth() {
        return percentagePerMonth;
    }

    public void setPercentagePerMonth(Double percentagePerMonth) {
        this.percentagePerMonth = percentagePerMonth;
    }

    public Integer getMonthsNumber() {
        return monthsNumber;
    }

    public void setMonthsNumber(Integer monthsNumber) {
        this.monthsNumber = monthsNumber;
    }

    @Override
    public String toString() {
        return "InvestmentPlan{" +
                "name='" + name + '\'' +
                ", days=" + days +
                ", monthsNumber=" + monthsNumber +
                ", percentagePerMonth=" + percentagePerMonth +
                ", minimalInvestment=" + minimalInvestment +
                '}';
    }
}
