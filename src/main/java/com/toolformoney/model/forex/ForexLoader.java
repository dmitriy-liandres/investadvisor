package com.toolformoney.model.forex;

import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.model.ForexInvestmentTarget;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.utils.Constants;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for all Pamm loaders
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public abstract class ForexLoader<T extends ForexInvestmentTarget> extends InvestmentTargetLoader<T> {

    /**
     * Calculate all required params and set values to Pamm
     *
     * @param changes               list with changes
     * @param forexInvestmentTarget forexInvestmentTarget to which we should set values
     * @return avgChange
     */
    public Double addChangesToForexTrades(List<DailyChange> changes, ForexInvestmentTarget forexInvestmentTarget) {
        Double resultedRelativeValue = 1.;
        LocalDate now = LocalDate.now();
        LocalDate nowMinusYear = now.minusDays(Constants.DAYS_PER_YEAR.longValue());
        //filter changes: we don't want to take into consideration today's result as day is not finished and we don't take into consideration results which were more than a year ago
        changes = changes.stream().filter(change -> !change.getDate().isEqual(now) && !change.getDate().isBefore(nowMinusYear)).collect(Collectors.toList());

        for (DailyChange change : changes) {
            resultedRelativeValue = resultedRelativeValue * (1 + change.getChange() / 100);
        }

        Double lossDaysNumber = 0.;
        Double maxDailyLoss = 0.;
        Double sumDailyLoss = 0.;
        Double deviation;

        for (DailyChange change : changes) {
            if (change.getChange() < 0) {
                lossDaysNumber++;
                if (maxDailyLoss > change.getChange()) {
                    maxDailyLoss = change.getChange();
                }
                sumDailyLoss += change.getChange();
            }
        }

        //calc deviation
        Integer ageInDays = Math.min(Constants.DAYS_PER_YEAR.intValue(), forexInvestmentTarget.getAgeInDays());

        changes.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        Double changesWithCoefficientsSum = 0.;
        for (int i = 0; i < changes.size(); i++) {
            changesWithCoefficientsSum += changes.get(i).getChange() * (i + 1);
        }
        //we need take into consideration not only work days but all days
        Double coefficientsSum = (1. + ageInDays) / 2 * ageInDays;
        Double avgChangePerAllDays = changesWithCoefficientsSum / coefficientsSum;//old way: (Math.pow(resultedRelativeValue, 1. / ageInDays) - 1) * 100;


        Double avgChangePerWorkDays = (Math.pow(resultedRelativeValue, 1. / changes.size()) - 1) * 100;
        Double changeSquaresSum = changes.stream().mapToDouble(value -> (value.getChange() - avgChangePerWorkDays) * (value.getChange() - avgChangePerWorkDays)).sum();
        deviation = Math.sqrt(changeSquaresSum / changes.size());

        //if values can't be calculates, let's use 2%
        forexInvestmentTarget.setLossDaysPercentage(lossDaysNumber == 0 ? 2 : lossDaysNumber * 100 / changes.size());
        forexInvestmentTarget.setMaxDailyLoss(maxDailyLoss == 0. ? -2 : maxDailyLoss);
        forexInvestmentTarget.setAverageDailyLoss(sumDailyLoss == 0 ? 2 : sumDailyLoss / lossDaysNumber);
        forexInvestmentTarget.setDeviation(deviation);
        return avgChangePerAllDays;
    }

    /**
     * if pumm has 0 money, or leaves less than 7 days, or avgChange<=0 we don't need such managers
     *
     * @param pamms original list of pamms
     */
    protected void filterUselessForexAccounts(List<ForexInvestmentTarget> pamms) {
        Iterator<ForexInvestmentTarget> pammIterator = pamms.iterator();
        while (pammIterator.hasNext()) {
            ForexInvestmentTarget pamm = pammIterator.next();
            //no offers
            if (CollectionUtils.isEmpty(pamm.getInvestmentTargetOffers())) {
                pammIterator.remove();
                continue;
            }
            //remove offers with percentage less than 0
            Iterator<InvestmentTargetOffer> offersIt = pamm.getInvestmentTargetOffers().iterator();
            while (offersIt.hasNext()) {
                InvestmentTargetOffer offer = offersIt.next();
                if (offer.getAvgChange() == null || offer.getAvgChange() <= 0) {
                    offersIt.remove();
                }
            }
            //no profitable offers
            if (CollectionUtils.isEmpty(pamm.getInvestmentTargetOffers())) {
                pammIterator.remove();
                continue;
            }
            //no money of two young pamm
            if (pamm.getTotalMoney() == 0. || pamm.getAgeInDays() < 30) {
                pammIterator.remove();
            }
        }


    }
}
