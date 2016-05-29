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

    private Currency currency;
    private Double avgChange;

    private InvestmentTargetRisk investmentTargetRisk;
    private InvestmentTargetProfit investmentTargetProfit;
    private List<InvestmentTargetOffer> investmentTargetOffers;

    public InvestmentTarget(InvestmentTargetRisk investmentTargetRisk,
                            InvestmentTargetProfit investmentTargetProfit) {
        this.investmentTargetRisk = investmentTargetRisk;
        this.investmentTargetProfit = investmentTargetProfit;
    }

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
    public Double getAvgChange() {
        return avgChange;
    }

    public void setAvgChange(Double avgChange) {
        this.avgChange = avgChange;
    }



    public InvestmentTargetProfit getInvestmentTargetProfit() {
        return investmentTargetProfit;
    }

    public InvestmentTargetRisk getInvestmentTargetRisk() {
        return investmentTargetRisk;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    /**
     * generates link to investment target (PAMM, HIPE and so on)
     */
    public abstract String generateLink();

    public abstract Boolean isHyip();

    public abstract Double getCommissionEnterPercentage();

    public abstract Double getCommissionWithdrawPercentage();

    public abstract Double getCommissionEnterFixed(Currency currency) throws IOException;

    public abstract Double getCommissionWithdrawFixed(Currency currency) throws IOException;

    @Override
    public String toString() {
        return "InvestmentTarget{" +
                "currency=" + currency +
                ", avgChange=" + avgChange +
                ", investmentTargetRisk=" + investmentTargetRisk +
                ", investmentTargetProfit=" + investmentTargetProfit +
                ", investmentTargetOffers=" + investmentTargetOffers +
                '}';
    }
}
