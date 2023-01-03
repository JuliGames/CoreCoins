package net.juligames.core.addons.coins.api;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public interface CoinsAccount {

    default String accountName() {
        return getOwner() + "`s account";
    }

    @NotNull
    UUID getOwner();

    Collection<UUID> getUsers();

    Map<Coin, Integer> getBalance();

    int getSpecificBalance(Coin coin);

    Map.Entry<Coin, Integer> getBalanceByName(String coinName);

    int changeAmount(Coin coin, Function<Integer, Integer> changer);

    int setAmount(Coin coin, int amount);

    /**
     * This will delete all coins from this {@link CoinsAccount}
     */
    void empty();

}
