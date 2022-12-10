package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
