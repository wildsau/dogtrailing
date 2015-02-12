package de.wildsau.dogtrailing.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

/**
 * Created by becker on 10.02.2015.
 */
public class TrackPoint {

    private final double longitude;
    private final double latitude;
    private final LatLng latLng;

    public TrackPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        latLng = new LatLng(latitude, longitude);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getDistance(TrackPoint otherPoint) {

        return SphericalUtil.computeDistanceBetween(this.latLng, otherPoint.latLng);
    }
}
