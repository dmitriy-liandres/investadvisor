package com.investadvisor.model;

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
        Double finalIncrease = 0.3;
        PammCommission pammCommission = new PammCommission(minBalance, 0, managerCommission);
        Double calculatedFinalResultAfterMangerCommission = pammCommission.calculateFinalResultAfterMangerCommission(finalIncrease);
        Double expectedCalculatedFinalResultAfterMangerCommission = 1 + finalIncrease * (1 - managerCommission / 100);
        assertEquals(expectedCalculatedFinalResultAfterMangerCommission, calculatedFinalResultAfterMangerCommission);
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
        Double finalIncrease = 0.05;
        Double avgChange = 0.1;
        PammCommissionFxOpen pammCommissionFxOpen = new PammCommissionFxOpen(minBalance, 0, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions);
        Double adjustedAvgChange = pammCommissionFxOpen.adjustAvgChangeBasedOnCommission(avgChange);
        Double expectedAdjustedAvgChange = avgChange - annualMasterCommission / 365;
        assertEquals(expectedAdjustedAvgChange, adjustedAvgChange);

        Double calculatedFinalResultAfterMangerCommission = pammCommissionFxOpen.calculateFinalResultAfterMangerCommission(finalIncrease);
        Double expectedCalculatedFinalResultAfterMangerCommission = (1 - assignmentCommissions / 100) * (1 + finalIncrease);
        assertEquals(expectedCalculatedFinalResultAfterMangerCommission, calculatedFinalResultAfterMangerCommission);
    }

    /**
     * final result >100%, but manager commissions is not taken
     */
    @Test
    public void testFxOpenPositiveSmallProfit() {
        Double minBalance = 100.;
        Double managerCommission = 20.;
        Double annualMasterCommission = 10.;
        Double minimumPerformanceConstant = 20.;
        Double assignmentCommissions = 10.;
        Double finalIncrease = 0.3;
        Double avgChange = 0.1;
        PammCommissionFxOpen pammCommissionFxOpen = new PammCommissionFxOpen(minBalance, 0, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions);
        Double adjustedAvgChange = pammCommissionFxOpen.adjustAvgChangeBasedOnCommission(avgChange);
        Double expectedAdjustedAvgChange = avgChange - annualMasterCommission / 365;
        assertEquals(expectedAdjustedAvgChange, adjustedAvgChange);

        Double calculatedFinalResultAfterMangerCommission = pammCommissionFxOpen.calculateFinalResultAfterMangerCommission(finalIncrease);
        Double expectedCalculatedFinalResultAfterMangerCommission = (1 - assignmentCommissions / 100) * (1 + finalIncrease);
        assertEquals(expectedCalculatedFinalResultAfterMangerCommission, calculatedFinalResultAfterMangerCommission);
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
        Double finalIncrease = 0.4;
        Double avgChange = 0.1;
        PammCommissionFxOpen pammCommissionFxOpen = new PammCommissionFxOpen(minBalance, 0, managerCommission, annualMasterCommission, minimumPerformanceConstant, assignmentCommissions);
        Double adjustedAvgChange = pammCommissionFxOpen.adjustAvgChangeBasedOnCommission(avgChange);
        Double expectedAdjustedAvgChange = avgChange - annualMasterCommission / 365;
        assertEquals(expectedAdjustedAvgChange, adjustedAvgChange);

        Double calculatedFinalResultAfterMangerCommission = pammCommissionFxOpen.calculateFinalResultAfterMangerCommission(finalIncrease);
        Double expectedCalculatedFinalResult = (1 - assignmentCommissions / 100) * (1 + finalIncrease);
        Double managerCommissionExpected = (expectedCalculatedFinalResult - 1 - minimumPerformanceConstant / 100) * managerCommission / 100;
        Double expectedCalculatedFinalResultAfterMangerCommission = expectedCalculatedFinalResult - managerCommissionExpected;
        assertEquals(expectedCalculatedFinalResultAfterMangerCommission, calculatedFinalResultAfterMangerCommission);
    }
}
