package tbc.shared;

import tbc.client.checkers.Vector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Move implements Serializable {
    private List<UUID> removed;
    private UUID pieceID;
    private Vector newLocation;

    private Move() {
    }

    public Move(UUID pieceID, Vector newLocation) {
        this.removed = new ArrayList<UUID>();
        this.pieceID = pieceID;
        this.newLocation = newLocation;
    }

    public Move(UUID pieceID, Vector newLocation, List<UUID> removed) {
        this.removed = removed;
        this.pieceID = pieceID;
        this.newLocation = newLocation;
    }

    public List<UUID> getRemoved() {
        return this.removed;
    }

    public Vector getNewLocation() {
        return this.newLocation;
    }

    public void addRemoved(UUID removed) {
        this.removed.add(removed);
    }

}
