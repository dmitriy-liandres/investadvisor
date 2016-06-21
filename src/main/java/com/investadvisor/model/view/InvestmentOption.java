package com.investadvisor.model.view;

import com.investadvisor.model.InvestmentType;

/**
 * Author Dmitriy Liandres
 * Date 21.06.2016
 */
public class InvestmentOption {
    private InvestmentType investmentType;
    private String investmentTypeLink;
    private String investmentPartnerName;
    private String investmentOptionName;
    private Double profitPercentage;
    private Double profitMoney;
    private Double totalRisk;
    private String detailsLink;

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }

    public InvestmentType getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(InvestmentType investmentType) {
        this.investmentType = investmentType;
    }

    public String getInvestmentTypeLink() {
        return investmentTypeLink;
    }

    public void setInvestmentTypeLink(String investmentTypeLink) {
        this.investmentTypeLink = investmentTypeLink;
    }

    public String getInvestmentPartnerName() {
        return investmentPartnerName;
    }

    public void setInvestmentPartnerName(String investmentPartnerName) {
        this.investmentPartnerName = investmentPartnerName;
    }

    public String getInvestmentOptionName() {
        return investmentOptionName;
    }

    public void setInvestmentOptionName(String investmentOptionName) {
        this.investmentOptionName = investmentOptionName;
    }

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

    public Double getTotalRisk() {
        return totalRisk;
    }

    public void setTotalRisk(Double totalRisk) {
        this.totalRisk = totalRisk;
    }

    @Override
    public String toString() {
        return "InvestmentOption{" +
                "investmentType=" + investmentType +
                ", investmentTypeLink='" + investmentTypeLink +
                ", investmentPartnerName='" + investmentPartnerName +
                ", investmentOptionName='" + investmentOptionName +
                ", profitPercentage=" + profitPercentage +
                ", profitMoney=" + profitMoney +
                ", totalRisk=" + totalRisk +
                ", detailsLink=" + detailsLink +
                '}';
    }
}
