package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinTransaction;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.addons.coins.api.CoreCoinsAPI;
import net.juligames.core.addons.coins.api.err.TransactionException;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ture Bentzin
 * ${DATE}
 */
public class Main {
    public static void main(String[] args) {
        Coin coin = CoreCoinsAPI.get().getCoin("Your Coin Name", "Description for your Coin");

        UUID uuid = UUID.randomUUID();
        CoinsAccount coinsAccount = CoreCoinsAPI.get().createAccount("AccountName", uuid);
        CoinsAccount coinsAccount1 = CoreCoinsAPI.get().getAccount("Hallo");


        CoinTransaction transaction = CoreCoinsAPI.get().transact(coin, coinsAccount, coinsAccount1, 100, uuid);
        Date timestamp = transaction.timestamp();
        boolean successful = transaction.successful();
        Collection<TransactionException> failures = transaction.failures();

        CoinsAccount from = null, to = null;
        CoinTransaction transact = CoreCoinsAPI.get().transact(coin, from, to, 100, UUID.randomUUID());
    }
}