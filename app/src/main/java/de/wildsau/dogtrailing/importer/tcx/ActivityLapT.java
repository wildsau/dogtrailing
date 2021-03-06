//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.09 at 09:18:56 AM CET 
//


package de.wildsau.dogtrailing.importer.tcx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Root(name = "Lap")
public class ActivityLapT {

    @ElementList(name = "Track", required = true, type = TrackpointT.class, entry = "Trackpoint")
    protected List<TrackpointT> track;
    @Attribute(name = "StartTime", required = true)
    protected Date startTime;

    public List<TrackpointT> getTrack() {
        if (track == null) {
            track = new ArrayList<TrackpointT>();
        }
        return this.track;
    }

    /**
     * Gets the value of the startTime property.
     *
     * @return possible object is
     * {@link java.util.Date }
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     *
     * @param value allowed object is
     *              {@link java.util.Date }
     */
    public void setStartTime(Date value) {
        this.startTime = value;
    }

}
