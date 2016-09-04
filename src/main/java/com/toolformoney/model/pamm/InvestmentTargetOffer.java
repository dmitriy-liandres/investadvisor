package com.toolformoney.model.pamm;

import com.toolformoney.Currency;
import com.toolformoney.model.InvestmentTargetOfferProfit;
import com.toolformoney.model.InvestmentTargetOfferRisk;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class InvestmentTargetOffer {

    private Double minInvestment;
    private Double maxInvestment;
    private Double minPeriodInDays;
    private Double maxPeriodInDays;
    private Double commissionFromProfit;
    private String link;
    private Currency currency;
    private Double avgChange;
    private String name;

    private InvestmentTargetOfferRisk investmentTargetOfferRisk;
    private InvestmentTargetOfferProfit investmentTargetOfferProfit;

    public InvestmentTargetOffer(String name,
                                 Double minInvestment,
                                 Double maxInvestment,
                                 Double minPeriodInDays,
                                 Double maxPeriodInDays,
                                 Double commissionFromProfit,
                                 String link,
                                 Currency currency,
                                 Double avgChange,
                                 InvestmentTargetOfferRisk investmentTargetOfferRisk,
                                 InvestmentTargetOfferProfit investmentTargetOfferProfit) {
        this.name = name;
        this.minInvestment = minInvestment;
        this.maxInvestment = maxInvestment;
        this.minPeriodInDays = minPeriodInDays;
        this.maxPeriodInDays = maxPeriodInDays;
        this.commissionFromProfit = commissionFromProfit;
        this.link = link;
        this.currency = currency;
        this.avgChange = avgChange;
        this.investmentTargetOfferRisk = investmentTargetOfferRisk;
        this.investmentTargetOfferProfit = investmentTargetOfferProfit;
    }

    public String getName() {
        return name;
    }

    public InvestmentTargetOfferRisk getInvestmentTargetOfferRisk() {
        return investmentTargetOfferRisk;
    }

    public InvestmentTargetOfferProfit getInvestmentTargetOfferProfit() {
        return investmentTargetOfferProfit;
    }

    public String getLink() {
        return link;
    }

    public Double getMinPeriodInDays() {
        return minPeriodInDays;
    }

    public Double getMaxPeriodInDays() {
        return maxPeriodInDays;
    }

    public Double getMinInvestment() {
        return minInvestment;
    }

    public Double getMaxInvestment() {
        return maxInvestment;
    }

    public Double getCommissionFromProfit() {
        return commissionFromProfit;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Double getAvgChange() {
        return avgChange;
    }

    @Override
    public String toString() {
        return "InvestmentTargetOffer{" +
                "minInvestment=" + minInvestment +
                ", maxInvestment=" + maxInvestment +
                ", minPeriodInDays=" + minPeriodInDays +
                ", maxPeriodInDays=" + maxPeriodInDays +
                ", commissionFromProfit=" + commissionFromProfit +
                ", link='" + link + '\'' +
                ", currency=" + currency +
                ", avgChange=" + avgChange +
                ", name='" + name + '\'' +
                ", investmentTargetOfferRisk=" + investmentTargetOfferRisk +
                ", investmentTargetOfferProfit=" + investmentTargetOfferProfit +
                '}';
    }
}
