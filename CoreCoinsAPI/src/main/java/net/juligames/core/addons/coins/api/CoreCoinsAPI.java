package net.juligames.core.addons.coins.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public interface CoreCoinsAPI {
    static CoreCoinsAPI get() {
        return CoreCoinsAPIAddon.getCoreCoinsAPI();
    }

    /**
     * Gets or creates the Coin
     */
    Coin getCoin(String name);

    /**
     * Gets or creates the Coin
     */
    Coin getCoin(String name, String description);

    CoinTransaction transact(Coin coin, CoinsAccount sender, CoinsAccount recipient, int amount, @Nullable UUID initiator);

    Collection<Coin> getAllCoins();

    Collection<Coin> getAllCoins(Predicate<Coin> coinPredicate);

    Collection<CoinsAccount> getAllAccounts();

    Collection<CoinsAccount> getAllAccounts(Predicate<CoinsAccount> coinsAccountPredicate);

    /**
     * creates the Account or does nothing
     */
    CoinsAccount createAccount(String name, UUID owner);

    /**
     * Get or creates the Account (maybe without owner)
     */
    CoinsAccount getAccount(String accountName);

    /**
     * Get a CoinExchanger that will exchange between the two coins by the factor given
     */
    StaticCoinExchanger getExchanger(Coin from, Coin to, double factor);

    /**
     * This Account is reserved and has the name "deposit" - it can be used for exchange
     */
    @ApiStatus.Internal
    CoinsAccount getDepositAccount();
}
