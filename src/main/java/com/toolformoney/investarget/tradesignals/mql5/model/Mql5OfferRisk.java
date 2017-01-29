package com.toolformoney.investarget.tradesignals.mql5.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.forex.ForexOfferRisk;
import com.toolformoney.model.tradesignals.TradeSignal;

/**
 * Author Dmitriy Liandres
 * Date 18.12.2016
 */
public class Mql5OfferRisk extends ForexOfferRisk<TradeSignal> {

    private static final Double MIN_LOT = 0.01;

    /**
     * In this method we calculate additional risk which happens when user wants to invest sum less than manager owns. IN this case additional risc can occur.
     * Example: manager has 10000, user wants to invest 1000. Manager uses 0.05 lots. user will see 0.01 lots, but when manager has 5% decrease user will see 10% decrease
     * Thus additional risk =  (10000/1000) / (0.05/0.01) = 2
     * Also we multiply by coefficient calculated based on trades number
     */
    @Override
    public Double getAdditionalCoefficient(TradeSignal tradeSignal, ProvidedParams providedParams) {
        Double managerToProvidedCoefficient = tradeSignal.getManagerMoney() / providedParams.getSumm();
        Double averageLot = tradeSignal.getAverageLot() == null ? MIN_LOT : tradeSignal.getAverageLot();
        Double maxRelativeLotsDifferent = averageLot / MIN_LOT;
        Double coefficient = managerToProvidedCoefficient / maxRelativeLotsDifferent;
        if (coefficient < 1) {
            coefficient = 1.;
        }
        Double tradesNumber = tradeSignal.getTradesNumber();
        coefficient *= tradesNumber < 10 ? 10. : tradesNumber < 50 ? 5. : tradesNumber < 100 ? 2.5 : tradesNumber < 500 ? 1.25 : 1;
        //let's increase coefficient if manager made adds

        //1 add should exist at least when account was started
        if (tradeSignal.getAddsNumber() > 1 && tradeSignal.getAddsNumber() <= 2) {
            coefficient *= 1.05;
        } else if (tradeSignal.getAddsNumber() > 2 && tradeSignal.getAddsNumber() <= 4) {
            coefficient *= 1.1;
        } else if (tradeSignal.getAddsNumber() > 4 && tradeSignal.getAddsNumber() <= 6) {
            coefficient *= 1.25;
        } else if (tradeSignal.getAddsNumber() > 6 && tradeSignal.getAddsNumber() <= 11) {
            coefficient *= 1.5;
        } else if (tradeSignal.getAddsNumber() > 11) {
            coefficient *= 1.75;
        }

        return coefficient;
    }

    @Override
    public Double getAdditionalMainCoefficient(TradeSignal tradeSignal, ProvidedParams providedParams) {
        Double averageEquityRisk = tradeSignal.getAverageEquityRisk() == null || tradeSignal.getAverageEquityRisk() == 0 ? 2 : tradeSignal.getAverageEquityRisk();
        Double maxEquityRisk = tradeSignal.getMaxEquityRisk() == 0 ? 2 : tradeSignal.getMaxEquityRisk();
        return Math.sqrt(averageEquityRisk * maxEquityRisk);
    }
}
