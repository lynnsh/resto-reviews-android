package com.radiantridge.restoradiantridge;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 1141669 on 11/21/2016.
 * @author rafia
 */

public class Restaurant implements Serializable{

    private String name;
    private int addNum;
    private String addStreet;
    private String addCity;
    private String addPostalCode;
    private String genre;
    private int priceRange; //the range is 1 to 4
    private Timestamp createdTime;  // ?? creation time of what
    private Timestamp modifiedTime;  // ?? of what
    private double starRating; // 1- 5
    private String notes;
    private double longitude;
    private double latitude;
    private int dbId;
    private String phone;
    private int source; //0-direct input, 1-zomato, 2-heroku api.
    private int herokuId; //id heroku expects in order to associate a review with it

    public Restaurant() {
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int herokuId) {
        this.herokuId = herokuId;
    }

    public int getHerokuId() {
        return herokuId;
    }

    public void setHerokuId(int dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddStreet() {
        return addStreet;
    }

    public void setAddStreet(String addStreet) {
        this.addStreet = addStreet;
    }

    public int getAddNum() {


        return addNum;
    }

    public void setAddNum(int addNum) {

        this.addNum = addNum;
    }

    public String getAddCity() {
        return addCity;
    }

    public void setAddCity(String addCity) {
        this.addCity = addCity;
    }

    public String getAddPostalCode() {
        return addPostalCode;
    }

    public void setAddPostalCode(String addPostalCode) {
        this.addPostalCode = addPostalCode;
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

    public void setDatabaseId(int id) {
        this.dbId = id;
    }

    public int getDatabaseId() {
        return this.dbId;
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

        if (addNum != that.addNum) return false;
        if (priceRange != that.priceRange) return false;
        if (Double.compare(that.starRating, starRating) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (dbId != that.dbId) return false;
        if (!name.equals(that.name)) return false;
        if (addStreet != null ? !addStreet.equals(that.addStreet) : that.addStreet != null)
            return false;
        if (addCity != null ? !addCity.equals(that.addCity) : that.addCity != null) return false;
        if (!addPostalCode.equals(that.addPostalCode)) return false;
        if (!genre.equals(that.genre)) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null)
            return false;
        if (modifiedTime != null ? !modifiedTime.equals(that.modifiedTime) : that.modifiedTime != null)
            return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (source != that.source) return false;
        if (herokuId != that.herokuId) return false;
        return phone != null ? phone.equals(that.phone) : that.phone == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + addNum;
        result = 31 * result + (addStreet != null ? addStreet.hashCode() : 0);
        result = 31 * result + (addCity != null ? addCity.hashCode() : 0);
        result = 31 * result + addPostalCode.hashCode();
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
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", addNum=" + addNum +
                ", addStreet='" + addStreet + '\'' +
                ", addCity='" + addCity + '\'' +
                ", addPostalCode='" + addPostalCode + '\'' +
                ", genre='" + genre + '\'' +
                ", priceRange=" + priceRange +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", starRating=" + starRating +
                ", notes='" + notes + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", dbId=" + dbId +
                ", source=" + dbId +
                ", herokuId=" + herokuId +
                ", phone='" + phone + '\'' +
                '}';
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("address", addNum+ " " + addStreet + " " + addCity);
        json.addProperty("postalcode", addPostalCode);
        json.addProperty("genre", genre);
        json.addProperty("price", priceRange);
        return json;
    }
}