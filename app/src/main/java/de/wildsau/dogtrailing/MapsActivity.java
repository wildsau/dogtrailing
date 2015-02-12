package de.wildsau.dogtrailing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.InputStream;

import de.wildsau.dogtrailing.importer.tcx.Importer;
import de.wildsau.dogtrailing.model.Marker;
import de.wildsau.dogtrailing.model.Track;
import de.wildsau.dogtrailing.model.TrackPoint;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                setUpMap();

            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Importer importer = new Importer();

        String file = "res/raw/test.tcx";
        getResources().openRawResource(R.raw.test);
        InputStream in = getResources().openRawResource(R.raw.test);

        try {
            Track track = importer.importTrack(in);
            PolylineOptions options = new PolylineOptions().geodesic(true).color(Color.LTGRAY).width(10.0f).zIndex(1.0f);
            TrackPoint tp = null;
            for (TrackPoint p : track.getTrackPoints()) {
                options.add(new LatLng(p.getLatitude(), p.getLongitude()));
                tp = p;
            }
            mMap.addPolyline(options);

            mMap.addPolyline(new PolylineOptions().addAll(options.getPoints()).color(Color.GREEN).width(3.0f).zIndex(2.0f));

            for (Marker m : track.getMarkers()) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(m.getLocation().getLatitude(), m.getLocation().getLongitude())).title(m.getName()));
            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(tp.getLatitude(), tp.getLongitude()), 15.0f));
        } catch (Exception e) {
            //TODO: Exception Handling!
            Log.e("MAP-ACTIVITY", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
