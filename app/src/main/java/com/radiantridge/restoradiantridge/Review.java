package com.radiantridge.restoradiantridge;

import com.google.gson.JsonObject;

/**
 * Review class describes a restaurant review.
 * A Review has a title, content, and a rating.
 * It also stores a heroku id of the restaurant it is associated with.
 *
 * @author Alena Shulzhenko
 * @version 2016-12-06
 */

public class Review {
    private String title;
    private String content;
    private double rating;
    private int herokuRestoId;
    //the user? php returns an id.

    /**
     * Returns the title of the review.
     * @return the title of the review.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the review.
     * @param title the title of the review.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the content of the review.
     * @return the content of the review.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the review.
     * @param content the content of the review.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the rating for the restaurant that this review gives.
     * @return the rating for the restaurant that this review gives.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating for the restaurant according to this review.
     * @param rating the rating for the restaurant according to this review.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Gets the heroku id of the restaurant this review is associated with.
     * @return  the heroku id of the restaurant this review is associated with.
     */
    public int getHerokuRestoId() {
        return herokuRestoId;
    }

    /**
     * Sets the heroku id of the restaurant this review is associated with.
     * @param herokuId  the heroku id of the restaurant this review is associated with.
     */
    public void setHerokuRestoId(int herokuId) {
        this.herokuRestoId = herokuId;
    }

    /**
     * Converts the Review object to JSON.
     * @return the Review object as JSON.
     */
    public JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("resto_id", herokuRestoId);
        json.addProperty("title", title);
        json.addProperty("content", content);
        json.addProperty("rating", rating);
        return json;
    }
}
