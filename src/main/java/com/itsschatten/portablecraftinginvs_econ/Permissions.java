package com.itsschatten.portablecraftinginvs_econ;

import com.itsschatten.libs.interfaces.IPermissions;

public enum Permissions implements IPermissions {

    RELOAD {
        @Override
        public String getPermission() {
            return "pci_econ.reload";
        }
    },
    UPDATE {
        @Override
        public String getPermission() {
            return "pci_econ.update";
        }
    },
    FREE {
        @Override
        public String getPermission() {
            return "pci_econ.free";
        }
    }

}
