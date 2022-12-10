package net.juligames.core.addons.coins.api.err;

import net.juligames.core.addons.coins.api.CoinsAccount;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class AccountOverflowException extends TransactionException implements Accounted {

    private final CoinsAccount account;

    public AccountOverflowException(CoinsAccount account) {
        this.account = account;
    }

    public AccountOverflowException(String message, CoinsAccount account) {
        super(message);
        this.account = account;
    }

    public CoinsAccount getAccount() {
        return account;
    }
}