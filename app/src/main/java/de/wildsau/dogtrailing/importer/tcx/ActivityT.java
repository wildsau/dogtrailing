//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.09 at 09:18:56 AM CET 
//


package de.wildsau.dogtrailing.importer.tcx;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Root(name = "Activity")
public class ActivityT {


    @Element(name = "Id")
    protected Date id;

    @ElementList(name = "Lap", required = true, inline = true)
    protected List<ActivityLapT> lap;

    public Date getId() {
        return id;
    }

    public void setId(Date id) {
        this.id = id;
    }

    public List<ActivityLapT> getLap() {
        if (lap == null) {
            lap = new ArrayList<ActivityLapT>();
        }
        return this.lap;
    }
}
