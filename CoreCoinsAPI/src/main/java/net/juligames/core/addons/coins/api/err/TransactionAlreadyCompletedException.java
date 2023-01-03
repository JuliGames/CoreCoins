package net.juligames.core.addons.coins.api.err;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class TransactionAlreadyCompletedException extends TransactionException {
    public TransactionAlreadyCompletedException() {
        super("The Transaction that was executed has already finished!");
    }

    public TransactionAlreadyCompletedException(String message) {
        super(message);
    }
}
