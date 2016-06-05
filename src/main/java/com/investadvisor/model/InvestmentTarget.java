package com.investadvisor.model;

import com.investadvisor.Currency;
import com.investadvisor.model.pamm.InvestmentTargetOffer;

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

    public InvestmentTarget(InvestmentType investmentType, String name) {
        this.investmentType = investmentType;
        this.name = name;
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


    @Override
    public String toString() {
        return "InvestmentTarget{" +
                "name='" + name + '\'' +
                ", investmentType=" + investmentType +
                ", investmentTargetOffers=" + investmentTargetOffers +
                '}';
    }
}
