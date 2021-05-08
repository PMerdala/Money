package pl.merdala.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {

    private final BigDecimal value;
    private final Currency currency;

    protected Money(BigDecimal value, Currency currency) {
        if (currency == null) throw new IllegalArgumentException("currency has to not be null");
        if (value == null) throw new IllegalArgumentException("value has to not be null");
        this.currency = currency;
        this.value = Money.round(value, currency.getDefaultFractionDigits());
    }

    public static Money of(BigDecimal value, String currencyCode) {
        if (currencyCode == null) throw new IllegalArgumentException("currency has to not be null");
        return new Money(value, Currency.getInstance(currencyCode));
    }

    public static Money of(long value, String currencyCode) {
        return new Money(BigDecimal.valueOf(value), Currency.getInstance(currencyCode));
    }

    public static Money of(double value, String currencyCode) {
        return new Money(BigDecimal.valueOf(value), Currency.getInstance(currencyCode));
    }

    public static Money of(String value, String currencyCode) {
        return new Money(new BigDecimal(value), Currency.getInstance(currencyCode));
    }
    private static BigDecimal round(BigDecimal value, int newPrecision) {
        BigDecimal newValue = value;
        int precision = value.scale();
        if (precision - 1 > newPrecision) {
            for (int i = precision - 1; i >= newPrecision; i--) {
                newValue = newValue.setScale(i, RoundingMode.HALF_UP);
            }
        } else {
            newValue = newValue.setScale(newPrecision, RoundingMode.HALF_UP);
        }
        return newValue;
    }

    public Money add(Money money) throws CurrencyHaveToBeSameException {
        if (!this.currency.equals(money.currency)) throw new CurrencyHaveToBeSameException();
        return new Money(this.value.add(money.value), this.currency);
    }

    public Money multiply(BigDecimal valueOf) {
        if (valueOf == null) throw new IllegalArgumentException("multiply valueOf has to not be null");
        return new Money(this.value.multiply(valueOf), currency);
    }

    public BigDecimal rateBetween(Money money, int rateScale) {
        if (money.value.doubleValue() == 0)
            throw new IllegalArgumentException("money can't has value 0 on rateBetween");
        return value.divide(money.value, rateScale, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (!value.equals(money.value)) return false;
        return currency.getCurrencyCode().equals(money.currency.getCurrencyCode());
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + currency.getCurrencyCode().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return value.toString() + currency.getCurrencyCode();
    }

    MoneyDTO getMoneyDTO() {
        return new MoneyDTO(value, currency.getCurrencyCode());
    }

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    public final static class MoneyDTO {

        public final BigDecimal value;
        public final String currencyCode;

        private MoneyDTO(BigDecimal value, String currencyCode) {
            this.value = value;
            this.currencyCode = currencyCode;
        }
    }
}
