package de.wildsau.dogtrailing.importer.tcx;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.InputStream;
import java.util.Date;

import de.wildsau.dogtrailing.model.MarkerType;
import de.wildsau.dogtrailing.model.Track;

/**
 * Created by becker on 10.02.2015.
 */
public class Importer {

    private static final String TAG = "Dogtrailing.Importer.TCX";

    private final Serializer serializer;

    public Importer() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer());

        serializer = new Persister(m);
    }

    public Track importTrack(InputStream inputStream) throws Exception {
        Track track = new Track();
        try {
            TrainingCenterDatabaseT tcx = serializer.read(TrainingCenterDatabaseT.class, inputStream, false);

            ActivityT activity = tcx.getActivities().getActivity().get(0);

            boolean started = false;
            int lapCount = 0;
            double longitude = 0.0;
            double latitude = 0.0;

            for (ActivityLapT lap : activity.getLap()) {
                for (TrackpointT point : lap.getTrack()) {
                    longitude = point.getPosition().getLongitudeDegrees();
                    latitude = point.getPosition().getLatitudeDegrees();
                    if (!started) {
                        track.addMarker("Start", MarkerType.MARKER, latitude, longitude);
                        started = true;
                    } else {
                        track.addTrackPoint(latitude, longitude);
                    }
                }
                lapCount++;
                track.addMarker("Lap " + lapCount, MarkerType.MARKER, latitude, longitude);

            }

            return track;
        } catch (Exception e) {
            Log.e(TAG, "Failed to read TCX", e);
            //TODO; Create Qualified Exception
            throw new Exception("Failed to read TCX", e);
        }
    }
}
