package net.juligames.core.addons.coins.api.err;

import net.juligames.core.addons.coins.api.CoinsAccount;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

/**
 * Used for {@link Exception}s
 * @author Ture Bentzin
 * 10.12.2022
 */
public interface Accounted {
    CoinsAccount getAccount();

    @ApiStatus.Experimental
    default UUID getAccountOwner() {
        return getAccount().getOwner();
    }
}
