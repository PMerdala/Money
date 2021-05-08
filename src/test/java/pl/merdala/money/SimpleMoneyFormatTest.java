package pl.merdala.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleMoneyFormatTest {

    Money money = Money.of(BigDecimal.valueOf(23456789.02), "PLN");
    Money zero = Money.of(BigDecimal.ZERO, "PLN");

    @Test
    void formatWithCurrency() {
        MoneyFormatter formatter = MoneyFormatter.SIMPLE_WITH_CURRENCY;
        assertEquals("23 456 789,02 PLN", formatter.format(money));
    }

    @Test
    void formatWithCurrency0() {
        MoneyFormatter formatter = MoneyFormatter.SIMPLE_WITH_CURRENCY;
        assertEquals("0,00 PLN", formatter.format(zero));
    }

    @Test
    void formatWithoutCurrency() {
        MoneyFormatter formatter = MoneyFormatter.SIMPLE_WITHOUT_CURRENCY;
        assertEquals("23 456 789,02", formatter.format(money));
    }

    @Test
    void formatWithoutCurrency0() {
        MoneyFormatter formatter = MoneyFormatter.SIMPLE_WITHOUT_CURRENCY;
        assertEquals("0,00", formatter.format(zero));
    }

    @Test
    void formatWithoutCurrencyNotRequired0Format() {
        MoneyFormatter formatter = new SimpleMoneyFormatter("#,###.##");
        assertEquals("23 456 789,02", formatter.format(money));
    }

    @Test
    void formatWithoutCurrencyNotRequired0Format0() {
        MoneyFormatter formatter = new SimpleMoneyFormatter("#,###.##");
        assertEquals("0", formatter.format(zero));
    }
}