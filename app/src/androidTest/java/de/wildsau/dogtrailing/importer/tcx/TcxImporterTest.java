package de.wildsau.dogtrailing.importer.tcx;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.io.InputStream;

import de.wildsau.dogtrailing.model.Track;

/**
 * Created by becker on 09.02.2015.
 */
public class TcxImporterTest extends InstrumentationTestCase {


    public void testParseXML() throws Exception {

        Importer importer = new Importer();

        String file = "res/raw/test.tcx";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);

        Track track = importer.importTrack(in);

        Log.i("TEST", "Trackpoints:" + track.getTrackPoints().size());
    }
}
