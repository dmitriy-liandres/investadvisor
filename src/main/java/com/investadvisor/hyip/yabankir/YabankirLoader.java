package com.investadvisor.hyip.yabankir;

import com.investadvisor.Currency;
import com.investadvisor.hyip.yabankir.model.YaBankir;
import com.investadvisor.hyip.yabankir.model.YaBankirOfferRisk;
import com.investadvisor.model.InvestmentTargetLoader;
import com.investadvisor.model.hyip.HyipOfferProfit;
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
        YaBankirOfferRisk yaBankirRisk = new YaBankirOfferRisk();
        HyipOfferProfit hyipProfit = new HyipOfferProfit();
        YaBankir yaBankirUsd = new YaBankir();

        InvestmentTargetOffer investmentTargetOfferUsd = new InvestmentTargetOffer("yabankir", 10., null, 365., null, 0., "https://yabankir.com/?aid=dima-amid", Currency.USD, avgPercentage, yaBankirRisk, hyipProfit);
        yaBankirUsd.addOffer(investmentTargetOfferUsd);
        hyips.add(yaBankirUsd);

        //add offer in RUB
        YaBankir yaBankirRub = new YaBankir();
        InvestmentTargetOffer investmentTargetOfferRub = new InvestmentTargetOffer("yabankir", 500., null, 365., null, 0., "https://yabankir.com/?aid=dima-amid", Currency.RUB, avgPercentage, yaBankirRisk, hyipProfit);
        yaBankirRub.addOffer(investmentTargetOfferRub);
        hyips.add(yaBankirRub);
        logger.info("Finish download all yabankir.com offers");
        return hyips;
    }
}