package com.investadvisor.hyip.yabankir;

import com.investadvisor.Currency;
import com.investadvisor.hyip.yabankir.model.YaBankir;
import com.investadvisor.hyip.yabankir.model.YaBankirProfit;
import com.investadvisor.hyip.yabankir.model.YaBankirRisk;
import com.investadvisor.model.InvestmentTargetLoader;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://yabankir.com
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class YaBankirLoader extends InvestmentTargetLoader<YaBankir> {

    private static final Logger logger = LoggerFactory.getLogger(YaBankirLoader.class);

    private static YaBankirLoader instance = new YaBankirLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static YaBankirLoader getInstance() {
        return instance;
    }

    private YaBankirLoader() {
    }

    @Override
    public List<YaBankir> load() throws IOException {
        logger.info("Start download all yabankir.com offers");
        List<YaBankir> hyips = new ArrayList<>();
        //add offer in usd

        Double avgPercentage = (Math.pow(3.65, 1. / 365) - 1) * 100;

        YaBankir yaBankirUsd = new YaBankir(new YaBankirRisk(), new YaBankirProfit());
        yaBankirUsd.setAvgChange(avgPercentage);
        yaBankirUsd.setCurrency(Currency.USD);

        InvestmentTargetOffer investmentTargetOfferUsd = new InvestmentTargetOffer(10., 365, 0.);
        yaBankirUsd.addOffer(investmentTargetOfferUsd);
        hyips.add(yaBankirUsd);

        //add offer in RUB
        YaBankir yaBankirRub = new YaBankir(new YaBankirRisk(), new YaBankirProfit());
        yaBankirUsd.setAvgChange(avgPercentage);
        yaBankirRub.setCurrency(Currency.RUB);

        InvestmentTargetOffer investmentTargetOfferRub = new InvestmentTargetOffer(500., 365, 0.);
        yaBankirRub.addOffer(investmentTargetOfferRub);
        hyips.add(yaBankirRub);
        logger.info("Finish download all yabankir.com offers");
        return hyips;
    }
}
