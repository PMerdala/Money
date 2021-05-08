package pl.merdala.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {

    //region create Money and round value

    @Test
    void createMoneyFromBigDecimalWithScaleSameAsCurrency() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1.26d), "PLN");
        assertEquals("1.26PLN", money.toString());
    }

    @Test
    void createMoneyFromLong() {
        //arrange
        Money money = Money.of(2, "PLN");
        assertEquals("2.00PLN", money.toString());
    }

    @Test
    void createMoneyFromDouble() {
        //arrange
        Money money = Money.of(1.26d, "PLN");
        assertEquals("1.26PLN", money.toString());
    }

    @Test
    void createMoneyFromString() {
        //arrange
        Money money = Money.of("1.26", "PLN");
        assertEquals("1.26PLN", money.toString());
    }
    @Test
    void createMoneyFromBigDecimalWithScaleLessAsInCurrency() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1.2d), "PLN");
        assertEquals("1.20PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalWithoutScale() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1), "PLN");
        assertEquals("1.00PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalWithoutScaleThousand() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1123), "PLN");
        assertEquals("1123.00PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalRoundUpScaleCurrencyPlus1() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1.255d), "PLN");
        assertEquals("1.26PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalRoundUp() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1.2544445d), "PLN");
        assertEquals("1.26PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalRoundUpThousand() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1000.2544445d), "PLN");
        assertEquals("1000.26PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalRoundUpWithNine() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1000.99999999d), "PLN");
        assertEquals("1001.00PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalRoundDownScaleCurrencyPlus1() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1.254d), "PLN");
        assertEquals("1.25PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalRoundDown() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1.2544444d), "PLN");
        assertEquals("1.25PLN", money.toString());
    }

    @Test
    void createMoneyFromBigDecimalRoundDownThousand() {
        //arrange
        Money money = getMoney(BigDecimal.valueOf(1000.35444444d), "PLN");
        assertEquals("1000.35PLN", money.toString());
    }
    //endregion create Money

    //region create Money throw Exception

    @Test
    void createMoneyArgumentCurrencyIsNullThrowException() {
        assertThrows(IllegalArgumentException.class, () -> getMoney(BigDecimal.valueOf(1), null));
    }

    @Test
    void createMoneyFromWrongStringThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Money.of("a","PLN"));
    }

    @Test
    void createMoneyFromWrongStringWithNumberThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Money.of("1a2","PLN"));
    }

    @Test
    void createMoneyArgumentValueIsNullThrowException() {
        assertThrows(IllegalArgumentException.class, () -> getMoney(null, "PLN"));
    }

    @Test
    void createMoneyArgumentCurrencyIsWrongThrowException() {
        assertThrows(IllegalArgumentException.class, () -> getMoney(BigDecimal.valueOf(1), "AAA"));
    }

    //endregion create Money throw Exception

    //region addMoney
    @Test
    void moneySameCurrencyCodeCanAdd() {
        //arrange
        Money money1 = getMoney(BigDecimal.valueOf(1.22d), "PLN");
        Money money2 = getMoney(BigDecimal.valueOf(1.23d), "PLN");
        //act
        Money money = money1.add(money2);
        assertEquals("2.45PLN", money.toString());
    }

    @Test
    void moneyDifferentCurrencyThrowException() {
        //arrange
        Money money1 = getMoney(BigDecimal.valueOf(1.22d), "USD");
        Money money2 = getMoney(BigDecimal.valueOf(1.23d), "PLN");
        //act assert
        assertThrows(CurrencyHaveToBeSameException.class, () -> money1.add(money2));
    }
    //endregion addMoney

    //region multiply scalar

    @Test
    void multiply() {
        Money money = getMoney(BigDecimal.valueOf(12.21d), "PLN");
        Money afterMultiply = money.multiply(BigDecimal.valueOf(21.21));
        assertEquals("258.97PLN", afterMultiply.toString());
    }

    @Test
    void multiplyThrowErrorThenMultiplyValueIsnull() {
        Money money = getMoney(BigDecimal.valueOf(12.21d), "PLN");
        assertThrows(IllegalArgumentException.class, () -> money.multiply(null));
    }

    //endregion multiply scalar

    //region rateBetween

    @Test
    void rateBetweenScale15() {
        Money money1 = getMoney(BigDecimal.valueOf(12.21d), "PLN");
        Money money2 = getMoney(BigDecimal.valueOf(4.19d), "PLN");
        BigDecimal rate = money1.rateBetween(money2, 15);
        assertEquals(BigDecimal.valueOf(2.914081145584726), rate);
    }

    @Test
    void rateBetweenScale11() {
        Money money1 = getMoney(BigDecimal.valueOf(12.21d), "PLN");
        Money money2 = getMoney(BigDecimal.valueOf(4.19d), "PLN");
        BigDecimal rate = money1.rateBetween(money2, 11);
        assertEquals(BigDecimal.valueOf(2.91408114558), rate);
    }

    @Test
    void rateBetweenScale4DifferentCurrency() {
        Money money1 = getMoney(BigDecimal.valueOf(12.21d), "PLN");
        Money money2 = getMoney(BigDecimal.valueOf(4.19d), "EUR");
        BigDecimal rate = money1.rateBetween(money2, 4);
        assertEquals(BigDecimal.valueOf(2.9141), rate);
    }

    @Test
    void rateBetweenThrowExceptionThenArgumentMoneyValueIs0() {
        Money money1 = getMoney(BigDecimal.valueOf(12.21d), "PLN");
        Money money2 = getMoney(BigDecimal.valueOf(0), "PLN");
        assertThrows(IllegalArgumentException.class, () -> money1.rateBetween(money2, 11));
    }
    //endregion rateBetween

    //region equals and hashcode

    @Test
    void equalsSameMoney() {
        Money money = getMoney(BigDecimal.valueOf(1),"PLN");
        assertEquals(money, money);
    }

    @Test
    void equals() {
        Money money1 = getMoney(BigDecimal.valueOf(1),"PLN");
        Money money2 = getMoney(BigDecimal.valueOf(1.000001d),"PLN");
        assertEquals(money1, money2);
    }

    @Test
    void equalsNotEqualsDifferentValue() {
        Money money1 = getMoney(BigDecimal.valueOf(1),"PLN");
        Money money2 = getMoney(BigDecimal.valueOf(2),"PLN");
        assertNotEquals(money1, money2);
    }
    @Test
    void equalsNotEqualsDifferentCurrencyCode() {
        Money money1 = getMoney(BigDecimal.valueOf(1),"PLN");
        Money money2 = getMoney(BigDecimal.valueOf(1),"USD");
        assertNotEquals(money1, money2);
    }

    @Test
    void hashcodeNotEqualsDifferentValue() {
        Money money1 = getMoney(BigDecimal.valueOf(1),"PLN");
        Money money2 = getMoney(BigDecimal.valueOf(2),"PLN");
        assertNotEquals(money1.hashCode(), money2.hashCode());
    }

    @Test
    void hashcodeNotEqualsDifferentCurrencyCode() {
        Money money1 = getMoney(BigDecimal.valueOf(1),"PLN");
        Money money2 = getMoney(BigDecimal.valueOf(1),"USD");
        assertNotEquals(money1.hashCode(), money2.hashCode());
    }

    @Test
    void hashcodeEqualsWhenMoneyEquals() {
        Money money1 = getMoney(BigDecimal.valueOf(1),"PLN");
        Money money2 = getMoney(BigDecimal.valueOf(1),"PLN");
        assertEquals(money1.hashCode(), money2.hashCode());
    }
    //endregion equals and hashcode

    @Test
    void getCurrencyCode() {
        Money money = getMoney(BigDecimal.valueOf(1), "PLN");
        assertEquals("PLN", money.getCurrencyCode());
    }

    private Money getMoney(BigDecimal value, String currency){
        return Money.of(value,currency);
    }
}
