package de.wildsau.dogtrailing.entities;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table TRAILING_SESSION.
 */
public class TrailingSession {

    private Long id;
    private String title;
    private String notes;
    private String distractions;
    private String finds;
    private Boolean test;
    private Boolean blind;
    private java.util.Date created;
    private java.util.Date searched;
    private Long exposureTime;
    private String weather;
    private Integer temperature;
    private Integer humidity;
    private String wind;
    private String windDirection;
    private String terrain;
    private String locality;
    private Boolean selfCreated;
    private String laidBy;
    private String searchItem;
    private String dogHandler;
    private String dog;
    private Double length;
    private Float rating;
    private Long tags;

    /**
     * Used to resolve relations
     */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TrailingSessionDao myDao;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public TrailingSession() {
    }

    public TrailingSession(Long id) {
        this.id = id;
    }

    public TrailingSession(Long id, String title, String notes, String distractions, String finds, Boolean test, Boolean blind, java.util.Date created, java.util.Date searched, Long exposureTime, String weather, Integer temperature, Integer humidity, String wind, String windDirection, String terrain, String locality, Boolean selfCreated, String laidBy, String searchItem, String dogHandler, String dog, Double length, Float rating, Long tags) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.distractions = distractions;
        this.finds = finds;
        this.test = test;
        this.blind = blind;
        this.created = created;
        this.searched = searched;
        this.exposureTime = exposureTime;
        this.weather = weather;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.windDirection = windDirection;
        this.terrain = terrain;
        this.locality = locality;
        this.selfCreated = selfCreated;
        this.laidBy = laidBy;
        this.searchItem = searchItem;
        this.dogHandler = dogHandler;
        this.dog = dog;
        this.length = length;
        this.rating = rating;
        this.tags = tags;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTrailingSessionDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDistractions() {
        return distractions;
    }

    public void setDistractions(String distractions) {
        this.distractions = distractions;
    }

    public String getFinds() {
        return finds;
    }

    public void setFinds(String finds) {
        this.finds = finds;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public Boolean getBlind() {
        return blind;
    }

    public void setBlind(Boolean blind) {
        this.blind = blind;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public void setCreated(java.util.Date created) {
        this.created = created;
    }

    public java.util.Date getSearched() {
        return searched;
    }

    public void setSearched(java.util.Date searched) {
        this.searched = searched;
    }

    public Long getExposureTime() {
        return exposureTime;
    }

    public void setExposureTime(Long exposureTime) {
        this.exposureTime = exposureTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Boolean getSelfCreated() {
        return selfCreated;
    }

    public void setSelfCreated(Boolean selfCreated) {
        this.selfCreated = selfCreated;
    }

    public String getLaidBy() {
        return laidBy;
    }

    public void setLaidBy(String laidBy) {
        this.laidBy = laidBy;
    }

    public String getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(String searchItem) {
        this.searchItem = searchItem;
    }

    public String getDogHandler() {
        return dogHandler;
    }

    public void setDogHandler(String dogHandler) {
        this.dogHandler = dogHandler;
    }

    public String getDog() {
        return dog;
    }

    public void setDog(String dog) {
        this.dog = dog;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getTags() {
        return tags;
    }

    public void setTags(Long tags) {
        this.tags = tags;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here



    // KEEP METHODS END

}
