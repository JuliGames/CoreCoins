package net.juligames.core.addons.coins.api.err;

import net.juligames.core.addons.coins.api.CoinsAccount;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class AccountDeficitException extends TransactionException implements Accounted {

    private final CoinsAccount account;

    public AccountDeficitException(CoinsAccount account) {
        this.account = account;
    }

    public AccountDeficitException(String message, CoinsAccount account) {
        super(message);
        this.account = account;
    }

    public CoinsAccount getAccount() {
        return account;
    }
}
