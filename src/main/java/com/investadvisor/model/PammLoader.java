package com.investadvisor.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for all Pamm loaders
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public abstract class PammLoader {


    /**
     * Maps json to object and back
     */
    protected static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    /**
     * Loads all pamms for one broker
     *
     * @return list of pamms
     * @throws IOException
     */
    protected abstract List<Pamm> load() throws IOException;

    /**
     * Calculate all required params and set values to Pamm
     *
     * @param changes list with changes
     * @param pamm    pamm to which we should set values
     */
    protected void addChangesToPamm(List<Double> changes, Pamm pamm) {
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

        Double avgChangePerAllDays = changes.stream().mapToDouble(value -> value).sum() / pamm.getAgeInDays();
        Double avgChangePerWorkDays = changes.stream().mapToDouble(value -> value).sum() / changes.size();
        Double changeSquaresSum = changes.stream().mapToDouble(value -> (value - avgChangePerWorkDays) * (value - avgChangePerWorkDays)).sum();
        deviation = Math.sqrt(changeSquaresSum / changes.size());

        //if values can't be calculates, let's use 2%
        pamm.setLossDaysPercentage(lossDaysNumber == 0 ? 2 : lossDaysNumber * 100 / changes.size());
        pamm.setMaxDailyLoss(maxDailyLoss == 0. ? -2 : maxDailyLoss);
        pamm.setAverageDailyLoss(sumDailyLoss == 0 ? 2 : sumDailyLoss / lossDaysNumber);
        pamm.setDeviation(deviation);
        pamm.setAvgChange(avgChangePerAllDays);
    }

    /**
     * Gets match values using provided patterns and text
     *
     * @param pattern regexp pattern
     * @param text    text where we search for pattern
     * @return the first found group
     */
    protected String getMatchValue(Pattern pattern, String text) {
        Matcher idMatcher = pattern.matcher(text);
        idMatcher.find();
        return idMatcher.group(1);
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
            if (pamm.getTotalMoney() == 0. || pamm.getAgeInDays() < 7 || pamm.getAvgChange() <= 0) {
                pammIterator.remove();
            }
        }


    }
}
