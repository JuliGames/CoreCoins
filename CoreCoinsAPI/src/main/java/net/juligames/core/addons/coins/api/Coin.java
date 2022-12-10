package net.juligames.core.addons.coins.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ture Bentzin
 * 10.12.2022
 * @implNote needs to implement {@link Object#equals(Object)}
 */
public interface Coin {

    /**
     * @return the name of the coin
     * @apiNote Should not contain whitespaces
     */
    @NotNull
    String getName();

    /**
     * @return the description of the coin
     * @apiNote if null then there is no description present (human-readable unformatted string)
     */
    @Nullable
    String getDescription();

}
