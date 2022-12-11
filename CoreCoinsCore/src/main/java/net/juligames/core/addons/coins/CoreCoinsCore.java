package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinTransaction;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.addons.coins.api.CoreCoinsAPI;
import net.juligames.core.addons.coins.jdbi.AccountBean;
import net.juligames.core.addons.coins.jdbi.AccountDAO;
import net.juligames.core.addons.coins.jdbi.CoinBean;
import net.juligames.core.addons.coins.jdbi.CoinDAO;
import net.juligames.core.api.API;
import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public final class CoreCoinsCore implements CoreCoinsAPI {
    @Override
    public CoreCoinsCore get() {
        return instance;
    }

    private static CoreCoinsCore instance;

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected Jdbi jdbiApi(){
        return API.get().getSQLManager().getJdbi();
    }

    public CoreCoinsCore() {
        instance = this;

        //setup jdbi
        setupJDBI(jdbiApi());

    }


    public void setupJDBI(@NotNull Jdbi jdbi){
        jdbi.withExtension(CoinDAO.class, extension -> {
            extension.createTable();
            return null;
        });
    }

    @Override
    public CoreCoin getCoin(String name) {
        CoinBean coinBean = jdbiApi().withExtension(CoinDAO.class, extension -> {
            extension.insert(new CoinBean(name, null));
            return extension.selectBean(name);
        });
        return Objects.requireNonNull(coinBean).assemble();
    }

    @Override
    public CoinsAccount createAccount(String name, UUID owner) {
        return jdbiApi().withExtension(AccountDAO.class, extension -> {
            extension.insert(new AccountBean(name, owner.toString()));
            return new CoreCoinsAccount(name);
        });
    }

    @Override
    public CoinTransaction transact(Coin coin, CoinsAccount sender, CoinsAccount recipient, int amount) {

        //temp just execute...



    }

    @Override
    public Collection<Coin> getAllCoins() {
        return null;
    }

    @Override
    public Collection<Coin> getAllCoins(Predicate<Coin> coinPredicate) {
        return null;
    }

    @Override
    public Collection<CoinsAccount> getAllAccounts() {
        return null;
    }

    @Override
    public Collection<CoinsAccount> getAllAccounts(Predicate<CoinsAccount> coinsAccountPredicate) {
        return null;
    }
}
