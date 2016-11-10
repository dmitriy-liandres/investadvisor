package com.toolformoney.investarget.bank.bankmoskvy;

import com.google.inject.Singleton;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.bank.Bank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
@Singleton
public class BankMoskvyLoader extends InvestmentTargetLoader<Bank> {

    private static final Logger logger = LoggerFactory.getLogger(BankMoskvyLoader.class);


    @Override
    public List<Bank> load() throws IOException {
        logger.info("Start download all http://www.bm.ru/ (VTB) offers");
        List<Bank> banks = new ArrayList<>();
        //add offer in usd

        Bank sperbank = new Bank(InvestmentTypeName.BANK_MOSKVY, "Банк Москвы", "/втб-банк-москвы");

        //RUB
       // sperbank.addOffer(new InvestmentTargetOffer("Правильный ответ", 100_000., null, 380., null, 0., "http://www.bm.ru/ru/personal/vklady/srochnye-vklady/pravilnyy-otvet/", Currency.RUB, null, new BankOfferRisk(0.15), new BankOfferProfit(false, 9.32)));
        banks.add(sperbank);

        logger.info("Finish download all http://www.bm.ru/ (VTB) offers");
        return banks;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.BANK_MOSKVY;
    }
}
