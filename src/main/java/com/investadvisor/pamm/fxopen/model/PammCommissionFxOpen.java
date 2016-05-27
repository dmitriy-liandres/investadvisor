package com.investadvisor.pamm.fxopen.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.Pamm;
import com.investadvisor.model.PammCommission;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class PammCommissionFxOpen extends PammCommission {
    /**
     * This commission is divided between all working days
     */
    private Double annualMasterCommission;
    /**
     * if total profit less than this one than performanceFee is not aplied
     */
    private Double minimumPerformanceConstant;
    /**
     * Commission which is taken by manager when user start working with him
     */
    private Double assignmentCommissions;

    public PammCommissionFxOpen(Double minInvestment,
                                Integer minPeriodInDays,
                                Double commissionFromProfit,
                                Double annualMasterCommission,
                                Double minimumPerformanceConstant,
                                Double assignmentCommissions) {
        super(minInvestment, minPeriodInDays, commissionFromProfit);
        this.annualMasterCommission = annualMasterCommission;
        this.minimumPerformanceConstant = minimumPerformanceConstant;
        this.assignmentCommissions = assignmentCommissions;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(Pamm pamm, ProvidedParams providedParams) {
        Double avgChange = pamm.getAvgChange() - annualMasterCommission / 365;
        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChange, providedParams.getPeriodInDays()) - 1;
        Double finalIncreased = (1 + estimatedIncrease) * (1 - assignmentCommissions / 100);

        if (finalIncreased - 1 < minimumPerformanceConstant / 100) {
            return finalIncreased;
        } else {
            Double managerCommission = (finalIncreased - minimumPerformanceConstant / 100 - 1) * getCommissionFromProfit() / 100;
            return (finalIncreased - managerCommission);
        }

    }


    public Double getAnnualMasterCommission() {
        return annualMasterCommission;
    }

    public void setAnnualMasterCommission(Double annualMasterCommission) {
        this.annualMasterCommission = annualMasterCommission;
    }

    public Double getMinimumPerformanceConstant() {
        return minimumPerformanceConstant;
    }

    public void setMinimumPerformanceConstant(Double minimumPerformanceConstant) {
        this.minimumPerformanceConstant = minimumPerformanceConstant;
    }

    public Double getAssignmentCommissions() {
        return assignmentCommissions;
    }

    public void setAssignmentCommissions(Double assignmentCommissions) {
        this.assignmentCommissions = assignmentCommissions;
    }
}
