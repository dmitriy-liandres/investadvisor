package com.toolformoney.investarget.bank.gazprombank;

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
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
public class Gazprombank extends InvestmentTargetLoader<Bank> {

    private static final Logger logger = LoggerFactory.getLogger(Gazprombank.class);

    @Override
    public List<Bank> load() throws IOException {
        logger.info("Start download all http://www.gazprombank.ru/ offers");
        List<Bank> banks = new ArrayList<>();
        //add offer in usd

        Bank gazprombank = new Bank("Газпромбанк", "/vklady-investitsii/banki/gazprombank/");
        //RUB
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 15000., 299999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 15000., 299999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 15000., 299999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 15000., 299999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 15000., 299999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.6)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 300000., 999999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 300000., 999999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 8.)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 300000., 999999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.3)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 300000., 999999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 300000., 999999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 1000000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 1000000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 8.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 1000000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 1000000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 1000000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.0)));

        //USD
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.15)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.2)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.25)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.25)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.3)));

        //EUR
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.05)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.15)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.2)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.25)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Перспективный", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379092/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.3)));


        //RUB
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 15000., 299999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.6)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 15000., 299999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.6)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 15000., 299999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.9)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 15000., 299999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.6)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 15000., 299999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.4)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 300000., 999999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 300000., 999999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 300000., 999999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 300000., 999999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 300000., 999999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.6)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 1000000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 1000000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 8.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 1000000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.3)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 1000000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 1000000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));


        //USD
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.15)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.6)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.05)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.1)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.15)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.2)));

        //EUR
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.04)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.08)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.12)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.16)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 12, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.09)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.18)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.22)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Накопительный", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379093/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.26)));


        //RUB
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 15000., 299999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 15000., 299999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 15000., 299999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 15000., 299999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 15000., 299999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.3)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 300000., 999999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 300000., 999999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 300000., 999999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 300000., 999999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 300000., 999999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.5)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 1000000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.9)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 1000000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.9)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 1000000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 1000000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.9)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 1000000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.7)));


        //USD
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.9)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.95)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.0)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.15)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.6)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.05)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 1.1)));

        //EUR
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.03)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.06)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.09)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.11)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.08)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.16)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.19)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Динамичный", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379094/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.22)));


        //RUB
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 15000., 299999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 5.9)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 15000., 299999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 6.3)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 15000., 299999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 5.4)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 15000., 299999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 15000., 299999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.3)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 300000., 999999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 6.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 300000., 999999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 6.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 300000., 999999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 5.6)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 300000., 999999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.9)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 300000., 999999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.5)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 1000000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 6.3)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 1000000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 6.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 1000000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 5.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 1000000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 5.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 1000000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 4.7)));


        //USD
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.05)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.55)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.4)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.35)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.3)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.65)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.45)));

        //EUR
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.02)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.05)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.07)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 500., 9999.99, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.1)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_MONTH * 3, Constants.DAYS_PER_MONTH * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_MONTH * 6, Constants.DAYS_PER_MONTH * 6, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.07)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_YEAR, Constants.DAYS_PER_YEAR * 1, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.15)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_YEAR * 2, Constants.DAYS_PER_YEAR * 2, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.17)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк - Рантье", 10000., null, Constants.DAYS_PER_YEAR * 3, Constants.DAYS_PER_YEAR * 3, 0., "http://www.gazprombank.ru/personal/invest_savings/deposits/379095/", Currency.EUR, null, new BankOfferRisk(0.1), new BankOfferProfit(true, 0.20)));


        banks.add(gazprombank);

        logger.info("Finish download all sberbank.ru offers");
        return banks;

    }


    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.GAZPROMBANK;
    }
}
