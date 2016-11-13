package com.toolformoney.model.pamm;

import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.model.InvestmentTargetLoader;
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
public abstract class PammLoader extends InvestmentTargetLoader<Pamm> {

    /**
     * Calculate all required params and set values to Pamm
     *
     * @param changes list with changes
     * @param pamm    pamm to which we should set values
     * @return avgChange
     */
    public Double addChangesToPamm(List<DailyChange> changes, Pamm pamm) {
        Double resultedRelativeValue = 1.;
        LocalDate now = LocalDate.now();
        LocalDate nowMinusYear = now.minusDays(Constants.DAYS_PER_YEAR.longValue());
        //filter changes: we don't want to take into consideration today's result as day is not finished and we don't take into consideration results which were more than a year ago
        changes = changes.stream().filter(change->!change.getDate().isEqual(now) && !change.getDate().isBefore(nowMinusYear)).collect(Collectors.toList());

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
        Integer ageInDays = Math.min(Constants.DAYS_PER_YEAR.intValue(), pamm.getAgeInDays());

        Double avgChangePerAllDays = (Math.pow(resultedRelativeValue, 1. / ageInDays) - 1) * 100;
        Double avgChangePerWorkDays = (Math.pow(resultedRelativeValue, 1. / changes.size()) - 1) - 1;
        Double changeSquaresSum = changes.stream().mapToDouble(value -> (value.getChange() - avgChangePerWorkDays) * (value.getChange() - avgChangePerWorkDays)).sum();
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
