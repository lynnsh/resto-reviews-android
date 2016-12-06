package com.radiantridge.restoradiantridge;

import com.google.gson.JsonObject;

/**
 * Created by aline on 06/12/16.
 */

public class Review {
    private String title;
    private String content;
    private double rating;
    private int herokuId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getHerokuId() {
        return herokuId;
    }

    public void setHerokuId(int herokuId) {
        this.herokuId = herokuId;
    }

    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("resto_id", herokuId);
        json.addProperty("title", title);
        json.addProperty("content", content);
        json.addProperty("rating", rating);
        return json;
    }
}
