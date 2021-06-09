package com.itsschatten.portablecraftinginvs_econ;

import com.itsschatten.libs.UpdateNotifications;
import com.itsschatten.libs.Utils;
import com.itsschatten.libs.configutils.PlayerConfigManager;
import com.itsschatten.portablecrafting.events.*;
import com.itsschatten.portablecraftinginvs_econ.configs.Messages;
import com.itsschatten.portablecraftinginvs_econ.configs.Settings;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;

public class InventoryOpenListeners implements Listener {

    private final Economy economy;

    public InventoryOpenListeners(Economy economy) {
        this.economy = economy;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final PlayerConfigManager manager = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data");
        final FileConfiguration playerConf = manager.getConfig();

        if (!playerConf.contains("econ.anvil-charge")) {
            playerConf.set("econ.anvil-charge", Settings.ANVIL_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.brewing-charge")) {
            playerConf.set("econ.brewing-charge", Settings.BREWING_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.cartography-charge")) {
            playerConf.set("econ.cartography-charge", Settings.CARTOGRAPHY_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.enchant-charge")) {
            playerConf.set("econ.enchant-charge", Settings.ENCHANT_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.furnace-charge")) {
            playerConf.set("econ.furnace-charge", Settings.FURNACE_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.grindstone-charge")) {
            playerConf.set("econ.grindstone-charge", Settings.GRINDSTONE_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.loom-charge")) {
            playerConf.set("econ.loom-charge", Settings.LOOM_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.smithing-charge")) {
            playerConf.set("econ.smithing-charge", Settings.SMITHING_FREE_AMOUNT);
            manager.saveConfig();
        }

        if (!playerConf.contains("econ.stonecutter-charge")) {
            playerConf.set("econ.stonecutter-charge", Settings.STONECUTTER_FREE_AMOUNT);
            manager.saveConfig();
        }


        if (event.getPlayer().hasPermission(Permissions.UPDATE.getPermission()) && Settings.USE_UPDATER && (UpdateNotifications.isUpdateAvailable() || CheckForUpdate.isUpdateAvailable())) {
            // Check if an update is available, if the updater is used, and if the player has permission to see an update.
            PluginDescriptionFile pdf = Utils.getInstance().getDescription(); // So we can get the version.

            Utils.debugLog(Settings.DEBUG, "Found an update for the plugin, sending the message to the player.");

            Utils.tell(event.getPlayer(), StringUtils.replaceEach(UpdateNotifications.getUpdateMessage(), new String[]{"{currentVer}", "{newVer}", "{link}"},
                    new String[]{pdf.getVersion(), UpdateNotifications.getLatestVersion(), "https://spigotmc.org/resources/" + UpdateNotifications.getProjectId()}));
        }

    }

    @EventHandler
    public void onAnvilOpen(final AnvilOpenEvent event) {
        Utils.log("woah");

        if (Settings.USE_ANVIL) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.anvil-charge") > 0) {
                    playerConf.set("econ.anvil-charge", playerConf.getInt("econ.anvil-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.ANVIL_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.ANVIL_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.ANVIL_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.ANVIL_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.ANVIL_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                    economy.withdrawPlayer(event.getPlayer(), Settings.ANVIL_COST);
                }
            }
        }
    }


    @EventHandler
    public void onBrewingOpen(final BrewingOpenEvent event) {
        if (Settings.USE_BREWING) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.brewing-charge") > 0) {
                    playerConf.set("econ.brewing-charge", playerConf.getInt("econ.brewing-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.BREWING_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.BREWING_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.BREWING_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.BREWING_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.BREWING_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                    economy.withdrawPlayer(event.getPlayer(), Settings.BREWING_COST);
                }
            }
        }
    }

    @EventHandler
    public void onCartographyOpen(final CartographyOpenEvent event) {
        if (Settings.USE_CARTOGRAPHY) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.cartography-charge") > 0) {
                    playerConf.set("econ.cartography-charge", playerConf.getInt("econ.cartography-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.CARTOGRAPHY_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.CARTOGRAPHY_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.CARTOGRAPHY_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.CARTOGRAPHY_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.CARTOGRAPHY_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                    economy.withdrawPlayer(event.getPlayer(), Settings.CARTOGRAPHY_COST);
                }
            }
        }
    }

    @EventHandler
    public void onEnchantOpen(final EnchantingOpenEvent event) {
        if (Settings.USE_ENCHANT) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.enchant-charge") > 0) {
                    playerConf.set("econ.enchant-charge", playerConf.getInt("econ.enchant-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.ENCHANT_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.ENCHANT_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.ENCHANT_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.ENCHANT_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.ENCHANT_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                    economy.withdrawPlayer(event.getPlayer(), Settings.ENCHANT_COST);
                }
            }
        }
    }

    @EventHandler
    public void onFurnaceOpen(final FurnaceOpenEvent event) {
        if (Settings.USE_FURNACE) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.furnace-charge") > 0) {
                    playerConf.set("econ.furnace-charge", playerConf.getInt("econ.furnace-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.FURNACE_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.FURNACE_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.FURNACE_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.FURNACE_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.FURNACE_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                    economy.withdrawPlayer(event.getPlayer(), Settings.FURNACE_COST);
                }
            }
        }
    }

    @EventHandler
    public void onGrindstoneOpen(final GrindStoneOpenEvent event) {
        if (Settings.USE_GRINDSTONE) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.grindstone-charge") > 0) {
                    playerConf.set("econ.grindstone-charge", playerConf.getInt("econ.grindstone-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.GRINDSTONE_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.GRINDSTONE_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.GRINDSTONE_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.GRINDSTONE_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.GRINDSTONE_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                    economy.withdrawPlayer(event.getPlayer(), Settings.GRINDSTONE_COST);
                }
            }
        }
    }

    @EventHandler
    public void onLoomOpen(final LoomOpenEvent event) {
        if (Settings.USE_LOOM) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.loom-charge") > 0) {
                    playerConf.set("econ.loom-charge", playerConf.getInt("econ.loom-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.LOOM_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.LOOM_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.LOOM_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.LOOM_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.LOOM_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.LOOM_COST);
            }
        }
    }

    @EventHandler
    public void onSmithingOpen(final SmithingOpenEvent event) {
        if (Settings.USE_SMITHING) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.smithing-charge") > 0) {
                    playerConf.set("econ.smithing-charge", playerConf.getInt("econ.smithing-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.SMITHING_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.SMITHING_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.SMITHING_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.SMITHING_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.SMITHING_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.SMITHING_COST);
            }
        }
    }

    @EventHandler
    public void onStonecutterOpen(final StoneCutterOpenEvent event) {
        if (Settings.USE_STONECUTTER) {
            if (Settings.ALLOW_FREE_CHARGE) {
                final FileConfiguration playerConf = PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").getConfig();
                if (playerConf.getInt("econ.stonecutter-charge") > 0) {
                    playerConf.set("econ.stonecutter-charge", playerConf.getInt("econ.stonecutter-charge") - 1);
                    PlayerConfigManager.getConfig(event.getPlayer(), "./plugins/PortableCraftingInvs/data").saveConfig();
                    return;
                }
            }

            if (Settings.ALLOW_FREE_PERM && event.getPlayer().hasPermission(Permissions.FREE.getPermission())) return;

            if (economy.hasAccount(event.getPlayer())) {
                if (!economy.has(event.getPlayer(), Settings.STONECUTTER_COST)) {
                    event.setCancelled(true);
                    Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.STONECUTTER_COST + "")
                            .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                }
                economy.withdrawPlayer(event.getPlayer(), Settings.STONECUTTER_COST);
            } else {
                if (economy.createPlayerAccount(event.getPlayer())) {
                    if (!economy.has(event.getPlayer(), Settings.STONECUTTER_COST)) {
                        event.setCancelled(true);
                        Utils.tell(event.getPlayer(), Messages.NO_MONEY.replace("{needed}", Settings.STONECUTTER_COST + "")
                                .replace("{balance}", economy.getBalance(event.getPlayer()) + ""));
                    }
                    economy.withdrawPlayer(event.getPlayer(), Settings.STONECUTTER_COST);
                }
            }
        }
    }

}
