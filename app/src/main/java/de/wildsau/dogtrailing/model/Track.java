package de.wildsau.dogtrailing.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by becker on 10.02.2015.
 */
public class Track {

    private final boolean compress = true;
    private final double compressionResolution = 1.0;

    private double totalDistanceExact = 0.0;

    private final List<TrackPoint> trackPoints = new ArrayList<TrackPoint>();
    private final List<Marker> markers = new ArrayList<Marker>();

    public void addTrackPoint(double latitude, double longitude) {
        TrackPoint currentTrackpoint = new TrackPoint(longitude, latitude);
        if (trackPoints.size() > 0 && compress) {
            TrackPoint lastTrackpoint = trackPoints.get(trackPoints.size() - 1);

            double deltaDistance = lastTrackpoint.getDistance(currentTrackpoint);
            if (deltaDistance > compressionResolution) {
                totalDistanceExact += deltaDistance;
                Log.d("Distance-Calc", "Total: " + totalDistanceExact + " | Delta: " + deltaDistance);
                trackPoints.add(currentTrackpoint);
            }
        } else {
            trackPoints.add(currentTrackpoint);
        }
    }

    public void addMarker(String name, MarkerType type, double latitude, double longitude) {
        addTrackPoint(latitude, longitude);

        markers.add(new Marker(name, type, trackPoints.get(trackPoints.size() - 1)));
    }


    public List<TrackPoint> getTrackPoints() {
        return trackPoints;
    }

    public List<Marker> getMarkers() {
        return markers;
    }
}
