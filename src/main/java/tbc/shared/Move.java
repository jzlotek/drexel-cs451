package tbc.shared;

import tbc.client.checkers.Vector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Move implements Serializable {
    private List<UUID> removed;
    private UUID pieceID;
    private Vector oldLocation;
    private Vector newLocation;

    public Move() {
    }

    public Move(UUID pieceID, Vector oldLocation, Vector newLocation) {
        this.removed = new ArrayList<UUID>();
        this.pieceID = pieceID;
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    public Move(UUID pieceID, Vector oldLocation, Vector newLocation, List<UUID> removed) {
        this.removed = removed;
        this.pieceID = pieceID;
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    public List<UUID> getRemoved() {
        return this.removed;
    }

    public Vector getOldLocation() { return this.oldLocation; }

    public Vector getNewLocation() {
        return this.newLocation;
    }

    public void addRemoved(UUID removed) {
        this.removed.add(removed);
    }

    @Override
    public String toString() {
        return  "ID: " + this.pieceID.toString() +
                " Old Location: " + this.oldLocation.toString() +
                " New Location: " + this.newLocation.toString() +
                " Removed Pieces: " + Arrays.asList(this.removed).toString();
    }
}
