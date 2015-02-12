package de.wildsau.dogtrailing.model;

/**
 * Created by becker on 11.02.2015.
 */
public class Marker {

    private final String name;
    private final MarkerType type;
    private final TrackPoint location;

    public Marker(String name, MarkerType type, TrackPoint location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public MarkerType getType() {
        return type;
    }

    public TrackPoint getLocation() {
        return location;
    }
}
