package com.investadvisor.model.pamm;

import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetProfit;
import com.investadvisor.model.InvestmentTargetRisk;

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
    private String name;
    private String id;


    private Double lossDaysPercentage;
    private Double maxDailyLoss;
    private Double averageDailyLoss;
    private Double deviation;

    public Pamm(InvestmentTargetRisk investmentTargetRisk, InvestmentTargetProfit investmentTargetProfit) {
        super(investmentTargetRisk, investmentTargetProfit);
    }

    public Pamm() {
        super(new PammRisk(), new PammProfit());
    }

    public Boolean isHyip() {
        return false;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", lossDaysPercentage=" + lossDaysPercentage +
                ", maxDailyLoss=" + maxDailyLoss +
                ", averageDailyLoss=" + averageDailyLoss +
                ", deviation=" + deviation +
                "} " + super.toString();
    }
}
