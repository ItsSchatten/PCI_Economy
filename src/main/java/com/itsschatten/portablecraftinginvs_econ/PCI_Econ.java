package com.itsschatten.portablecraftinginvs_econ;

import com.itsschatten.libs.UpdateNotifications;
import com.itsschatten.libs.Utils;
import com.itsschatten.portablecraftinginvs_econ.configs.Messages;
import com.itsschatten.portablecraftinginvs_econ.configs.Settings;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PCI_Econ extends JavaPlugin {

    @Getter
    private static Economy econ;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Utils.log("&c[ERROR] Some error has occurred in registering the Economy... Do you have an economy plugin and Vault installed?");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Utils.setInstance(this);
        Bukkit.getPluginManager().registerEvents(new InventoryOpenListeners(econ), this);

        Settings.init();
        Messages.init();

        if (Settings.USE_UPDATER) {
            new UpdateNotifications(93136) {
                @Override
                public void onUpdateAvailable() {
                    Utils.log("There is an update available for the plugin! Current Version " + getDescription().getVersion() + " New Version " + getLatestVersion() + " {https://spigotmc.org/resources/" + getProjectId() + ")");
                }
            }.runTaskAsynchronously(this);

            new CheckForUpdate().runTaskTimerAsynchronously(this, Settings.UPDATE_INTERVAL * (60 * 20), Settings.UPDATE_INTERVAL * (60 * 20)); // Wait for the time set in the settings.yml before checking for an update.
            Utils.debugLog(Settings.DEBUG, "Checked for update, and set timer running, checking for update again in " + Settings.UPDATE_INTERVAL + " minutes.");
        }

        Utils.registerCommand(new PCIEconCommand());
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}
