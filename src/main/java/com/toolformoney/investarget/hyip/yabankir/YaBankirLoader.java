package com.toolformoney.investarget.hyip.yabankir;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.hyip.yabankir.model.YaBankir;
import com.toolformoney.investarget.hyip.yabankir.model.YaBankirOfferRisk;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.hyip.HyipOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
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
@Singleton
public class YaBankirLoader extends InvestmentTargetLoader<YaBankir> {

    private static final Logger logger = LoggerFactory.getLogger(YaBankirLoader.class);

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

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return null;
    }
}
