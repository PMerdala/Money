package pl.merdala.money;

public interface MoneyTransform<Target> {
    Target format(Money money);
}
