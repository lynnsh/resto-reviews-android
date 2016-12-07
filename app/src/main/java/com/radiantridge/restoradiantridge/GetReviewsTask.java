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
 * Created by aline on 06/12/16.
 */

public class GetReviewsTask extends AsyncTask<Integer, Void, Review[]> {
    public static final String TAG = "GetReviewsTask";
    @Override
    public Review[] doInBackground(Integer... params) {
        List<Review> reviews = new ArrayList<>();
        double restoId = params[0];
        try {
            URL url = new URL("https://radiant-ridge-88291.herokuapp.com/api/api/reviews?resto_id="
                    + restoId);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            // specifies whether this connection allows receiving data
            http.setDoInput(true);
            http.connect();
            int response = http.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream is = http.getInputStream();
                read(is, reviews);
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

    private void read(InputStream is, List<Review> reviews) throws IOException, JSONException {
        int bytesRead;
        byte[] buffer = new byte[1024];

        // for data from the server
        BufferedInputStream bufferedInStream = new BufferedInputStream(is);
        // to collect data in our output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(byteArrayOutputStream);

        // read the stream until end
        while ((bytesRead = bufferedInStream.read(buffer)) != -1) {
            writer.write(buffer);
        }
        writer.flush();
        JSONArray array = new JSONArray(new String(byteArrayOutputStream.toString()));
        for(int i = 0; i < array.length(); i++) {
            JSONObject row = array.getJSONObject(i);
            Review review = new Review();
            review.setHerokuId(row.getInt("resto_id"));
            review.setContent(row.getString("content"));
            review.setRating(row.getInt("rating"));
            review.setTitle(row.getString("title"));
            reviews.add(review);
        }

    }
}
