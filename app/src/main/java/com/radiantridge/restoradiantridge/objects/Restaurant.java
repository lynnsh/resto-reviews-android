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

    public Restaurant() {
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getHerokuId() {
        return herokuId;
    }

    public void setHerokuId(int herokuId) {
        this.herokuId = herokuId;
    }

    public int getZomatoId() {
        return zomatoId;
    }

    public void setZomatoId(int zomatoId) {
        this.zomatoId = zomatoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public double getStarRating() {
        return starRating;
    }

    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public int getSource() {
        return this.source;
    }

    public void setSource(int source) {
        if(source != 0 && source != 1 && source != 2)
            throw new IllegalArgumentException("Invalid source value: " + source);
        this.source = source;
    }


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