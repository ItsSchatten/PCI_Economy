package com.itsschatten.portablecraftinginvs_econ;

import com.itsschatten.libs.commandutils.UniversalCommand;
import com.itsschatten.portablecraftinginvs_econ.configs.Messages;
import com.itsschatten.portablecraftinginvs_econ.configs.Settings;
import org.bukkit.command.CommandSender;

public class PCIEconCommand extends UniversalCommand {

    public PCIEconCommand() {
        super("pci_econ");

        setDescription("Admin command for the PCI Economy addon only reloads the configs.");
        setPermission(Permissions.RELOAD.getPermission());
    }

    @Override
    protected void run(CommandSender player, String[] args) {
        checkPerms(player, Permissions.RELOAD);

        if (args.length == 0) { // Reload if no arguments are passed.
            Messages.init();
            Settings.init();

            tell(Messages.RELOAD_MESSAGE);
        } else if (args.length == 1) {

            final String param2 = args[0].toLowerCase(); // Set args[1]

            switch (param2) { // Check if the argument is one of the below, if not send wrong args.
                case "settings":
                    Settings.init();
                    tell(Messages.RELOAD_SPECIFIC.replace("{file}", "settings.yml"));
                    break;
                case "messages":
                    Messages.init();
                    tell(Messages.RELOAD_SPECIFIC.replace("{file}", "messages.yml"));
                    break;
                default:
                    tell(Messages.WRONG_ARGS);
                    break;
            }
        }
    }
}
