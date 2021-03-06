package com.toolformoney.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.Pamm;
import com.toolformoney.model.forex.ForexOfferProfit;
import com.toolformoney.model.forex.ForexOfferRisk;
import com.toolformoney.investarget.pamm.fxopen.model.FxOpenForexOfferProfit;
import com.toolformoney.investarget.pamm.fxopen.model.PammOfferFxOpen;
import com.toolformoney.model.pamm.PammOfferRisk;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammOfferTest extends TestBase{

    @Test
    public void testGeneralCase() {
        Double minBalance = 100.;
        Double managerCommission = 20.;
        Double minPeriodInDays = 0.;
        Double avgChange = 0.1;
        Pamm pamm = generatePamm();


        ProvidedParams providedParams = generateProvidedParams();
        InvestmentTargetOffer investmentTargetOffer = new InvestmentTargetOffer("name", minBalance, null, minPeriodInDays, null, managerCommission, "link", Currency.USD, avgChange, new PammOfferRisk(), new ForexOfferProfit());

        Double calculateProfitAfterMangerCommission = new ForexOfferProfit().calculateProfitAfterMangerCommission(pamm, investmentTargetOffer, providedParams);

        Double estimatedIncrease = Math.pow(1 + avgChange / 100, providedParams.getPeriodInDays()) - 1;
        Double expectedCalculateProfitAfterMangerCommission = 1 + estimatedIncrease * (1 - managerCommission / 100);

        assertEquals(expectedCalculateProfitAfterMangerCommission, calculateProfitAfterMangerCommission);
    }

    /**
     * final result <100%
     */
    @Test
    public void testFxOpenNegativeProfit() {
        Double minBalance = 100.;
        Double managerCommission = 20.;
        Double annualMasterCommission = 10.;
        Double minimumPerformanceConstant = 10.;
        Double assignmentCommissions = 10.;
        Double avgChange = annualMasterCommission / 365 + 0.0001;
        Double minPeriodInDays = 0.;

        Pamm pamm = generatePamm();
        ProvidedParams providedParams = generateProvidedParams();
        PammOfferFxOpen pammOfferFxOpen = new PammOfferFxOpen("name", minBalance, minPeriodInDays, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions, Currency.USD, avgChange, "link");

        Double calculateProfitAfterMangerCommission = new FxOpenForexOfferProfit().calculateProfitAfterMangerCommission(pamm, pammOfferFxOpen, providedParams);
        avgChange -= annualMasterCommission / 365;
        Double estimatedIncrease = Math.pow(1 + avgChange / 100, providedParams.getPeriodInDays()) - 1;
        Double finalIncreased = (1 + estimatedIncrease) * (1 - assignmentCommissions / 100);
        assertEquals(finalIncreased, calculateProfitAfterMangerCommission);
    }

    /**
     * final result >100%, but manager commissions is not taken
     */
    @Test
    public void testFxOpenPositiveSmallProfit() {
        Double minBalance = 100.;
        Double managerCommission = 20.;
        Double annualMasterCommission = 10.;
        Double minimumPerformanceConstant = 10.;
        Double assignmentCommissions = 10.;
        Double avgChange = annualMasterCommission / 365 + 0.0005;
        Double minPeriodInDays = 0.;

        Pamm pamm = generatePamm();
        ProvidedParams providedParams = generateProvidedParams();
        PammOfferFxOpen pammOfferFxOpen = new PammOfferFxOpen("name", minBalance, minPeriodInDays, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions, Currency.USD, avgChange, "link");

        Double calculateProfitAfterMangerCommission = new FxOpenForexOfferProfit().calculateProfitAfterMangerCommission(pamm, pammOfferFxOpen, providedParams);
        avgChange -= annualMasterCommission / 365;
        Double estimatedIncrease = Math.pow(1 + avgChange / 100, providedParams.getPeriodInDays()) - 1;
        Double finalIncreased = (1 + estimatedIncrease) * (1 - assignmentCommissions / 100);
        assertEquals(finalIncreased, calculateProfitAfterMangerCommission);

    }

    /**
     * final result >100%,  manager commissions is taken
     */
    @Test
    public void testFxOpenPositiveHighProfit() {
        Double minBalance = 100.;
        Double managerCommission = 20.;
        Double annualMasterCommission = 10.;
        Double minimumPerformanceConstant = 10.;
        Double assignmentCommissions = 10.;
        Double avgChange = annualMasterCommission / 365 + 0.1;
        Double minPeriodInDays = 0.;

        Pamm pamm = generatePamm();
        ProvidedParams providedParams = generateProvidedParams();
        PammOfferFxOpen pammOfferFxOpen = new PammOfferFxOpen("name", minBalance, minPeriodInDays, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions, Currency.USD, avgChange, "link");

        Double calculateProfitAfterMangerCommission = new FxOpenForexOfferProfit().calculateProfitAfterMangerCommission(pamm, pammOfferFxOpen, providedParams);
        avgChange -= annualMasterCommission / 365;
        Double estimatedIncrease = Math.pow(1 + avgChange / 100, providedParams.getPeriodInDays()) - 1;
        Double finalIncreased = (1 + estimatedIncrease) * (1 - assignmentCommissions / 100);
        finalIncreased -= (finalIncreased - minimumPerformanceConstant / 100 - 1) * managerCommission / 100;
        assertEquals(finalIncreased, calculateProfitAfterMangerCommission);

    }

    private ProvidedParams generateProvidedParams() {
        Double summ = 1000.;
        Double periodInDays = 365.;
        Currency currency = Currency.USD;
        Integer maxAllowedRisk = 100;
        return new ProvidedParams(summ, periodInDays, currency, maxAllowedRisk);
    }


}
