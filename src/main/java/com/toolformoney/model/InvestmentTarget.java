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
    private String investmentTypeLink;
    private String investmentPartnerLink;

    public InvestmentTarget(InvestmentType investmentType,
                            String name,
                            String investmentTypeLink,
                            String investmentPartnerLink) {
        this.investmentType = investmentType;
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

    @Override
    public String toString() {
        return "InvestmentTarget{" +
                "name='" + name + '\'' +
                ", investmentType=" + investmentType +
                ", investmentTypeLink='" + investmentTypeLink + '\'' +
                ", investmentPartnerLink='" + investmentPartnerLink + '\'' +
                ", investmentTargetOffers=" + investmentTargetOffers +
                '}';
    }
}
