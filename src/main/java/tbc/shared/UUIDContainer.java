package tbc.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class UUIDContainer implements Serializable {

    private ArrayList<UUID> uuids;

    public UUIDContainer(ArrayList<UUID> uuids) {
        this.uuids = uuids;
    }

    public ArrayList<UUID> getUUIDS() {
        return this.uuids;
    }

    public void setUUIDS(ArrayList<UUID> uuids) {
        this.uuids = uuids;
    }
}
