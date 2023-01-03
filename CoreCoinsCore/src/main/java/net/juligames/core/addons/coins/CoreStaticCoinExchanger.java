package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.api.SimpleCoinExchanger;
import net.juligames.core.addons.coins.api.StaticCoinExchanger;

import java.util.function.Function;

/**
 * @author Ture Bentzin
 * 03.01.2023
 */
public class CoreStaticCoinExchanger extends SimpleCoinExchanger implements StaticCoinExchanger {

    private final double factor;

    public CoreStaticCoinExchanger(Coin from, Coin to, double factor) {
        super(from, to);
        this.factor = factor;
    }

    @Override
    public Function<Integer, Integer> exchangeFunction() {
        return integer -> Double.valueOf(integer * factor).intValue(); //may support decimals in the future
    }

    @Override
    public double factor() {
        return factor;
    }
}
