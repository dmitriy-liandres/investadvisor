package com.investadvisor.model;

import com.investadvisor.Currency;
import com.investadvisor.ProvidedParams;
import com.investadvisor.pamm.fxopen.model.PammCommissionFxOpen;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammCommissionTest {

    @Test
    public void testGeneralCase() {
        Double minBalance = 100.;
        Double managerCommission = 20.;
        Integer minPeriodInDays = 0;
        Pamm pamm = new Pamm();
        pamm.setAvgChange(0.1);

        ProvidedParams providedParams = generateProvidedParams();
        PammCommission pammCommission = new PammCommission(minBalance, minPeriodInDays, managerCommission);
        Double calculateProfitAfterMangerCommission = pammCommission.calculateProfitAfterMangerCommission(pamm, providedParams);

        Double avgChange = pamm.getAvgChange();
        Double estimatedIncrease = Math.pow(1 + avgChange, providedParams.getPeriodInDays()) - 1;
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
        Integer minPeriodInDays = 0;

        Pamm pamm = new Pamm();
        pamm.setAvgChange(avgChange);
        ProvidedParams providedParams = generateProvidedParams();
        PammCommissionFxOpen pammCommissionFxOpen = new PammCommissionFxOpen(minBalance, minPeriodInDays, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions);

        Double calculateProfitAfterMangerCommission = pammCommissionFxOpen.calculateProfitAfterMangerCommission(pamm, providedParams);
        avgChange -= annualMasterCommission / 365;
        Double estimatedIncrease = Math.pow(1 + avgChange, providedParams.getPeriodInDays()) - 1;
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
        Integer minPeriodInDays = 0;

        Pamm pamm = new Pamm();
        pamm.setAvgChange(avgChange);
        ProvidedParams providedParams = generateProvidedParams();
        PammCommissionFxOpen pammCommissionFxOpen = new PammCommissionFxOpen(minBalance, minPeriodInDays, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions);

        Double calculateProfitAfterMangerCommission = pammCommissionFxOpen.calculateProfitAfterMangerCommission(pamm, providedParams);
        avgChange -= annualMasterCommission / 365;
        Double estimatedIncrease = Math.pow(1 + avgChange, providedParams.getPeriodInDays()) - 1;
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
        Double avgChange = annualMasterCommission / 365 + 0.001;
        Integer minPeriodInDays = 0;

        Pamm pamm = new Pamm();
        pamm.setAvgChange(avgChange);
        ProvidedParams providedParams = generateProvidedParams();
        PammCommissionFxOpen pammCommissionFxOpen = new PammCommissionFxOpen(minBalance, minPeriodInDays, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions);

        Double calculateProfitAfterMangerCommission = pammCommissionFxOpen.calculateProfitAfterMangerCommission(pamm, providedParams);
        avgChange -= annualMasterCommission / 365;
        Double estimatedIncrease = Math.pow(1 + avgChange, providedParams.getPeriodInDays()) - 1;
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
