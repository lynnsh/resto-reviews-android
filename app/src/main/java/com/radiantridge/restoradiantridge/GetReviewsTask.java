package com.radiantridge.restoradiantridge;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * GetReviewsTask class is responsible to retrieve the reviews
 * from back-end database on Heroku that are associated with the provided
 * restaurant.
 *
 * @author Alena Shulzhenko
 * @version 2016-12-06
 */
public class GetReviewsTask extends AsyncTask<Integer, Void, Review[]> {
    public static final String TAG = "GetReviewsTask";

    /**
     * Using the provided restaurant id, retrieves the list
     * of reviews from Heroku database.
     * @param params the restaurant id for which the reciews are required.
     * @return the list of reviews that are associated with the provided restaurant.
     */
    @Override
    public Review[] doInBackground(Integer... params) {
        List<Review> reviews = new ArrayList<>();
        double restoId = params[0];
        try {
            //GET url to Heroku
            URL url = new URL("https://radiant-ridge-88291.herokuapp.com/api/api/reviews?resto_id="
                    + restoId);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            //allow receiving data
            http.setDoInput(true);
            http.connect();
            int response = http.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream is = http.getInputStream();
                retrieveReviews(is, reviews);
            }
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        catch(JSONException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return reviews.toArray(new Review[]{});
    }

    /**
     * Retrieves the list of reviews using the input stream.
     * @param is the InputStream object.
     * @param reviews the list of reviews to fill.
     * @throws IOException If there is a connection problem.
     * @throws JSONException If the received JSON format was invalid.
     */
    private void retrieveReviews(InputStream is, List<Review> reviews) throws IOException, JSONException {
        int bytesRead;
        byte[] buffer = new byte[1024];

        //for data from the server
        BufferedInputStream bufferedInStream = new BufferedInputStream(is);
        //to collect data in output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(byteArrayOutputStream);

        //read the stream until end
        while ((bytesRead = bufferedInStream.read(buffer)) != -1) {
            writer.write(buffer);
        }
        writer.flush();
        JSONArray array = new JSONArray(byteArrayOutputStream.toString());
        getReviewsList(array, reviews);
    }

    /**
     * Parses JSONArray in order to fill up the Reviews list.
     * @param array the array to parse with the reviews data.
     * @param reviews the reviews array to fill with data from heroku.
     * @throws JSONException If there is a problem when parsing JSON.
     */
    private void getReviewsList(JSONArray array, List<Review> reviews) throws JSONException {
        for(int i = 0; i < array.length(); i++) {
            JSONObject row = array.getJSONObject(i);
            Review review = new Review();
            review.setHerokuRestoId(row.getInt("resto_id"));
            review.setContent(row.getString("content"));
            review.setRating(row.getInt("rating"));
            review.setTitle(row.getString("title"));
            reviews.add(review);
            Log.d(TAG, "Added review: " + review);
        }
    }
}
