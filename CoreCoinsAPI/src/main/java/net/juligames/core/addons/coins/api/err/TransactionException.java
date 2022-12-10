package net.juligames.core.addons.coins.api.err;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class TransactionException extends Exception{

    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
