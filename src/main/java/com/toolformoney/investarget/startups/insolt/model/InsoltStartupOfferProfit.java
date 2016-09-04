package com.toolformoney.investarget.startups.insolt.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentTargetOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.startup.StartupDetails;
import com.toolformoney.model.startup.StartupOffer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class InsoltStartupOfferProfit extends InvestmentTargetOfferProfit {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    /**
     * if it is possible to sell for 1.5$ and original price is 2, than value = 91.5-2)/2*100
     */
    private Double askBidDifferenceInPercentages;
    private StartupDetails startupDetails;

    public InsoltStartupOfferProfit(Double askBidDifferenceInPercentages, StartupDetails startupDetails) {
        this.askBidDifferenceInPercentages = askBidDifferenceInPercentages;
        this.startupDetails = startupDetails;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {

        StartupOffer startupOffer = (StartupOffer) investmentTargetOffer;
        Double periodInDates = providedParams.getPeriodInDays();

        //startups starts in particular date, so investments before this date do not get percents
        LocalDate now = LocalDate.now();
        LocalDate startDate = LocalDate.parse(startupDetails.getStartDate(), DATE_FORMATTER);
        if (now.isBefore(startDate)) {
            periodInDates -= ChronoUnit.DAYS.between(now, startDate);
        }
        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = startupOffer.getPercentagePerMonth() * Math.floor(periodInDates / 30) / 100;


        //there is a difference between bid and ask
        estimatedIncrease -= askBidDifferenceInPercentages / 100;

        //site commission
        estimatedIncrease -= 2 / 100;

        return 1 + estimatedIncrease;
    }
}
