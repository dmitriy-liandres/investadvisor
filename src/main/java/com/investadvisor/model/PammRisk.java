package com.investadvisor.model;

import com.investadvisor.Currency;
import com.investadvisor.ProvidedParams;
import com.investadvisor.exchangerates.YahooExchangeRates;

import java.io.IOException;

/**
 * Class is used to keep and calculate risk
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammRisk {

    private Double brokerCoefficient;
    private Double pammAgeCoefficient;
    private Double investmentAgeCoefficient;
    private Double pammManagerMoneyCoefficient;
    private Double pammManagerTotalMoneyCoefficient;
    private Double investmentSummCoefficient;
    private Double mainRiskCoefficient;
    private Double totalRisk;

    public Double getBrokerCoefficient() {
        return brokerCoefficient;
    }

    public void setBrokerCoefficient(Double brokerCoefficient) {
        this.brokerCoefficient = brokerCoefficient;
    }

    public Double getPammAgeCoefficient() {
        return pammAgeCoefficient;
    }

    public void setPammAgeCoefficient(Double pammAgeCoefficient) {
        this.pammAgeCoefficient = pammAgeCoefficient;
    }

    public Double getInvestmentAgeCoefficient() {
        return investmentAgeCoefficient;
    }

    public void setInvestmentAgeCoefficient(Double investmentAgeCoefficient) {
        this.investmentAgeCoefficient = investmentAgeCoefficient;
    }

    public Double getPammManagerMoneyCoefficient() {
        return pammManagerMoneyCoefficient;
    }

    public void setPammManagerMoneyCoefficient(Double pammManagerMoneyCoefficient) {
        this.pammManagerMoneyCoefficient = pammManagerMoneyCoefficient;
    }

    public Double getPammManagerTotalMoneyCoefficient() {
        return pammManagerTotalMoneyCoefficient;
    }

    public void setPammManagerTotalMoneyCoefficient(Double pammManagerTotalMoneyCoefficient) {
        this.pammManagerTotalMoneyCoefficient = pammManagerTotalMoneyCoefficient;
    }

    public Double getInvestmentSummCoefficient() {
        return investmentSummCoefficient;
    }

    public void setInvestmentSummCoefficient(Double investmentSummCoefficient) {
        this.investmentSummCoefficient = investmentSummCoefficient;
    }

    public Double getTotalRisk() {
        return totalRisk;
    }

    public void calculateRisk(Pamm pamm, ProvidedParams providedParams) throws IOException {
        brokerCoefficient = 0.;
        switch (pamm.getPammBroker()) {
            case ALFA_FOREX:
                brokerCoefficient = 1.;
                break;
            case ALPARI:
                brokerCoefficient = 1.;
                break;
            case FXOPEN:
                brokerCoefficient = 1.;
                break;
        }

        Double managerMoneyInUsd = YahooExchangeRates.convertToUSD(pamm.getManagerMoney(), pamm.getCurrency(), Currency.USD);
        Double totalMoneyInUsd = YahooExchangeRates.convertToUSD(pamm.getTotalMoney(), pamm.getCurrency(), Currency.USD);
        Double providedMoneyInUsd = YahooExchangeRates.convertToUSD(providedParams.getSumm(), providedParams.getCurrency(), Currency.USD);

        pammAgeCoefficient = pamm.getAgeInDays() < 90 ? 3 : pamm.getAgeInDays() < 180 ? 2 : pamm.getAgeInDays() < 365 ? 1.5 : 1;
        investmentAgeCoefficient = providedParams.getPeriodInDays() > pamm.getAgeInDays() ? 3 : providedParams.getPeriodInDays() > pamm.getAgeInDays() / 2 ? 2 : providedParams.getPeriodInDays() > pamm.getAgeInDays() / 3 ? 1.5 : 1;
        pammManagerMoneyCoefficient = managerMoneyInUsd < 1000 ? 3 : managerMoneyInUsd < 3000 ? 2 : managerMoneyInUsd < 10000 ? 1.5 : 1;
        pammManagerTotalMoneyCoefficient = totalMoneyInUsd < 10000 ? 3 : totalMoneyInUsd < 30000 ? 2 : totalMoneyInUsd < 100000 ? 1.5 : 1;
        investmentSummCoefficient = providedMoneyInUsd > totalMoneyInUsd ? 3 : providedMoneyInUsd > totalMoneyInUsd / 2 ? 2 : providedMoneyInUsd > pamm.getTotalMoney() / 3 ? 1.5 : 1;
        mainRiskCoefficient = Math.pow(Math.abs(pamm.getLossDaysPercentage() * pamm.getMaxDailyLoss() * pamm.getAverageDailyLoss() * pamm.getDeviation()), 0.25);

        totalRisk = brokerCoefficient * pammAgeCoefficient * investmentAgeCoefficient * pammManagerMoneyCoefficient * pammManagerTotalMoneyCoefficient * investmentSummCoefficient * mainRiskCoefficient;

    }


    @Override
    public String toString() {
        return "PammRisk{" +
                "brokerCoefficient=" + brokerCoefficient +
                ", pammAgeCoefficient=" + pammAgeCoefficient +
                ", investmentAgeCoefficient=" + investmentAgeCoefficient +
                ", pammManagerMoneyCoefficient=" + pammManagerMoneyCoefficient +
                ", pammManagerTotalMoneyCoefficient=" + pammManagerTotalMoneyCoefficient +
                ", investmentSummCoefficient=" + investmentSummCoefficient +
                ", mainRiskCoefficient=" + mainRiskCoefficient +
                ", totalRisk=" + totalRisk +
                '}';
    }
}
