package com.toolformoney.model.tradesignals;

import com.toolformoney.model.ForexInvestmentTarget;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.InvestmentTypeName;

/**
 * Author Dmitriy Liandres
 * Date 11.12.2016
 */
public abstract class TradeSignal extends ForexInvestmentTarget {
    //average equity risk in percents
    private Double averageEquityRisk;
    //max equity risk in percents
    private Double maxEquityRisk;
    private Double averageLot;
    private Double tradesNumber;
    //number of times when money were added
    private Double addsNumber;

    public TradeSignal(InvestmentTypeName investmentTypeName, String name, String investmentPartnerLink) {
        super(InvestmentType.TRADE_SIGNAL, investmentTypeName, name, "/vklady-investitsii/torgovye-signaly/", investmentPartnerLink);
    }

    public Double getAverageEquityRisk() {
        return averageEquityRisk;
    }

    public void setAverageEquityRisk(Double averageEquityRisk) {
        this.averageEquityRisk = averageEquityRisk;
    }

    public Double getMaxEquityRisk() {
        return maxEquityRisk;
    }

    public void setMaxEquityRisk(Double maxEquityRisk) {
        this.maxEquityRisk = maxEquityRisk;
    }

    public Double getTradesNumber() {
        return tradesNumber;
    }

    public void setTradesNumber(Double tradesNumber) {
        this.tradesNumber = tradesNumber;
    }

    public Double getAverageLot() {
        return averageLot;
    }

    public void setAverageLot(Double averageLot) {
        this.averageLot = averageLot;
    }

    public Double getAddsNumber() {
        return addsNumber;
    }

    public void setAddsNumber(Double addsNumber) {
        this.addsNumber = addsNumber;
    }
}
