package com.radiantridge.restoradiantridge;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by 1141669 on 11/21/2016.
 */

public class ZomatoConnector {
    private final String TAG="ZomatoConn";
    private final String userKey = "7aa3592c74a89f7580be9be959fde45b";
    // follow these by lat=num&lon=num
    private final String nearbyUrl = "https://developers.zomato.com/api/v2.1/geocode?";
    private final String cuisinesUrl = "https://developers.zomato.com/api/v2.1/cuisines?";

    public List<Restaurant> getNearbyRestaurants(double latitude, double longitude)
    {
        try {
            // Construct the request
            URL url = new URL(nearbyUrl + "lat=" + latitude + "&lon=" + longitude);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestProperty("user-key", userKey);
            urlConn.setRequestMethod("GET");
            urlConn.setReadTimeout(10000);
            urlConn.setConnectTimeout(15000);
            urlConn.setDoInput(true);

            // Send the request
            urlConn.connect();

            // Get results
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                return processNearbyJSON(convertStreamToString(urlConn.getInputStream()));
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    private List<Restaurant> processNearbyJSON(String response)
    {
        return null;
    }

    private String convertStreamToString(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = in.readLine()) != null)
        {
            sb.append(line + "\n");
        }

        return sb.toString();
    }
}
