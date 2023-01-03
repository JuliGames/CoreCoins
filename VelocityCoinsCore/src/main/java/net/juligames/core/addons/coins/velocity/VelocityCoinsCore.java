package net.juligames.core.addons.coins.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import net.juligames.core.addons.coins.CoreCoinsCore;
import net.juligames.core.addons.coins.api.CoreCoinsAPIAddon;
import org.slf4j.Logger;

@Plugin(
        id = "velocitycoinscore",
        name = "VelocityCoinsCore",
        version = "1.0-SNAPSHOT",
        authors = "Ture Bentzin"
)
public class VelocityCoinsCore {

    @Inject
    private Logger logger;


    public Logger getLogger() {
        return logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        getLogger().info("booting CoreCoinsCore...");
        new CoreCoinsCore();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        getLogger().info("removing CoreCoinsCore...");
        CoreCoinsAPIAddon.setCoreCoinsAPI(null);
    }
}
