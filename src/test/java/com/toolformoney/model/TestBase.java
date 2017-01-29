package com.toolformoney.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 13.11.2016
 */
public class TestBase {

    public static Pamm generatePamm() {
        return new Pamm(null, "name", "investmentPartnerLink") {

            @Override
            public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
                return null;
            }

            @Override
            public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
                return null;
            }

            @Override
            public Double getCommissionEnterFixed(ProvidedParams providedParams) throws IOException {
                return null;
            }

            @Override
            public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
                return null;
            }
        };
    }
}
