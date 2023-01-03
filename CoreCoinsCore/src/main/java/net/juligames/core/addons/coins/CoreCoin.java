package net.juligames.core.addons.coins;

import net.juligames.core.addons.coins.api.Coin;
import net.juligames.core.addons.coins.jdbi.CoinDAO;
import net.juligames.core.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class CoreCoin implements Coin {

    @NotNull
    private final String name;

    public CoreCoin(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getDescription() {
        return API.get().getSQLManager().getJdbi().withExtension(CoinDAO.class, extension ->
                extension.selectBean(getName()).getDescription()
        );
    }
}
