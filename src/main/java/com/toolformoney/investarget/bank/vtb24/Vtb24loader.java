package com.toolformoney.investarget.bank.vtb24;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.bank.Bank;
import com.toolformoney.model.bank.BankOfferProfit;
import com.toolformoney.model.bank.BankOfferRisk;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * http://www.vtb24.ru/
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
@Singleton
public class Vtb24loader extends InvestmentTargetLoader<Bank> {

    private static final Logger logger = LoggerFactory.getLogger(Vtb24loader.class);

    @Override
    public List<Bank> load() throws IOException {
        logger.info("Start download all http://www.vtb24.ru/ offers");
        List<Bank> banks = new ArrayList<>();
        //add offer in usd

        Bank sperbank = new Bank(InvestmentTypeName.VTB_24, "ВТБ", "/vklady-investitsii/banki/vtb/");

        //RUB
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 100_000., 699_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 100_000., 699_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 100_000., 699_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.4)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 100_000., 699_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 100_000., 699_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.3)));

        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 700_000., 1499_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.9)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 700_000., 1499_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.85)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 700_000., 1499_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.75)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 700_000., 1499_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.6)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 700_000., 1499_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.3)));

        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 1500_000., null, 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 6.0)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 1500_000., null, 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.95)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 1500_000., null, 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.85)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 1500_000., null, 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.7)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 1500_000., null, 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.4)));

        //USD
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.6)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.55)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.4)));

        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.75)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.3)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.25)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.15)));

        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.6)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.55)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.45)));

        //EUR
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 3000., 19_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));

        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.15)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 20_000., 49_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));

        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.55)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Комфортный (Интернет)", 50_000., null, 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.4)));

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //RUB
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 100_000., 699_999., 91., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 6.8)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 100_000., 699_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.95)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 100_000., 699_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.3)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 100_000., 699_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.1)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 100_000., 699_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 6.55)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 100_000., 699_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 6.45)));

        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 700_000., 1499_999., 91., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.25)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 700_000., 1499_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 8.4)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 700_000., 1499_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.75)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 700_000., 1499_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 700_000., 1499_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.15)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 700_000., 1499_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.05)));

        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 1500_000., null, 90., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 8.15)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 1500_000., null, 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 9.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 1500_000., null, 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 8.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 1500_000., null, 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 8.15)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 1500_000., null, 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.65)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 1500_000., null, 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 7.55)));

        //USD
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 91., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.65)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.25)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.1)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.8)));

        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 90., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.3)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.05)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.65)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.6)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.4)));

        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 90., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.4)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 2.05)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 2.0)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.95)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 1.9)));

        //EUR
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 90., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.25)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 3000., 19_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));

        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 90., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.55)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.45)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 20_000., 49_999., 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.4)));

        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 190., 180., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 181., 394., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.35)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 395., 545., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.8)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 546., 731., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.75)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 732., 1101., 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.65)));
        sperbank.addOffer(new InvestmentTargetOffer("Выгодный (Интернет)", 50_000., null, 1102., null, 0., "http://www.vtb24.ru/personal/savings/deposits-calc/comfort/Pages/default.aspx#", Currency.EUR, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.6)));

        banks.add(sperbank);

        logger.info("Finish download all http://www.vtb24.ru/ offers");
        return banks;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.VTB_24;
    }
}
