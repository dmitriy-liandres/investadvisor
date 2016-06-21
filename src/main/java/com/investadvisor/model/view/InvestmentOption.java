package com.investadvisor.model.view;

import com.investadvisor.model.InvestmentType;

/**
 * Author Dmitriy Liandres
 * Date 21.06.2016
 */
public class InvestmentOption {
    private String investmentTypeName;
    private String investmentTypeLink;
    private String investmentPartnerName;
    private String investmentPartnerLink;
    private String investmentOptionName;
    private Double profitPercentage;
    private Double totalRisk;
    private String detailsLink;

    public String getInvestmentPartnerLink() {
        return investmentPartnerLink;
    }

    public void setInvestmentPartnerLink(String investmentPartnerLink) {
        this.investmentPartnerLink = investmentPartnerLink;
    }

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }

    public String getInvestmentTypeName() {
        return investmentTypeName;
    }

    public void setInvestmentTypeName(String investmentTypeName) {
        this.investmentTypeName = investmentTypeName;
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

    public Double getTotalRisk() {
        return totalRisk;
    }

    public void setTotalRisk(Double totalRisk) {
        this.totalRisk = totalRisk;
    }

    @Override
    public String toString() {
        return "InvestmentOption{" +
                "investmentTypeName=" + investmentTypeName +
                ", investmentTypeLink='" + investmentTypeLink + '\'' +
                ", investmentPartnerName='" + investmentPartnerName + '\'' +
                ", investmentPartnerLink='" + investmentPartnerLink + '\'' +
                ", investmentOptionName='" + investmentOptionName + '\'' +
                ", profitPercentage=" + profitPercentage +
                ", totalRisk=" + totalRisk +
                ", detailsLink='" + detailsLink + '\'' +
                '}';
    }
}
