package com.toolformoney.investarget.bank.sberbank;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.bank.Bank;
import com.toolformoney.model.bank.BankOfferProfit;
import com.toolformoney.model.bank.BankOfferRisk;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * http://sberbank.ru/
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
@Singleton
public class SberbankLoader extends InvestmentTargetLoader<Bank> {

    private static final Logger logger = LoggerFactory.getLogger(SberbankLoader.class);

    @Override
    public List<Bank> load() throws IOException {
        logger.info("Start download all sberbank.ru offers");
        List<Bank> banks = new ArrayList<>();
        //add offer in usd

        Bank sperbank = new Bank(InvestmentTypeName.SBERBANK, "Сбербанк", "/vklady-investitsii/banki/sberbank/");

        //RUB
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 100000., Constants.DAYS_PER_MONTH, Constants.DAYS_PER_MONTH * 2, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 2.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 100000., Constants.DAYS_PER_MONTH * 2, Constants.DAYS_PER_MONTH * 3, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 2.65)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 100000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.21)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 100000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.78)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 100000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.76)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 100000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.67)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 100000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.57)));


        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 400000., Constants.DAYS_PER_MONTH, Constants.DAYS_PER_MONTH * 2, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 2.6)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 400000., Constants.DAYS_PER_MONTH * 2, Constants.DAYS_PER_MONTH * 3, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 2.9)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 400000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.46)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 400000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.03)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 400000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.02)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 400000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.94)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 400000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.85)));

        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 400000., null, Constants.DAYS_PER_MONTH, Constants.DAYS_PER_MONTH * 2, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 2.75)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 400000., null, Constants.DAYS_PER_MONTH * 2, Constants.DAYS_PER_MONTH * 3, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.05)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 400000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.61)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 400000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.19)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 400000., null, Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.18)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 400000., null, Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.1)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 400000., null, Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.02)));




        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 1000., 100000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 2.96)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 1000., 100000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.42)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 1000., 100000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.3)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 1000., 100000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.09)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 1000., 100000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 2.86)));



        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100000., 400000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.21)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100000., 400000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.68)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100000., 400000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.56)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100000., 400000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100000., 400000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.14)));


        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 400000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.36)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 400000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.83)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 400000., null, Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.71)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 400000., null, Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.51)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 400000., null, Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/renew_online", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 3.3)));

        //USD
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100., 3000., Constants.DAYS_PER_MONTH, Constants.DAYS_PER_MONTH * 2, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100., 3000., Constants.DAYS_PER_MONTH * 2, Constants.DAYS_PER_MONTH * 3, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100., 3000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100., 3000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 1000., 3000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100., 3000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.25)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100., 3000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.2)));


        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 3000., 10000., Constants.DAYS_PER_MONTH, Constants.DAYS_PER_MONTH * 2, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 3000., 10000., Constants.DAYS_PER_MONTH * 2, Constants.DAYS_PER_MONTH * 3, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 3000., 10000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 3000., 10000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 3000., 10000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 3000., 10000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 3000., 10000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.3)));

        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 10000., 20000., Constants.DAYS_PER_MONTH, Constants.DAYS_PER_MONTH * 2, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 10000., 20000., Constants.DAYS_PER_MONTH * 2, Constants.DAYS_PER_MONTH * 3, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 10000., 20000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 10000., 20000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.05)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 100000., 20000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 10000., 20000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.4)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 10000., 20000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.35)));

        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 20000., null, Constants.DAYS_PER_MONTH, Constants.DAYS_PER_MONTH * 2, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 20000., null, Constants.DAYS_PER_MONTH * 2, Constants.DAYS_PER_MONTH * 3, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 20000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.05)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 20000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.1)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 20000., null, Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.55)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 20000., null, Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Сохраняй", 20000., null, Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.4)));





        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100., 3000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100., 3000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 1000., 3000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.15)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100., 3000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100., 3000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));


        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 3000., 10000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 3000., 10000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 3000., 10000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.25)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 3000., 10000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.1)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 3000., 10000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));

        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 10000., 20000., Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 10000., 20000., Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 100000., 20000., Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.3)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 10000., 20000., Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.15)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 10000., 20000., Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));

        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 20000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 20000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 20000., null, Constants.DAYS_PER_MONTH * 12, Constants.DAYS_PER_MONTH * 24, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 20000., null, Constants.DAYS_PER_MONTH * 24, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Пополняй", 20000., null, Constants.DAYS_PER_MONTH * 36, Constants.DAYS_PER_MONTH * 36, 0., "https://www.sberbank.ru/ru/person/contributions/deposits/save_online", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.05)));
        
        banks.add(sperbank);

        logger.info("Finish download all sberbank.ru offers");
        return banks;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.SBERBANK;
    }
}
