package pl.merdala.money;

public interface MoneyFormatter extends MoneyTransform<String>{
    SimpleMoneyFormatter SIMPLE_WITH_CURRENCY = new SimpleMoneyFormatter("#,##0.00 @CC");
    SimpleMoneyFormatter SIMPLE_WITHOUT_CURRENCY = new SimpleMoneyFormatter("#,##0.00");
}
