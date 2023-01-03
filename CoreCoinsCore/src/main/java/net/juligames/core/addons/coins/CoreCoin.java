package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.jdbi.CoinBean;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class CoreCoin implements Coin {

    @NotNull
    private final String name;

    @Nullable
    public final String description;

    public CoreCoin(@NotNull String name, @Nullable String description) {
        this.name = name;
        this.description = description;
    }

    @ApiStatus.Experimental
    public CoreCoin(@NotNull CoinBean coinBean) {
        this(coinBean.getName(), coinBean.getDescription());
    }

    @Override
    public @NotNull String getName() {

        return name;
    }

    @Override
    public @Nullable String getDescription() {
        return description;
    }
}
