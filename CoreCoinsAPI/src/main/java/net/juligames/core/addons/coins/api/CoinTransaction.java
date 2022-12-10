package net.juligames.core.addons.coins.api;

import net.juligames.core.addons.coins.api.err.TransactionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public interface CoinTransaction {

    /**
     * @apiNote do not use for advanced checking, just for monitoring.
     * @return the initiator of the Transaction
     */
    @Nullable
    UUID initiator();

    @NotNull
    CoinsAccount from();

    @NotNull
    CoinsAccount to();

    @NotNull
    Coin coin();

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    int amount();

    @NotNull
    String getTransferString();

    /**
     * @return the date the transaction was performed
     */
    @Nullable
    Date timestamp();

    /**
     *
     * @return if the transaction was performed. if returned false you can check out the errors {@link CoinTransaction#failures()}
     */
    boolean successful();

    @NotNull
    Collection<TransactionException> failures();
}
