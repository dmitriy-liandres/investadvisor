package com.investadvisor.investarget.startups.shareinstock.model;

import com.investadvisor.Currency;
import com.investadvisor.model.startup.Startup;
import com.investadvisor.model.startup.StartupBroker;

import java.io.IOException;

/**   shareinstock.com
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class ShareInStock extends Startup {
    public ShareInStock() {
        super("shareinstock" , StartupBroker.SHARE_IN_STOCK);
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 0.5;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 0.5 + 5.;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return 0.;
    }
}