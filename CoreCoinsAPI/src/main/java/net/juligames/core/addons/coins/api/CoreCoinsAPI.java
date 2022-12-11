package net.juligames.core.addons.coins.api;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public interface CoreCoinsAPI {
    default CoreCoinsAPI get() {
        return CoreCoinsAPIAddon.getCoreCoinsAPI();
    }

    Coin getCoin(String name);

    CoinTransaction transact(Coin coin, CoinsAccount sender, CoinsAccount recipient, int amount);

    Collection<Coin> getAllCoins();
    Collection<Coin> getAllCoins(Predicate<Coin> coinPredicate);

    Collection<CoinsAccount> getAllAccounts();
    Collection<CoinsAccount> getAllAccounts(Predicate<CoinsAccount> coinsAccountPredicate);

    CoinsAccount createAccount(String name, UUID owner);
}
