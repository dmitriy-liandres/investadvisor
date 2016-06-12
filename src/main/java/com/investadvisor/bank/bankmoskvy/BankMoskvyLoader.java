package com.investadvisor.bank.bankmoskvy;

import com.investadvisor.Currency;
import com.investadvisor.model.InvestmentTargetLoader;
import com.investadvisor.model.bank.Bank;
import com.investadvisor.model.bank.BankOfferProfit;
import com.investadvisor.model.bank.BankOfferRisk;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
public class BankMoskvyLoader extends InvestmentTargetLoader<Bank> {

    private static final Logger logger = LoggerFactory.getLogger(BankMoskvyLoader.class);

    private static BankMoskvyLoader instance = new BankMoskvyLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static BankMoskvyLoader getInstance() {
        return instance;
    }

    private BankMoskvyLoader() {
    }

    @Override
    public List<Bank> load() throws IOException {
        logger.info("Start download all http://www.bm.ru/ (VTB) offers");
        List<Bank> banks = new ArrayList<>();
        //add offer in usd

        Bank sperbank = new Bank("Банк Москвы");

        //RUB
        sperbank.addOffer(new InvestmentTargetOffer("Правильный ответ", 100_000., null, 380., null, 0., "http://www.bm.ru/ru/personal/vklady/srochnye-vklady/pravilnyy-otvet/", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(false, 9.32)));
        banks.add(sperbank);

        logger.info("Finish download all http://www.bm.ru/ (VTB) offers");
        return banks;
    }
}
