package com.toolformoney.investarget.pamm.fxopen.model;

import com.toolformoney.Currency;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.forex.ForexOfferRisk;
import com.toolformoney.model.pamm.PammOfferRisk;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class PammOfferFxOpen extends InvestmentTargetOffer {
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

    public PammOfferFxOpen(String name,
                           Double minInvestment,
                           Double minPeriodInDays,
                           Double commissionFromProfit,
                           Double annualMasterCommission,
                           Double minimumPerformanceConstant,
                           Double assignmentCommissions,
                           Currency currency,
                           Double avgChange,
                           String link) {
        super(name, minInvestment, null, minPeriodInDays, null, commissionFromProfit, link, currency, avgChange, new PammOfferRisk(), new FxOpenForexOfferProfit());
        this.annualMasterCommission = annualMasterCommission;
        this.minimumPerformanceConstant = minimumPerformanceConstant;
        this.assignmentCommissions = assignmentCommissions;
    }


    public Double getAnnualMasterCommission() {
        return annualMasterCommission;
    }
    public Double getMinimumPerformanceConstant() {
        return minimumPerformanceConstant;
    }

    public Double getAssignmentCommissions() {
        return assignmentCommissions;
    }

}
