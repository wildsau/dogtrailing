package de.wildsau.dogtrailing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.InputStream;

import de.wildsau.dogtrailing.importer.tcx.Importer;
import de.wildsau.dogtrailing.model.Marker;
import de.wildsau.dogtrailing.model.Track;
import de.wildsau.dogtrailing.model.TrackPoint;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMap();

        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap mMap) {
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
