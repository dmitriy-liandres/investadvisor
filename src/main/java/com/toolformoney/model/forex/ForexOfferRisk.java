package com.toolformoney.model.forex;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.exchangerates.FixerIOExchangeRates;
import com.toolformoney.model.ForexInvestmentTarget;
import com.toolformoney.model.InvestmentTargetOfferProfitCalculation;
import com.toolformoney.model.InvestmentTargetOfferRisk;
import com.toolformoney.model.pamm.InvestmentTargetOffer;

import java.io.IOException;

/**
 * Class is used to keep and calculate risk
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public abstract class ForexOfferRisk<T extends ForexInvestmentTarget> extends InvestmentTargetOfferRisk<T> {


    @Override
    public Double calculateAndSetRisk(T forexInvestmentTarget, ProvidedParams providedParams, InvestmentTargetOfferProfitCalculation investmentTargetOfferProfitCalculation) throws IOException {
        Double pammAgeCoefficient;
        Double investmentAgeCoefficient;
        Double pammManagerMoneyCoefficient;
        Double pammManagerTotalMoneyCoefficient;
        Double investmentSummCoefficient;
        Double mainRiskCoefficient;

        //get currency
        InvestmentTargetOffer offer = forexInvestmentTarget.getInvestmentTargetOffers().get(0);

        Double managerMoneyInUsd = FixerIOExchangeRates.convert(forexInvestmentTarget.getManagerMoney(), offer.getCurrency(), Currency.USD);
        Double totalMoneyInUsd = FixerIOExchangeRates.convert(forexInvestmentTarget.getTotalMoney(), offer.getCurrency(), Currency.USD);
        Double providedMoneyInUsd = FixerIOExchangeRates.convert(providedParams.getSumm(), providedParams.getCurrency(), Currency.USD);
        Double providedPeriodInDays = providedParams.getPeriodInDays();

        pammAgeCoefficient = forexInvestmentTarget.getAgeInDays() < 30 ? 10 : forexInvestmentTarget.getAgeInDays() < 90 ? 5. : forexInvestmentTarget.getAgeInDays() < 180 ? 2.5 : forexInvestmentTarget.getAgeInDays() < 365 ? 1.5 : 1.;
        investmentAgeCoefficient = providedPeriodInDays > forexInvestmentTarget.getAgeInDays() ? 10. : providedPeriodInDays > forexInvestmentTarget.getAgeInDays() / 2 ? 5. : providedPeriodInDays > forexInvestmentTarget.getAgeInDays() / 4 ? 2.5 : providedPeriodInDays > forexInvestmentTarget.getAgeInDays() / 8 ? 1.25 : 1.;
        pammManagerMoneyCoefficient = managerMoneyInUsd < 1000 ? 3. : managerMoneyInUsd < 3000 ? 2. : managerMoneyInUsd < 10000 ? 1.25 : 1.;
        pammManagerTotalMoneyCoefficient = totalMoneyInUsd < 10000 ? 3. : totalMoneyInUsd < 25000 ? 2. : totalMoneyInUsd < 50000 ? 1.5 : 1.;
        investmentSummCoefficient = providedMoneyInUsd > totalMoneyInUsd ? 5. : providedMoneyInUsd > totalMoneyInUsd / 2 ? 3. : providedMoneyInUsd > totalMoneyInUsd / 4 ? 2. : providedMoneyInUsd > totalMoneyInUsd / 8 ? 1.25 : 1.;


        int mainRiskCoefficientsNumber = 4;
        Double additionalMainCoefficient = getAdditionalMainCoefficient(forexInvestmentTarget, providedParams);
        mainRiskCoefficient = forexInvestmentTarget.getLossDaysPercentage() * forexInvestmentTarget.getMaxDailyLoss() * forexInvestmentTarget.getAverageDailyLoss() * forexInvestmentTarget.getDeviation();
        if (additionalMainCoefficient != null) {
            mainRiskCoefficientsNumber++;
            mainRiskCoefficient *= additionalMainCoefficient;
        }
        mainRiskCoefficient = Math.pow(Math.abs(mainRiskCoefficient), 1. / mainRiskCoefficientsNumber);

        //todo maybe we have to add deposit load to this coefficient in future
        Double additionalCoefficient = getAdditionalCoefficient(forexInvestmentTarget, providedParams);
        double risk = additionalCoefficient * pammAgeCoefficient * investmentAgeCoefficient * pammManagerMoneyCoefficient * pammManagerTotalMoneyCoefficient * investmentSummCoefficient * mainRiskCoefficient;
        return risk;
    }

    public abstract Double getAdditionalCoefficient(T forexInvestmentTarget, ProvidedParams providedParams);

    public abstract Double getAdditionalMainCoefficient(T forexInvestmentTarget, ProvidedParams providedParams);

    @Override
    public String toString() {
        return "ForexOfferRisk{} " + super.toString();
    }
}
