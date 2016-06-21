package com.investadvisor.model.pamm;

import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentType;

/**
 * Base Bean for all PAMMS and PAMM based companies
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public abstract class Pamm extends InvestmentTarget {

    private PammBroker pammBroker;
    private Integer ageInDays;
    private Double managerMoney;
    private Double totalMoney;

    private String id;


    private Double lossDaysPercentage;
    private Double maxDailyLoss;
    private Double averageDailyLoss;
    private Double deviation;

    public Pamm(String name, String investmentPartnerLink) {
        super(InvestmentType.PAMM, name, "/памм/", investmentPartnerLink);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PammBroker getPammBroker() {
        return pammBroker;
    }

    public void setPammBroker(PammBroker pammBroker) {
        this.pammBroker = pammBroker;
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
                "pammBroker=" + pammBroker +
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
