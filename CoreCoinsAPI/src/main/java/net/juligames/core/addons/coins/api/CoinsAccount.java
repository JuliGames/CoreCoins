package net.juligames.core.addons.coins.api;

import com.google.protobuf.MapEntry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

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

    MapEntry<Coin, Integer> getBalanceByName(String coinName);

    /**
     * This will delete all coins from this {@link CoinsAccount}
     */
    void empty();

}
