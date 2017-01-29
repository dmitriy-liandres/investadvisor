package com.toolformoney.model;

/**
 * Author Dmitriy Liandres
 * Date 22.12.2016
 */
public class InvestmentTargetOfferProfitCalculation {

    private Double profitPercentage;
    private Double profitMoney;
    private Boolean isSuitsUser;

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(Double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public Double getProfitMoney() {
        return profitMoney;
    }

    public void setProfitMoney(Double profitMoney) {
        this.profitMoney = profitMoney;
    }

    public Boolean getIsSuitsUser() {
        return isSuitsUser;
    }

    public void setIsSuitsUser(Boolean isSuitsUser) {
        this.isSuitsUser = isSuitsUser;
    }
}
