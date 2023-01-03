package net.juligames.core.addons.coins.api;

import de.bentzin.tools.pair.Pair;
import org.checkerframework.checker.units.qual.A;

import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 03.01.2023
 */
public abstract class SimpleCoinExchanger implements CoinExchanger{

    private final Coin from;
    private final Coin to;

    private CoreCoinsAPI coreCoinsAPI() {
        return CoreCoinsAPI.get();
    }

    protected SimpleCoinExchanger(Coin from, Coin to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Coin from() {
        return from;
    }

    @Override
    public Coin to() {
        return to;
    }

    @Override
    public Pair<CoinTransaction> exchange(CoinsAccount coinsAccount, int amount){
        CoinsAccount deposit = coreCoinsAPI().getDepositAccount();
        CoinTransaction withdrawal = null;
        CoinTransaction ingress = null;

        final CoinTransaction transact = coreCoinsAPI().transact(from, coinsAccount,
                deposit, amount, null); //get the money
        deposit.changeAmount(from, integer -> integer - amount); //delete the money
        if(transact.successful()) {
            withdrawal = transact;

            int newAmount = exchangeFunction().apply(amount); //calculate the new value
            deposit.changeAmount(to,integer -> integer + newAmount); //give deposit the money
            CoinTransaction transact1 = coreCoinsAPI().transact(to, deposit, coinsAccount,
                    newAmount, null);//send the money
            if(transact1.successful()) {
                ingress = transact1;
            }
        }
        return new Pair<>(withdrawal,ingress);
     }

    public abstract Function<Integer,Integer> exchangeFunction();
}
