package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.addons.coins.jdbi.AccountDAO;
import net.juligames.core.addons.coins.jdbi.BalanceBean;
import net.juligames.core.addons.coins.jdbi.BalanceDAO;
import net.juligames.core.addons.coins.jdbi.CauseJDBI;
import net.juligames.core.api.API;
import net.juligames.core.api.TODO;
import net.juligames.core.api.err.dev.TODOException;
import org.jdbi.v3.core.extension.ExtensionCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.*;
import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
@TODO(doNotcall = true)
public class CoreCoinsAccount implements CoinsAccount {

    private final String accountName;

    public CoreCoinsAccount(String accountName) {
        this.accountName = accountName;
    }

    public String accountName() {
        return accountName;
    }

    @Override
    @CauseJDBI
    public @NotNull UUID getOwner() {
        return extension(extension -> UUID.fromString(extension.selectBean(accountName).getOwner()));
    }

    @Override
    public Collection<UUID> getUsers() {
        throw new TODOException();
    }

    @Override
    @CauseJDBI
    public Map<Coin, Integer> getBalance() {
        return API.get().getSQLManager().getJdbi().withExtension(BalanceDAO.class, extension -> {
            Map<Coin, Integer> map = new HashMap<>();
            for (BalanceBean balanceBean : extension.selectBalance(accountName))
                map.put(new CoreCoin(balanceBean.getCoinName()), balanceBean.getBalance());
            return map;
        });
    }

    @CauseJDBI
    public int getSpecificBalance(Coin coin) {
        return getBalance().getOrDefault(coin, 0);
    }

    @Override
    @CauseJDBI
    public Map.Entry<Coin, Integer> getBalanceByName(String coinName) {
        Optional<Map.Entry<Coin, Integer>> first = getBalance().entrySet().stream().filter(coinIntegerEntry ->
                coinIntegerEntry.getKey().getName().equals(coinName)).findFirst();
        return first.orElse(null);
    }

    @CauseJDBI
    @Override
    public int changeAmount(Coin coin, Function<Integer, Integer> changer) {
        return API.get().getSQLManager().getJdbi().withExtension(BalanceDAO.class, extension -> {
            final int specificBalance = getSpecificBalance(coin);
            @Range(from = 0, to = Integer.MAX_VALUE)
            final int newBalance = changer.apply(specificBalance);
            extension.update(accountName, coin.getName(), newBalance);
            return specificBalance;
        });
    }

    @CauseJDBI
    @Override
    public int setAmount(Coin coin, int amount) {
        return changeAmount(coin, integer -> amount);
    }

    @Override
    @CauseJDBI
    public void empty() {
        //delete all balance
        API.get().getSQLManager().getJdbi().withExtension(BalanceDAO.class, extension -> {
            extension.delete(accountName);
            return null;
        });
    }

    private <R> R extension(ExtensionCallback<R, AccountDAO, Exception> extensionCallback) {
        try {
            return API.get().getSQLManager().getJdbi().withExtension(AccountDAO.class, extensionCallback);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
