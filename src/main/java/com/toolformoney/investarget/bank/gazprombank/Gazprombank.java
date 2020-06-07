package com.toolformoney.investarget.bank.gazprombank;

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

        Bank gazprombank = new Bank(InvestmentTypeName.GAZPROMBANK, "Газпромбанк", "/vklady-investitsii/banki/gazprombank/");
        //RUB
        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 250000., null, 181., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 250000., null, 367., null, 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.5)));

        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 50000., 299999.99, 181., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.6)));
        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 50000., 299999.99, 367., null, 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 300000., 499999.99, 181., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 300000., 499999.99, 367., null, 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.2)));
        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 500000., null, 181., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 7.0)));
        gazprombank.addOffer(new InvestmentTargetOffer("Тройная ставка", 500000., null, 367., null, 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/3287/", Currency.RUB, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 6.4)));


        //USD
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 500., 9999.99, 91., 180., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 500., 9999.99, 181., 270., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 500., 9999.99, 271., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 500., 9999.99, 367., 548., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 500., 9999.99, 549., 731., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.7)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 500., 9999.99, 732., 1097., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.7)));

        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 10000., null, 91., 180., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 10000., null, 181., 270., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.1)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 10000., null, 271., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.5)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 10000., null, 367., 548., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 10000., null, 549., 731., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.8)));
        gazprombank.addOffer(new InvestmentTargetOffer("Для сбережения", 10000., null, 732., 1097., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/2491/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.8)));


        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк – На жизнь", 500., 9999.99, 91., 180., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/1927/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк – На жизнь", 500., 9999.99, 181., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/1927/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк – На жизнь", 500., 9999.99, 367., 1097., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/1927/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.6)));

        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк – На жизнь", 10000., null, 91., 180., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/1927/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк – На жизнь", 10000., null, 181., 366., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/1927/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.01)));
        gazprombank.addOffer(new InvestmentTargetOffer("Газпромбанк – На жизнь", 10000., null, 367., 1097., 0., "https://www.gazprombank.ru/personal/increase/deposits/detail/1927/", Currency.USD, null, new BankOfferRisk(0.1), new BankOfferProfit(false, 0.6)));
        //EUR


        banks.add(gazprombank);

        logger.info("Finish download all sberbank.ru offers");
        return banks;

    }


    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.GAZPROMBANK;
    }
}
