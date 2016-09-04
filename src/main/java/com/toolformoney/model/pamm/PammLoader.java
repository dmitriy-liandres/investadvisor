package com.toolformoney.model.pamm;

import com.toolformoney.model.InvestmentTargetLoader;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Base class for all Pamm loaders
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public abstract class PammLoader extends InvestmentTargetLoader<Pamm> {

    /**
     * Calculate all required params and set values to Pamm
     *
     * @param changes                 list with changes
     * @param totalIncreaseInPercents total change for all work time. 13 means that at the end manager had start value * 1.13
     * @param pamm                    pamm to which we should set values
     * @return avgChange
     */
    protected Double addChangesToPamm(List<Double> changes, Double totalIncreaseInPercents, Pamm pamm) {
        Double lossDaysNumber = 0.;
        Double maxDailyLoss = 0.;
        Double sumDailyLoss = 0.;
        Double deviation;

        for (Double change : changes) {
            if (change < 0) {
                lossDaysNumber++;
                if (maxDailyLoss > change) {
                    maxDailyLoss = change;
                }
                sumDailyLoss += change;
            }
        }

        //calc deviation

        Double avgChangePerAllDays = (Math.pow(totalIncreaseInPercents / 100 + 1, 1. / pamm.getAgeInDays()) - 1) * 100;
        Double avgChangePerWorkDays = (Math.pow(totalIncreaseInPercents / 100 + 1, 1. / changes.size()) - 1) - 1;
        Double changeSquaresSum = changes.stream().mapToDouble(value -> (value - avgChangePerWorkDays) * (value - avgChangePerWorkDays)).sum();
        deviation = Math.sqrt(changeSquaresSum / changes.size());

        //if values can't be calculates, let's use 2%
        pamm.setLossDaysPercentage(lossDaysNumber == 0 ? 2 : lossDaysNumber * 100 / changes.size());
        pamm.setMaxDailyLoss(maxDailyLoss == 0. ? -2 : maxDailyLoss);
        pamm.setAverageDailyLoss(sumDailyLoss == 0 ? 2 : sumDailyLoss / lossDaysNumber);
        pamm.setDeviation(deviation);
        return avgChangePerAllDays;
    }

    /**
     * if pumm has 0 money, or leaves less than 7 days, or avgChange<=0 we don't need such managers
     *
     * @param pamms original list of pamms
     */
    protected void filterUselessPamms(List<Pamm> pamms) {
        Iterator<Pamm> pammIterator = pamms.iterator();
        while (pammIterator.hasNext()) {
            Pamm pamm = pammIterator.next();
            //no offers
            if (CollectionUtils.isEmpty(pamm.getInvestmentTargetOffers())) {
                pammIterator.remove();
                continue;
            }
            //remove offers with percentage less than 0
            Iterator<InvestmentTargetOffer> offersIt = pamm.getInvestmentTargetOffers().iterator();
            while (offersIt.hasNext()) {
                InvestmentTargetOffer offer = offersIt.next();
                if (offer.getAvgChange() <= 0) {
                    offersIt.remove();
                }
            }
            //no profitable offers
            if (CollectionUtils.isEmpty(pamm.getInvestmentTargetOffers())) {
                pammIterator.remove();
                continue;
            }
            //no money of two young pamm
            if (pamm.getTotalMoney() == 0. || pamm.getAgeInDays() < 7) {
                pammIterator.remove();
            }
        }


    }
}
