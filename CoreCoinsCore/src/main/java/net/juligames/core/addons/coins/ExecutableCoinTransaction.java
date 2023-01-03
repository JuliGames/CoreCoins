package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.CoinTransaction;
import net.juligames.core.addons.coins.api.CoinsAccount;
import net.juligames.core.addons.coins.api.CoreCoinsAPI;
import net.juligames.core.addons.coins.api.err.AccountDeficitException;
import net.juligames.core.addons.coins.api.err.AccountOverflowException;
import net.juligames.core.addons.coins.api.err.TransactionAlreadyCompletedException;
import net.juligames.core.addons.coins.api.err.TransactionException;
import net.juligames.core.addons.coins.jdbi.BalanceDAO;
import net.juligames.core.addons.coins.jdbi.CauseJDBI;
import net.juligames.core.addons.coins.jdbi.TransactionBean;
import net.juligames.core.addons.coins.jdbi.TransactionDAO;
import net.juligames.core.api.API;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class ExecutableCoinTransaction implements CoinTransaction {

    private final @Nullable UUID initiator;
    private final @NotNull CoinsAccount from;
    private final @NotNull CoinsAccount to;
    private final @NotNull Coin coin;
    private final @NotNull Collection<TransactionException> transactionExceptions;
    private @Nullable Date timeStamp;
    private boolean executed = false;
    @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
    private int amount = 0;

    @ApiStatus.Internal
    protected static @NotNull ExecutableCoinTransaction fromBean(@NotNull TransactionBean bean) {
        @Nullable UUID uuid = bean.getInitiator().isEmpty()? null: UUID.fromString(bean.getInitiator());
        return new ExecutableCoinTransaction(uuid,
                account(bean.getFrom()),account(bean.getTo()),
                coin(bean.getCoin()),
                bean.getAmount(), Date.from(bean.getTimeStamp().toInstant()));
    }

    @ApiStatus.Internal
    private static CoinsAccount account(String s) {
        return CoreCoinsAPI.get().getAccount(s);
    }

    @ApiStatus.Internal
    private static Coin coin(String s){
        return CoreCoinsAPI.get().getCoin(s);
    }

    public ExecutableCoinTransaction(@Nullable UUID initiator, @NotNull CoinsAccount from,
                                     @NotNull CoinsAccount to, @NotNull Coin coin,
                                     @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE) int amount) {
        this.initiator = initiator;
        this.from = from;
        this.to = to;
        this.coin = coin;
        this.amount = amount;
        this.transactionExceptions = new ArrayList<>();
    }

    /**
     * This Constructor uses the {@link ExecutableCoinTransaction} as a record for {@link CoinTransaction} History.
     * An Instance created with this constructor will not be executable
     */
    @ApiStatus.Internal
    protected ExecutableCoinTransaction(@Nullable UUID initiator, @NotNull CoinsAccount from,
                                     @NotNull CoinsAccount to, @NotNull Coin coin,
                                     @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE) int amount,
                                     @NotNull Date timeStamp) {
        this(initiator,from,to,coin,amount);
        this.executed = true;
        this.timeStamp = timeStamp;
    }

    @Override
    public @Nullable UUID initiator() {
        return initiator;
    }

    @Override
    public @NotNull CoinsAccount from() {
        return from;
    }

    @Override
    public @NotNull CoinsAccount to() {
        return to;
    }

    @Override
    public @NotNull Coin coin() {
        return coin;
    }

    @Override
    public @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE) int amount() {
        return amount;
    }

    @Override
    public @NotNull String getTransferString() {
        return (!successful() ? "did not " : "") + "transfer " + amount + " " + coin.getName() + " from " + from.accountName() + " to " + to.accountName();
    }

    /**
     * Date of the last attempt
     */
    @Override
    public @Nullable Date timestamp() {
        return timeStamp;
    }

    @Override
    public boolean successful() {
        return failures().isEmpty();
    }

    @Override
    public synchronized @NotNull Collection<TransactionException> failures() {
        return transactionExceptions;
    }

    //execute
    @CauseJDBI
    public synchronized void execute() {
        try {
            if (executed) {
                throw new TransactionAlreadyCompletedException();
            }
            executed = true;
            //run
            final int balance = from.getSpecificBalance(coin);
            //check 1 - deficit
            if (balance < amount)
                throw new AccountDeficitException("senders account does not have enough of " + coin.getName(), from);
            {
                int specificBalance = to.getSpecificBalance(coin);
                int freeSpace = Integer.MAX_VALUE - specificBalance;
                if (freeSpace < amount)
                    throw new AccountOverflowException("recipients account would overflow when transaction would be allowed", to);
            }
            //allowed transaction
            {
                //from
                final int newFromBalance = from.getSpecificBalance(coin) - amount;
                final int newToBalance = to.getSpecificBalance(coin) + amount;

                timeStamp = Date.from(Instant.now()); // set before possible throw

                API.get().getSQLManager().getJdbi().inTransaction(handle -> {
                    BalanceDAO balanceDAO = handle.attach(BalanceDAO.class);
                    balanceDAO.update(from.accountName(), coin.getName(), newFromBalance);
                    balanceDAO.update(to.accountName(), coin.getName(), newToBalance);
                    return null;
                });
            }
            {
                //Add to DB
                final java.sql.Date date = new java.sql.Date(timeStamp.getTime()); //very unnecessary
                API.get().getSQLManager().getJdbi().withExtension(TransactionDAO.class, extension -> {
                    extension.insert(date, from.accountName(), to.accountName(),
                            coin.getName(), amount, initiator != null ? initiator.toString() : "");
                    return null;
                });
            }

        } catch (TransactionException transactionException) {
            failures().add(transactionException);
        } catch (Exception e) {
            failures().add(new TransactionException("Transaction failed because of an unknown exception", e));
        }

    }
}
