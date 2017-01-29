package com.toolformoney.model.pamm;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.forex.ForexOfferRisk;

/**
 * Author Dmitriy Liandres
 * Date 18.12.2016
 */
public class PammOfferRisk extends ForexOfferRisk<Pamm> {
    @Override
    public Double getAdditionalCoefficient(Pamm pamm, ProvidedParams providedParams) {
        double brokerCoefficient = 0.;
        switch (pamm.getPammBroker()) {
            case ALFA_FOREX:
                brokerCoefficient = 1.;
                break;
            case ALPARI:
                brokerCoefficient = 1.;
                break;
            case FXOPEN:
                brokerCoefficient = 1.;
                break;
            case UNI_TRADE:
                brokerCoefficient = 100.;
                break;
            case INSOLT:
                brokerCoefficient = 100.;
                break;
            case WELTRADE:
                brokerCoefficient = 1.;
                break;
            case PRIVATE_FX:
                brokerCoefficient = 1.1;
                break;
            case AMARKETS:
                brokerCoefficient = 0.9;
                break;
            default:
                throw new RuntimeException("No broker coefficient specified");
        }
        return brokerCoefficient;
    }

    @Override
    public Double getAdditionalMainCoefficient(Pamm forexInvestmentTarget, ProvidedParams providedParams) {
        return null;
    }
}
