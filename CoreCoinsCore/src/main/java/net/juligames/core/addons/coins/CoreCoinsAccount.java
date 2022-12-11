package net.juligames.core.addons.coins;

import com.google.protobuf.MapEntry;
import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.addons.coins.jdbi.AccountBean;
import net.juligames.core.addons.coins.jdbi.AccountDAO;
import net.juligames.core.api.API;
import net.juligames.core.api.TODO;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.extension.ExtensionCallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
@TODO(doNotcall = true)
public class CoreCoinsAccount implements CoinsAccount {

    @Contract("_ -> new")
    public static @NotNull CoreCoinsAccount fromBean(@NotNull AccountBean accountBean) {
        return new CoreCoinsAccount(accountBean.getAccountName());
    }

    private final String accountName;
    private final Jdbi jdbi;

    public CoreCoinsAccount(String accountName) {
        this.accountName = accountName;
        jdbi = API.get().getSQLManager().getJdbi();

        //check or throw
        Object coinBean = jdbi.withExtension(AccountDAO.class, extension -> extension.selectBean(accountName));
        Objects.requireNonNull(coinBean);
    }

    public String accountName() {
        return accountName;
    }

    @Override
    public @NotNull UUID getOwner() {
        return extension(extension -> UUID.fromString(extension.selectBean(accountName).getOwner()));
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

    public <R> R extension(ExtensionCallback<R,AccountDAO,Exception> extensionCallback){
        try {
           return jdbi.withExtension(AccountDAO.class,extensionCallback);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
