package com.radiantridge.restoradiantridge;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

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
 * Created by aline on 05/12/16.
 */

public class GetRestosTask extends AsyncTask<Double, Void, Restaurant[]> {
    public static final String TAG = "GetRestosTask";
    private URL herokuGetRestosUrl;
    @Override
    public Restaurant[] doInBackground(Double... params) {
        List<Restaurant> restos = new ArrayList<>();
        double latitude = params[0];
        double longitude = params[1];
        try {
            URL url = new URL("https://radiant-ridge-88291.herokuapp.com/api/api/restos?latitude="
                    + latitude + "&longitude=" + longitude);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            // specifies whether this connection allows receiving data
            http.setDoInput(true);
            http.connect();
            int response = http.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream is = http.getInputStream();
                read(is, restos);
            }
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        catch(JSONException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return restos.toArray(new Restaurant[]{});
    }

    private void read(InputStream is, List<Restaurant> restos) throws IOException, JSONException {
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
            Restaurant resto = new Restaurant();
            resto.setHerokuId(row.getInt("id"));
            resto.setName(row.getString("name"));
            resto.setGenre(row.getString("genre"));
            resto.setPriceRange(row.getInt("price"));
            resto.setStarRating(row.optDouble("rating", 0));
            resto.setSource(2);
            resto.setAddress(row.getString("address"));
            resto.setLatitude(row.getDouble("latitude"));
            resto.setLongitude(row.getDouble("longitude"));
        }

    }
}
