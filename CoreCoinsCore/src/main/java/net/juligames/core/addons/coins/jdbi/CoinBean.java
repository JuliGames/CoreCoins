package net.juligames.core.addons.coins.jdbi;

import net.juligames.core.addons.coins.CoreCoin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class CoinBean {

    private String name;
    @Nullable
    private String description;

    public CoinBean(String name, @Nullable String description) {
        this.name = name;
        this.description = description;
    }

    public CoinBean() {
    }

    public @Nullable String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiStatus.Internal
    public CoreCoin assemble() {
        return new CoreCoin(getName());
    }
}
