package net.juligames.core.addons.coins.api;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
public class CoreCoinsAPIAddon {

    private static CoreCoinsAPI coreCoinsAPI;

    public static CoreCoinsAPI getCoreCoinsAPI() {
        return coreCoinsAPI;
    }

    public static void setCoreCoinsAPI(CoreCoinsAPI newApi) {
        coreCoinsAPI = newApi;
    }
}
