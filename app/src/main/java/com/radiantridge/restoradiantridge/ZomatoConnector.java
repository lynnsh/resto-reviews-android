package com.radiantridge.restoradiantridge;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 1141669 on 11/21/2016.
 */
public class ZomatoConnector extends AsyncTask<Double, Void, Restaurant[]>{
    private final String TAG="ZomatoConn";
    private final String userKey = "7aa3592c74a89f7580be9be959fde45b";
    // follow these by lat=num&lon=num
    private final String nearbyUrl = "https://developers.zomato.com/api/v2.1/geocode?";
    private final String cuisinesUrl = "https://developers.zomato.com/api/v2.1/cuisines?";
    private RestoListFragment fragment;

    public ZomatoConnector(RestoListFragment fragment)
    {
        this.fragment = fragment;
    }

    @Override
    protected Restaurant[] doInBackground(Double...params)
    {
        double latitude = params[0];
        double longitude = params[1];
        InputStream stream = null;
        HttpURLConnection urlConn = null;

        try {
            // Construct the request
            URL url = new URL(nearbyUrl + "lat=" + latitude + "&lon=" + longitude);
            urlConn = (HttpURLConnection) url.openConnection();
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
                stream = urlConn.getInputStream();
                return processNearbyJSON(convertStreamToString(stream));
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (stream != null)
            {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return null;
    }

    private Restaurant[] processNearbyJSON(String response) throws JSONException {
        JSONObject totalResponse = new JSONObject(response);
        JSONArray restoArray = totalResponse.getJSONArray("nearby_restaurants");
        Restaurant[] list = new Restaurant[restoArray.length()];

        for(int i = 0; i < restoArray.length(); i++)
        {
            JSONObject obj = restoArray.getJSONObject(i).getJSONObject("restaurant");
            list[i] = buildRestaurant(obj);
        }

        return list;
    }

    private String convertStreamToString(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = in.readLine()) != null)
        {
            sb.append(line).append("\n");
        }

        return sb.toString();
    }

    private Restaurant buildRestaurant(JSONObject obj) throws JSONException {
        Restaurant resto = new Restaurant();

        if (obj.has("name"))
        {
            resto.setName(obj.getString("name"));
        }

        if (obj.has("location")) // split into address fields
        {
            JSONObject sub = obj.getJSONObject("location");

            if (sub.has("address"))
            {
                parseAddress(resto, sub.getString("address"));
            }
            if (sub.has("zipcode"))
            {
                resto.setAddPostalCode(sub.getString("zipcode"));
            }
            if (sub.has("latitude"))
            {
                resto.setLatitude(sub.getDouble("latitude"));
            }
            if (sub.has("longitude"))
            {
                resto.setLongitude(sub.getDouble("longitude"));
            }
        }

        if (obj.has("cuisines"))
        {
            resto.setGenre(obj.getString("cuisines"));
        }

        if (obj.has("price_range"))
        {
            // TODO: get price_range
        }

        if (obj.has("user_rating")) // get user_rating obj, get aggregate_rating (FOR starRating)
        {
            // TODO: get user_rating
        }

        if (obj.has("phone_numbers"))
        {
            resto.setPhone(obj.getString("phone_numbers"));
        }

        return resto;
    }

    private void parseAddress(Restaurant resto, String address)
    {
        String[] split = address.split(",");
        String[] numAndName = split[0].split(" ", 2);

        // Getting Street Number
        String num = numAndName[0];
        resto.setAddNum(Integer.parseInt(num));

        // Getting Street Name
        if (numAndName.length == 1)
        {
            // They had a comma for num
            resto.setAddStreet(split[1]);
            resto.setAddCity(split[2]);
        }
        else
        {
            resto.setAddStreet(numAndName[1]);
            resto.setAddCity(split[1]);
        }
    }

    @Override
    protected void onPostExecute(Restaurant[] list)
    {
        fragment.setList(list);
    }
}
