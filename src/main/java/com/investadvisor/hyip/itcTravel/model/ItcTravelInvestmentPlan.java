package com.investadvisor.hyip.itcTravel.model;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravelInvestmentPlan {

    private String name;
    private Double percentsPerMonth;
    private Double minDays;
    /**
     * If true, than after particular period deposit is closed and user have to reopen again. Otherwise deposit works forever
     */
    private Boolean isDepositClosable;
    private Double minInvestment;

    private String link;

    public ItcTravelInvestmentPlan(String name, Double percentsPerMonth, Double minDays, Boolean isDepositClosable, Double minInvestment, String link) {
        this.name = name;
        this.percentsPerMonth = percentsPerMonth;
        this.minDays = minDays;
        this.isDepositClosable = isDepositClosable;
        this.minInvestment = minInvestment;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public Double getPercentsPerMonth() {
        return percentsPerMonth;
    }

    public Double getAvgPercentage() {
        return (Math.pow((1 + getPercentsPerMonth() / 100), 1. / 30) - 1) * 100;
    }

    public Double getMinDays() {
        return minDays;
    }

    public Double getMinInvestment() {
        return minInvestment;
    }

    public Boolean getIsDepositClosable() {
        return isDepositClosable;
    }

    @Override
    public String toString() {
        return "ItcTravelInvestmentPlan{" +
                "name='" + name + '\'' +
                ", percentsPerMonth=" + percentsPerMonth +
                ", minDays=" + minDays +
                ", isDepositClosable=" + isDepositClosable +
                ", minInvestment=" + minInvestment +
                '}';
    }
}
