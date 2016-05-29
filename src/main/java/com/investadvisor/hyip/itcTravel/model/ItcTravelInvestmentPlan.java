package com.investadvisor.hyip.itcTravel.model;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravelInvestmentPlan {

    private String name;
    private Double percentsPerMonth;
    private Integer minDays;
    /**
     * If true, than after particular period deposit is closed and user have to reopen again. Otherwise deposit works forever
     */
    private Boolean isDepositClosed;
    private Double minInvestment;

    public ItcTravelInvestmentPlan(String name, Double percentsPerMonth, Integer minDays, Boolean isDepositClosed, Double minInvestment) {
        this.name = name;
        this.percentsPerMonth = percentsPerMonth;
        this.minDays = minDays;
        this.isDepositClosed = isDepositClosed;
        this.minInvestment = minInvestment;
    }

    public String getName() {
        return name;
    }

    public Double getPercentsPerMonth() {
        return percentsPerMonth;
    }

    public Integer getMinDays() {
        return minDays;
    }

    public Double getMinInvestment() {
        return minInvestment;
    }

    public Boolean getIsDepositClosed() {
        return isDepositClosed;
    }

    @Override
    public String toString() {
        return "ItcTravelInvestmentPlan{" +
                "name='" + name + '\'' +
                ", percentsPerMonth=" + percentsPerMonth +
                ", minDays=" + minDays +
                ", isDepositClosed=" + isDepositClosed +
                ", minInvestment=" + minInvestment +
                '}';
    }
}
