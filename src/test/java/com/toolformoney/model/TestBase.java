package com.toolformoney.model;

import com.toolformoney.Currency;
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
            public Double getCommissionEnterPercentage() {
                return null;
            }

            @Override
            public Double getCommissionWithdrawPercentage() {
                return null;
            }

            @Override
            public Double getCommissionEnterFixed(Currency currency) throws IOException {
                return null;
            }

            @Override
            public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
                return null;
            }
        };
    }
}
