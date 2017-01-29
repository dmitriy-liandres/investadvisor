package com.toolformoney.model;

import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.model.pamm.Pamm;
import com.toolformoney.model.forex.ForexLoader;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Author Dmitriy Liandres
 * Date 13.11.2016
 */
public class ForexLoaderTest extends TestBase {

    /**
     * final result >100%, but manager commissions is not taken
     */
    @Test
    public void testFxOpenPositiveSmallProfit() {
        Pamm pamm = generatePamm();
        ForexLoaderBase pammLoaderBase = new ForexLoaderBase();

        List<DailyChange> changes = new ArrayList<>();
        changes.add(new DailyChange(LocalDate.of(2016, 10, 1), 2.));
        changes.add(new DailyChange(LocalDate.of(2016, 10, 2), -3.));
        changes.add(new DailyChange(LocalDate.of(2016, 10, 3), 4.));

        pamm.setAgeInDays(3);

        Double averageChange = pammLoaderBase.addChangesToForexTrades(changes, pamm);
        assertEquals((Math.pow(1.02 * 0.97 * 1.04, 1. / 3) - 1) * 100, averageChange);
        assertEquals(-3., pamm.getAverageDailyLoss());
        assertEquals(-3., pamm.getMaxDailyLoss());

    }

    private class ForexLoaderBase extends ForexLoader {

        @Override
        public List<Pamm> load() throws IOException {
            return null;
        }

        @Override
        public InvestmentTypeName getInvestmentTypeName() {
            return null;
        }
    }
}
