package pl.merdala.money;

import java.text.DecimalFormat;

public class SimpleMoneyFormatter extends AbstractMoneyTransform<String> implements MoneyFormatter {

    private final String formatString;

    public SimpleMoneyFormatter(String formatString) {
        this.formatString = formatString;
    }

    @Override
    protected String format(Money.MoneyDTO moneyDTO) {
        String format = formatString.replace("@CC", moneyDTO.currencyCode).replace(" ", " ");
        DecimalFormat formatter = new DecimalFormat(format);
        return formatter.format(moneyDTO.value);
    }

}
