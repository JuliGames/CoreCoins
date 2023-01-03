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
        Coin coin = null;
        CoinsAccount from = null,to = null;
        CoinTransaction transact = CoreCoinsAPI.get().transact(coin, from, to, 100, UUID.randomUUID());
    }
}