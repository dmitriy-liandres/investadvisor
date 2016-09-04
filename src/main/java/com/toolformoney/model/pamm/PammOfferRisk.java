package com.toolformoney.model.pamm;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.InvestmentTargetOfferRisk;

import java.io.IOException;

/**
 * Class is used to keep and calculate risk
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammOfferRisk extends InvestmentTargetOfferRisk<Pamm, PammOfferProfit> {

    private Double brokerCoefficient;
    private Double pammAgeCoefficient;
    private Double investmentAgeCoefficient;
    private Double pammManagerMoneyCoefficient;
    private Double pammManagerTotalMoneyCoefficient;
    private Double investmentSummCoefficient;
    private Double mainRiskCoefficient;


    @Override
    public Double calculateAndSetRisk(Pamm pamm, ProvidedParams providedParams, PammOfferProfit pammOfferProfit) throws IOException {
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
            case UNI_TRADE:
                brokerCoefficient = 1.;
                break;
            case INSOLT:
                brokerCoefficient = 1.;
                break;
            default:
                throw new RuntimeException("No broker coefficient specified");
        }
        //get currency
        InvestmentTargetOffer offer = pamm.getInvestmentTargetOffers().get(0);

        Double managerMoneyInUsd = YahooExchangeRates.convert(pamm.getManagerMoney(), offer.getCurrency(), Currency.USD);
        Double totalMoneyInUsd = YahooExchangeRates.convert(pamm.getTotalMoney(), offer.getCurrency(), Currency.USD);
        Double providedMoneyInUsd = YahooExchangeRates.convert(providedParams.getSumm(), providedParams.getCurrency(), Currency.USD);

        pammAgeCoefficient = pamm.getAgeInDays() < 90 ? 5 : pamm.getAgeInDays() < 180 ? 2.5 : pamm.getAgeInDays() < 365 ? 1.5 : 1;
        investmentAgeCoefficient = providedParams.getPeriodInDays() > pamm.getAgeInDays() ? 5 : providedParams.getPeriodInDays() > pamm.getAgeInDays() / 2 ? 2.5 : providedParams.getPeriodInDays() > pamm.getAgeInDays() / 3 ? 1.5 : 1;
        pammManagerMoneyCoefficient = managerMoneyInUsd < 1000 ? 3 : managerMoneyInUsd < 3000 ? 2 : managerMoneyInUsd < 10000 ? 1.5 : 1;
        pammManagerTotalMoneyCoefficient = totalMoneyInUsd < 10000 ? 3 : totalMoneyInUsd < 30000 ? 2 : totalMoneyInUsd < 100000 ? 1.5 : 1;
        investmentSummCoefficient = providedMoneyInUsd > totalMoneyInUsd ? 5 : providedMoneyInUsd > totalMoneyInUsd / 2.5 ? 2 : providedMoneyInUsd > pamm.getTotalMoney() / 3 ? 1.5 : 1;
        mainRiskCoefficient = Math.pow(Math.abs(pamm.getLossDaysPercentage() * pamm.getMaxDailyLoss() * pamm.getAverageDailyLoss() * pamm.getDeviation()), 0.25);

        //todo maybe we have to add deposit load to this coefficient in future
        setTotalRisk(brokerCoefficient * pammAgeCoefficient * investmentAgeCoefficient * pammManagerMoneyCoefficient * pammManagerTotalMoneyCoefficient * investmentSummCoefficient * mainRiskCoefficient);
        return getTotalRisk();
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
                "} " + super.toString();
    }
}
