package com.investadvisor.model;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammRiskProfit {
    private Pamm pamm;
    private PammRisk pammRisk;
    private PammProfit pammProfit;

    public PammRiskProfit(Pamm pamm, PammRisk pammRisk, PammProfit pammProfit) {
        this.pamm = pamm;
        this.pammRisk = pammRisk;
        this.pammProfit = pammProfit;
    }

    public Pamm getPamm() {
        return pamm;
    }

    public PammRisk getPammRisk() {
        return pammRisk;
    }

    public PammProfit getPammProfit() {
        return pammProfit;
    }

    @Override
    public String toString() {
        return "PammRiskProfit{" +
                "pamm=" + pamm +
                ", pammRisk=" + pammRisk +
                ", pammProfit=" + pammProfit +
                '}';
    }
}
