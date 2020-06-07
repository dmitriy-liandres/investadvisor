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
        logger.info("Start download all https://www.vtb.ru/ offers");
        List<Bank> banks = new ArrayList<>();
        //add offer in usd

        Bank vtb = new Bank(InvestmentTypeName.VTB_24, "ВТБ", "/vklady-investitsii/banki/vtb/");

        //RUB
        vtb.addOffer(new InvestmentTargetOffer("Время роста", 30_000., null, 180., 379., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vremya-rosta/?name=vremya-rosta", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 5.)));
        vtb.addOffer(new InvestmentTargetOffer("Время роста", 30_000., null, 380., null, 0., "https://www.vtb.ru/personal/vklady-i-scheta/vremya-rosta/?name=vremya-rosta", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.9)));

        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 30_000., null, 91., 180., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.32)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 30_000., null, 181., 394., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.39)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 30_000., null, 395., 545., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.39)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 30_000., null, 546., 730., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.43)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 30_000., null, 731., 1101., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.32)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 30_000., null, 1102., 1830., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.24)));

        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 30_000., null, 91., 180., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.81)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 30_000., null, 181., 394., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 4.03)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 30_000., null, 395., 545., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.92)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 30_000., null, 546., 730., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.96)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 30_000., null, 731., 1101., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.73)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 30_000., null, 1102., 1830., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.52)));

        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 30_000., null, 181., 394., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.68)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 30_000., null, 395., 545., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.46)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 30_000., null, 546., 730., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.48)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 30_000., null, 731., 1101., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 3.14)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 30_000., null, 1102., 1830., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 2.59)));

        //USD
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 500., null, 91., 180., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.05)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 500., null, 181., 394., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.25)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 500., null, 395., 545., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 500., null, 545., 730., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 500., null, 731., 1101., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));
        vtb.addOffer(new InvestmentTargetOffer("Выгодный", 500., null, 1102., 1830., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-vigodnyj/?name=vklad-vigodnyj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.5)));

        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 500., null, 91., 180., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 500., null, 181., 394., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.1)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 500., null, 395., 545., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.4)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 500., null, 546., 730., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.4)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 500., null, 731., 1101., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.4)));
        vtb.addOffer(new InvestmentTargetOffer("Пополняемый", 500., null, 1102., 1830., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-popolnyaemjy/?name=vklad-popolnyaemjy", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));

        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 3_000., null, 181., 394., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.05)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 3_000., null, 395., 545., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.2)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 3_000., null, 546., 730., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.25)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 3_000., null, 731., 1101., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.25)));
        vtb.addOffer(new InvestmentTargetOffer("Комфортный", 3_000., null, 1102., 1830., 0., "https://www.vtb.ru/personal/vklady-i-scheta/vklad-komfortnyj/?name=vklad-komfoortnj", Currency.USD, null, new BankOfferRisk(0.15), new BankOfferProfit(true, 0.01)));


        //EUR


        banks.add(vtb);

        logger.info("Finish download all http://www.vtb24.ru/ offers");
        return banks;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.VTB_24;
    }
}
