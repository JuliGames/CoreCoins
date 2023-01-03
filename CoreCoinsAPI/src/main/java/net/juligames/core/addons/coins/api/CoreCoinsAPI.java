package net.juligames.core.addons.coins.api;

import org.jetbrains.annotations.Nullable;

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

    /**
     * Gets or creates the Coin
     * @param name
     * @return
     */
    Coin getCoin(String name);

    CoinTransaction transact(Coin coin, CoinsAccount sender, CoinsAccount recipient, int amount,@Nullable UUID initiator);

    Collection<Coin> getAllCoins();
    Collection<Coin> getAllCoins(Predicate<Coin> coinPredicate);

    Collection<CoinsAccount> getAllAccounts();
    Collection<CoinsAccount> getAllAccounts(Predicate<CoinsAccount> coinsAccountPredicate);

    CoinsAccount createAccount(String name, UUID owner);
}
