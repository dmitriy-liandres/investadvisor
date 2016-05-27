package com.investadvisor.model;

import com.investadvisor.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Bean for all PAMMS and PAMM based companies
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public class Pamm {

    private PammBroker pammBroker;
    private Integer ageInDays;
    private Double managerMoney;
    private Double totalMoney;
    private String name;
    private String id;
    private Currency currency;
    private List<PammCommission> commissions;
    private Double lossDaysPercentage;
    private Double maxDailyLoss;
    private Double averageDailyLoss;
    private Double deviation;
    private Double avgChange;


    public List<PammCommission> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<PammCommission> commissions) {
        this.commissions = commissions;
    }

    public void addCommission(PammCommission commission) {
        if (commissions == null) {
            commissions = new ArrayList<>();
        }
        commissions.add(commission);
    }

    public Double getAvgChange() {
        return avgChange;
    }

    public void setAvgChange(Double avgChange) {
        this.avgChange = avgChange;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ageInDays=" + ageInDays +
                ", managerMoney=" + managerMoney +
                ", totalMoney=" + totalMoney +
                ", commissions=" + commissions +
                ", lossDaysPercentage=" + lossDaysPercentage +
                ", maxDailyLoss=" + maxDailyLoss +
                ", averageDailyLoss=" + averageDailyLoss +
                ", deviation=" + deviation +
                ", currency=" + currency +
                ", avgChange=" + avgChange +
                '}';
    }
}
