package net.juligames.core.addons.coins;

import com.google.protobuf.MapEntry;
import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.api.TODO;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
@TODO(doNotcall = true)
public class CoreCoinsAccount implements CoinsAccount {


    public String accountName() {

    }

    @Override
    public @NotNull UUID getOwner() {
        return null;
    }

    @Override
    public Collection<UUID> getUsers() {
        return null;
    }

    @Override
    public Map<Coin, Integer> getBalance() {
        return null;
    }

    public int getSpecificBalance(Coin coin) {
        return getBalance().getOrDefault(coin, 0);
    }

    @Override
    public MapEntry<Coin, Integer> getBalanceByName(String coinName) {
        return null;
    }

    @Override
    public void empty() {

    }
}
