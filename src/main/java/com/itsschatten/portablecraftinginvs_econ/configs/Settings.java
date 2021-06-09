package com.itsschatten.portablecraftinginvs_econ.configs;

import com.itsschatten.libs.configutils.SimpleConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Settings extends SimpleConfig {

    public static boolean USE_ANVIL, USE_BREWING, USE_CARTOGRAPHY, USE_ENCHANT, USE_FURNACE, USE_GRINDSTONE, USE_LOOM, USE_SMITHING, USE_STONECUTTER,
            DEBUG, USE_UPDATER, ALLOW_FREE_CHARGE, ALLOW_FREE_PERM;
    public static int ANVIL_FREE_AMOUNT, BREWING_FREE_AMOUNT, CARTOGRAPHY_FREE_AMOUNT, ENCHANT_FREE_AMOUNT, FURNACE_FREE_AMOUNT, GRINDSTONE_FREE_AMOUNT,
            LOOM_FREE_AMOUNT, SMITHING_FREE_AMOUNT, STONECUTTER_FREE_AMOUNT, UPDATE_INTERVAL;
    public static double ANVIL_COST, BREWING_COST, CARTOGRAPHY_COST, ENCHANT_COST, FURNACE_COST, GRINDSTONE_COST, LOOM_COST, SMITHING_COST, STONECUTTER_COST;
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private static Settings instance;

    public Settings(String fileName) {
        super(fileName);

        setHeader(new String[]{
                "--------------------------------------------------------",
                " This configuration file has been automatically updated!",
                "",
                " Unfortunately, due to the way Bukkit saves .yml files,",
                " all comments in your file where lost. To read them,",
                " please open " + fileName + " directly to browse the default values.",
                " Don't know how to do this? You can also check our github",
                " page for the default file.",
                "(https://github.com/itsschatten/PCI_Economy/)",
                "--------------------------------------------------------"});

        if (instance != null)
            setInstance(null);

        setInstance(this);
    }

    public static void init() {
        new Settings("settings.yml").onLoad();
    }

    private void onLoad() {
        DEBUG = (boolean) get("debug");
        USE_UPDATER = (boolean) get("use-updater");
        UPDATE_INTERVAL = getInt("update-check-interval");
        ALLOW_FREE_CHARGE = (boolean) get("allow-free-charges");
        ALLOW_FREE_PERM = (boolean) get("allow-free-perm");

        USE_ANVIL = (boolean) get("use-anvil-charges");
        USE_BREWING = (boolean) get("use-brewing-charges");
        USE_CARTOGRAPHY = (boolean) get("use-cartography-charges");
        USE_ENCHANT = (boolean) get("use-enchant-charges");
        USE_FURNACE = (boolean) get("use-furnace-charges");
        USE_GRINDSTONE = (boolean) get("use-grindstone-charges");
        USE_LOOM = (boolean) get("use-loom-charges");
        USE_SMITHING = (boolean) get("use-smithing-charges");
        USE_STONECUTTER = (boolean) get("use-stonecutter-charges");

        ANVIL_FREE_AMOUNT = getInt("anvil-free");
        BREWING_FREE_AMOUNT = getInt("brewing-free");
        CARTOGRAPHY_FREE_AMOUNT = getInt("cartography-free");
        ENCHANT_FREE_AMOUNT = getInt("enchant-free");
        FURNACE_FREE_AMOUNT = getInt("furnace-free");
        GRINDSTONE_FREE_AMOUNT = getInt("grindstone-free");
        LOOM_FREE_AMOUNT = getInt("loom-free");
        SMITHING_FREE_AMOUNT = getInt("smithing-free");
        STONECUTTER_FREE_AMOUNT = getInt("stonecutter-free");

        ANVIL_COST = getDouble("anvil-cost");
        BREWING_COST = getDouble("brewing-cost");
        CARTOGRAPHY_COST = getDouble("cartography-cost");
        ENCHANT_COST = getDouble("enchant-cost");
        FURNACE_COST = getDouble("furnace-cost");
        GRINDSTONE_COST = getDouble("grindstone-cost");
        LOOM_COST = getDouble("loom-cost");
        SMITHING_COST = getDouble("smithing-cost");
        STONECUTTER_COST = getDouble("stonecutter-cost");
    }


}
