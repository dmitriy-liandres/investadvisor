package com.toolformoney.model;

import com.toolformoney.Currency;
import com.toolformoney.model.pamm.InvestmentTargetOffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class InvestmentTarget {

    private String name;
    private InvestmentType investmentType;
    private InvestmentTypeName investmentTypeName;
    private String investmentTypeLink;
    private String investmentPartnerLink;

    public InvestmentTarget(InvestmentType investmentType,
                            InvestmentTypeName investmentTypeName,
                            String name,
                            String investmentTypeLink,
                            String investmentPartnerLink) {
        this.investmentType = investmentType;
        this.investmentTypeName = investmentTypeName;
        this.name = name;
        this.investmentTypeLink = investmentTypeLink;
        this.investmentPartnerLink = investmentPartnerLink;
    }

    private List<InvestmentTargetOffer> investmentTargetOffers;


    public List<InvestmentTargetOffer> getInvestmentTargetOffers() {
        return investmentTargetOffers;
    }

    public void setInvestmentTargetOffers(List<InvestmentTargetOffer> investmentTargetOffers) {
        this.investmentTargetOffers = investmentTargetOffers;
    }

    public void addOffer(InvestmentTargetOffer offer) {
        if (investmentTargetOffers == null) {
            investmentTargetOffers = new ArrayList<>();
        }
        investmentTargetOffers.add(offer);
    }

    public InvestmentType getInvestmentType() {
        return investmentType;
    }

    public abstract Double getCommissionEnterPercentage();

    public abstract Double getCommissionWithdrawPercentage();

    public abstract Double getCommissionEnterFixed(Currency currency) throws IOException;

    public abstract Double getCommissionWithdrawFixed(Currency currency) throws IOException;

    public String getName() {
        return name;
    }

    public String getInvestmentTypeLink() {
        return investmentTypeLink;
    }

    public String getInvestmentPartnerLink() {
        return investmentPartnerLink;
    }

    public void setInvestmentType(InvestmentType investmentType) {
        this.investmentType = investmentType;
    }

    public void setInvestmentTypeLink(String investmentTypeLink) {
        this.investmentTypeLink = investmentTypeLink;
    }

    public InvestmentTypeName getInvestmentTypeName() {
        return investmentTypeName;
    }

    @Override
    public String toString() {
        return "InvestmentTarget{" +
                "name='" + name + '\'' +
                ", investmentType=" + investmentType +
                ", investmentTypeName=" + investmentTypeName +
                ", investmentTypeLink='" + investmentTypeLink + '\'' +
                ", investmentPartnerLink='" + investmentPartnerLink + '\'' +
                ", investmentTargetOffers=" + investmentTargetOffers +
                '}';
    }
}
