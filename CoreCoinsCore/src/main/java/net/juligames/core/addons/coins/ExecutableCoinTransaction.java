package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinTransaction;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.addons.coins.api.err.AccountDeficitException;
import net.juligames.core.addons.coins.api.err.AccountOverflowException;
import net.juligames.core.addons.coins.api.err.TransactionAlreadyCompletedException;
import net.juligames.core.addons.coins.api.err.TransactionException;
import net.juligames.core.api.err.dev.TODOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class ExecutableCoinTransaction implements CoinTransaction {

    private @Nullable UUID initiator;
    private @Nullable Date timeStamp;

    public ExecutableCoinTransaction(@Nullable UUID initiator, @NotNull CoreCoinsAccount from,
                                     @NotNull CoreCoinsAccount to, @NotNull CoreCoin coin,
                                     @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE) int amount) {
        this.initiator = initiator;
        this.from = from;
        this.to = to;
        this.coin = coin;
        this.amount = amount;
        this.transactionExceptions = new ArrayList<>();
    }

    private @NotNull CoinsAccount from;
    private @NotNull CoinsAccount to;
    private @NotNull Coin coin;

    private boolean executed = false;
    private @NotNull Collection<TransactionException> transactionExceptions;

    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    private int amount = 0;



    @Override
    public @Nullable UUID initiator() {
        return initiator;
    }

    @Override
    public @NotNull CoreCoinsAccount from() {
        return from;
    }

    @Override
    public @NotNull CoreCoinsAccount to() {
        return to;
    }

    @Override
    public @NotNull CoreCoin coin() {
        return coin;
    }

    @Override
    public @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE) int amount() {
        return amount;
    }

    @Override
    public @NotNull String getTransferString() {
        return "transfer " + amount + " " + coin.getName() +" from " + from.accountName() + " to " + to.accountName();
    }

    @Override
    public @Nullable Date timestamp() {
        return timeStamp;
    }

    @Override
    public boolean successful() {
        return failures().isEmpty();
    }

    @Override
    public @NotNull Collection<TransactionException> failures() {
        return transactionExceptions;
    }

    //execute
    public synchronized void execute() {
        try {
            if(executed) {
                throw new TransactionAlreadyCompletedException();
            }
            //run
            final int balance = from.getSpecificBalance(coin);
            //check 1 - deficit
            if(balance < amount) throw new AccountDeficitException("senders account does not have enough of " + coin.getName(), from);
            {
                int specificBalance = to.getSpecificBalance(coin);
                int freeSpace = Integer.MAX_VALUE - specificBalance;
                if(freeSpace < amount) throw new AccountOverflowException("recipients account would overflow when transaction would be allowed", to);
            }
            //allowed transaction
            {
                //from
                int newBalance = from.getSpecificBalance(coin) - amount;
                //from.getBalance().put(coin,newBalance); not allowed
                //EXECUTE A TRANSACTION FOR BOTH
            }

            {
                //to
                int newBalance = to.getSpecificBalance(coin) + amount;
                // to.getBalance().put(coin,newBalance); not allowed
            }
            //transaction finished
            executed = true;
            timeStamp = Date.from(Instant.now());
        }catch (TransactionException transactionException) {
            failures().add(transactionException);
        }catch (Exception e) {
            failures().add(new TransactionException("Transaction failed because of an unknown exception",e));
        }

    }

    private void save() {

    }
}
