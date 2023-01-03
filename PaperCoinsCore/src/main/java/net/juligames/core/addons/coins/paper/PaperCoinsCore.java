package net.juligames.core.addons.coins.paper;

import net.juligames.core.addons.coins.CoreCoinsCore;
import net.juligames.core.addons.coins.api.CoreCoinsAPIAddon;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperCoinsCore extends JavaPlugin {

    @Override
    public void onLoad() {
        getLogger().info("booting CoreCoinsCore...");
        new CoreCoinsCore();
    }

    @Override
    public void onDisable() {
        getLogger().info("removing CoreCoinsCore...");
        CoreCoinsAPIAddon.setCoreCoinsAPI(null);
    }
}
