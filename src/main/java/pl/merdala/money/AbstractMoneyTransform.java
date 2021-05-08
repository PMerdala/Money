package pl.merdala.money;

public abstract class AbstractMoneyTransform<Target> implements MoneyTransform<Target> {

    protected abstract Target format(Money.MoneyDTO dto);

    @Override
    public Target format(Money money) {
        return format( money.getMoneyDTO());
    }

}
