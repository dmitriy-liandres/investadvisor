package com.toolformoney.model.startup;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTargetOfferProfitCalculation;
import com.toolformoney.model.InvestmentTargetOfferRisk;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class StartupOfferRisk extends InvestmentTargetOfferRisk<Startup> {
    private Boolean attractedRequiredSum;
    private Double avgPercentagePerMonth;

    private Double qualityCoefficient;

    /**
     * @param attractedRequiredSum  whether sum has been attracted at least once
     * @param avgPercentagePerMonth how much is payed per month
     * @param qualityCoefficient    1 - the best. The more  - the worth
     */
    public StartupOfferRisk(Boolean attractedRequiredSum,
                            Double avgPercentagePerMonth,
                            Double qualityCoefficient) {
        this.attractedRequiredSum = attractedRequiredSum;
        this.avgPercentagePerMonth = avgPercentagePerMonth;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Double getAvgPercentagePerMonth() {
        return avgPercentagePerMonth;
    }

    @Override
    public Double calculateAndSetRisk(Startup startup, ProvidedParams providedParams, InvestmentTargetOfferProfitCalculation investmentTargetOfferProfitCalculation) throws IOException {
        Double baseRisk;
        switch (startup.getStartupBroker()) {

            case SHARE_IN_STOCK:
                baseRisk = 2.4;   //check https://docs.google.com/spreadsheets/d/1BTF74xrrFseArIUS0YtvKYZ7Wkf0LBeQMaxnAx_BQuE/edit#gid=488837017 C11
                if (providedParams.getPeriodInDays() < 365) {
                    //in a year shares can be sold by nominal, earlier - not
                    baseRisk *= 2;
                }
                //currently shareInStock under higher risk
                baseRisk *= 50;
                break;
            case INSOLT:
                baseRisk = 6.25;   //check https://docs.google.com/spreadsheets/d/1BTF74xrrFseArIUS0YtvKYZ7Wkf0LBeQMaxnAx_BQuE/edit#gid=488837017 C11
                break;
            default:
                throw new RuntimeException("No base commission for startup " + startup.getStartupBroker());

        }

        Double attractedSharesCoefficient = attractedRequiredSum ? 1 : 1.5;
        Double percentageCoefficient;
        if (avgPercentagePerMonth <= 3) {
            percentageCoefficient = 1.;
        } else if (avgPercentagePerMonth <= 3) {
            percentageCoefficient = 1.;
        } else if (avgPercentagePerMonth <= 5) {
            percentageCoefficient = 1.5;
        } else {
            percentageCoefficient = 2.;
        }

        Double risk = baseRisk * attractedSharesCoefficient * percentageCoefficient * qualityCoefficient;
        return risk;
    }
}
