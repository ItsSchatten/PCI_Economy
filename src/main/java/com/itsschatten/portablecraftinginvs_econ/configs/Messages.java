package com.itsschatten.portablecraftinginvs_econ.configs;

import com.itsschatten.libs.Utils;
import com.itsschatten.libs.configutils.SimpleConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Messages extends SimpleConfig {

    public static String PREFIX, NO_MONEY, UPDATE, RELOAD_MESSAGE, RELOAD_SPECIFIC, WRONG_ARGS;
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private static Messages instance;

    public Messages(String fileName) {
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
        new Messages("messages.yml").onLoad();
        Utils.setPrefix(PREFIX);
    }

    private void onLoad() {

        PREFIX = getString("prefix");
        NO_MONEY = getString("not-enough-money");
        UPDATE = getString("update-available");

        RELOAD_MESSAGE = getString("reload-message");
        RELOAD_SPECIFIC = getString("reload-message-specific");
        WRONG_ARGS = getString("wrong-args");

    }

}
