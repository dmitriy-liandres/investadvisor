package com.toolformoney.model;

import com.toolformoney.model.pamm.PammBroker;

/**
 * Author Dmitriy Liandres
 * Date 11.12.2016
 */
public abstract class ForexInvestmentTarget extends InvestmentTarget {

    private Integer ageInDays;
    private Double managerMoney;
    private Double totalMoney;

    private String id;


    //next values are calculated
    private Double lossDaysPercentage;
    private Double maxDailyLoss;
    private Double averageDailyLoss;
    private Double deviation;

    public ForexInvestmentTarget(InvestmentType investmentType, InvestmentTypeName investmentTypeName, String name, String investmentTypeLink, String investmentPartnerLink) {
        super(investmentType, investmentTypeName, name, investmentTypeLink, investmentPartnerLink);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAgeInDays() {
        return ageInDays;
    }

    public void setAgeInDays(Integer ageInDays) {
        this.ageInDays = ageInDays;
    }

    public Double getManagerMoney() {
        return managerMoney;
    }

    public void setManagerMoney(Double managerMoney) {
        this.managerMoney = managerMoney;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }


    public Double getLossDaysPercentage() {
        return lossDaysPercentage;
    }

    public void setLossDaysPercentage(Double lossDaysPercentage) {
        this.lossDaysPercentage = lossDaysPercentage;
    }

    public Double getMaxDailyLoss() {
        return maxDailyLoss;
    }

    public void setMaxDailyLoss(Double maxDailyLoss) {
        this.maxDailyLoss = maxDailyLoss;
    }

    public Double getAverageDailyLoss() {
        return averageDailyLoss;
    }

    public void setAverageDailyLoss(Double averageDailyLoss) {
        this.averageDailyLoss = averageDailyLoss;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }

    @Override
    public String toString() {
        return "Pamm{" +
                ", ageInDays=" + ageInDays +
                ", managerMoney=" + managerMoney +
                ", totalMoney=" + totalMoney +
                ", id='" + id + '\'' +
                ", lossDaysPercentage=" + lossDaysPercentage +
                ", maxDailyLoss=" + maxDailyLoss +
                ", averageDailyLoss=" + averageDailyLoss +
                ", deviation=" + deviation +
                "} " + super.toString();
    }
}

