package com.radiantridge.restoradiantridge.objects;

import com.google.gson.JsonObject;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Restaurant class describes a restaurant.
 * A restaurant has a name, address, genre, price range from 1 to 4,
 * a star rating, notes, phone number, latitude and longitude.
 * For comparison and database use, it also has a created time, modified
 * time, database id, source type, heroku id, and zomato id.
 *
 * @author Rafia Anwar
 * @version 02/12/2016
 */
public class Restaurant implements Serializable{
    private String name;
    private String address;
    private String genre;
    private int priceRange; //the range is 1 to 4
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private double starRating; // 1- 5
    private String notes;
    private double longitude;
    private double latitude;
    private int dbId;
    private String phone;
    private int source; //0-direct input, 1-zomato, 2-heroku api.
    private int herokuId; //id heroku expects in order to associate a review with it
    private int zomatoId;

    /**
     * Default constructor
     */
    public Restaurant() {
    }

    /**
     * Returns the database id of the restaurant
     * @return the database id of the restaurant
     */
    public int getDbId() {
        return dbId;
    }

    /**
     * Sets the database id of the restaurant
     * @param dbId the database id of the restaurant
     */
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    /**
     * Returns the heroku id of the restaurant
     * @return the heroku id of the restaurant
     */
    public int getHerokuId() {
        return herokuId;
    }

    /**
     * Sets the heroku id of the restaurant
     * @param herokuId the heroku id of the restaurant
     */
    public void setHerokuId(int herokuId) {
        this.herokuId = herokuId;
    }
    /**
     * Returns the zomato id of the restaurant
     * @return the zomato id of the restaurant
     */
    public int getZomatoId() {
        return zomatoId;
    }

    /**
     * Sets the zomato id of the restaurant
     * @param zomatoId the zomatp id of the restaurant
     */
    public void setZomatoId(int zomatoId) {
        this.zomatoId = zomatoId;
    }

    /**
     * Returns the name of the restaurant
     * @return the name of the restaurant
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the restaurant
     * @param name the name of the restaurant
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the address of the restaurant
     * @return the address of the restaurant
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the address of the restaurant
     * @param address the address of the restaurant
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Returns the genre of the restaurant
     * @return the genre of the restaurant
     */
    public String getGenre() {
        return genre;
    }
    /**
     * Sets the genre of the restaurant
     * @param genre the genre of the restaurant
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     * Returns the price range of the restaurant
     * @return the price range of the restaurant
     */
    public int getPriceRange() {
        return priceRange;
    }
    /**
     * Sets the price range of the restaurant
     * @param priceRange the price range of the restaurant
     */
    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    /**
     * Returns the time of creation of restaurant's record in database
     * @return the time of creation of restaurant's record in database
     */
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets the time of creation of restaurant's record in database
     * @param createdTime the time of creation of restaurant's record in database
     */
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
    /**
     * Returns the time of modification of restaurant's record in database
     * @return the time of modification of restaurant's record in database
     */
    public Timestamp getModifiedTime() {
        return modifiedTime;
    }
    /**
     * Sets the time of modification of restaurant's record in database
     * @param modifiedTime the time of modification of restaurant's record in database
     */
    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * Returns the rating
     * @return the rating
     */
    public double getStarRating() {
        return starRating;
    }

    /**
     * Sets the rating
     * @param starRating rating of restaurant
     */
    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }

    /**
     * Return the notes about the restaurant
     * @return the notes about the restaurant
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes about the restaurant
     * @param notes  the notes about the restaurant
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    /**
     * Return the longitude of the restaurant
     * @return the longitude of the restaurant
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the restaurant
     * @param longitude  the longitude of the restaurant
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    /**
     * Return the latitude of the restaurant
     * @return the latitude of the restaurant
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the restaurant
     * @param latitude  the latitude of the restaurant
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the phone of the restaurant
     * @param phone  the phone of the restaurant
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Return the phone number of the restaurant
     * @return the phone number of the restaurant
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Returns the source of the restaurant, which can be 0,1,2
     * for localdb, zomato and heroku respectively
     * @return the source
     */
    public int getSource() {
        return this.source;
    }

    /**
     * Sets the source depending where the restaurant is coming from
     * @param source the source of restaurant
     */
    public void setSource(int source) {
        if(source != 0 && source != 1 && source != 2)
            throw new IllegalArgumentException("Invalid source value: " + source);
        this.source = source;
    }

    /**
     * Overridden equals method
     * @param o the object to compare
     * @return true if same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        if (priceRange != that.priceRange) return false;
        if (Double.compare(that.starRating, starRating) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (dbId != that.dbId) return false;
        if (!name.equals(that.name)) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (!genre.equals(that.genre)) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null)
            return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null)
            return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (source != that.source) return false;
        if (herokuId != that.herokuId) return false;
        if (zomatoId != that.zomatoId) return false;
        return phone != null ? phone.equals(that.phone) : that.phone == null;

    }

    /**
     * Overridden hashCode method
     * @return  The hashcode of the restaurant
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + genre.hashCode();
        result = 31 * result + priceRange;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (modifiedTime != null ? modifiedTime.hashCode() : 0);
        temp = Double.doubleToLongBits(starRating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + dbId;
        result = 31 * result + source;
        result = 31 * result + herokuId;
        result = 31 * result + zomatoId;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    /**
     * Overridden toString method
     * @return  The restaurant in String format
     */
    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", genre='" + genre + '\'' +
                ", priceRange=" + priceRange +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", starRating=" + starRating +
                ", notes='" + notes + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", dbId=" + dbId +
                ", source=" + source +
                ", herokuId=" + herokuId +
                ", zomatoId=" + zomatoId +
                ", phone='" + phone + '\'' +
                '}';
    }

    /**
     * Converts the fields to a Json property
     * @return  The restaurant inside of a JsonObject
     */
    public JsonObject toJsonObject() {
        //rating is not sent since in php it is determined based on reviews
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("address", address);
        json.addProperty("genre", genre);
        json.addProperty("price", priceRange);
        return json;
    }
}