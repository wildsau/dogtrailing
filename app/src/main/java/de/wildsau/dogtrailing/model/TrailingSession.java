package de.wildsau.dogtrailing.model;

import java.util.Date;

/**
 * Created by becker on 12.02.2015.
 */
public class TrailingSession {

    private Date createdDateTime;
    private Date searchedDateTime;
    private String title;
    private String notes;
    private String location;
    private double length;

    public TrailingSession() {
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Date getSearchedDateTime() {
        return searchedDateTime;
    }

    public void setSearchedDateTime(Date searchedDateTime) {
        this.searchedDateTime = searchedDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public long getExposureTime() {
        return searchedDateTime.getTime() - createdDateTime.getTime();
    }

    //dog
    //apportel
    //fährtenart
    //wetter beim legen
    //wetter beim suchen (Temp, Regen, Wind)
    //untergrund
    //fotos
    //Winkel
    //verleitungen
    //Untergrund / Gelände
    //Länge
    //Leger
    //Suche


}
