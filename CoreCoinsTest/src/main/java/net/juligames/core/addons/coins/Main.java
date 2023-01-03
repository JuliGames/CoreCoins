package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinTransaction;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.addons.coins.api.CoreCoinsAPI;

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

        CoinsAccount from = null, to = null;
        CoinTransaction transact = CoreCoinsAPI.get().transact(coin, from, to, 100, UUID.randomUUID());
    }
}