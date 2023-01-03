package net.juligames.core.addons.coins.api;

import de.bentzin.tools.pair.Pair;
import org.jetbrains.annotations.ApiStatus;

/**
 * @author Ture Bentzin
 * 03.01.2023
 */
@ApiStatus.Experimental
public interface CoinExchanger {

    Coin from();
    Coin to();

    /**
     * This will first withdraw the given amount in the "from" Currency from the account and then add the new amount in
     * the "to" currency to the account. This is performed using Transactions and the Deposit Account that holds the money
     * temporary!
     * @param coinsAccount the account to perform the transaction on
     * @param amount the amount (in "form" Currency)
     * @return a Pair with the withdrawal and ingress Transaction
     */
    Pair<CoinTransaction> exchange(CoinsAccount coinsAccount, int amount);
}
