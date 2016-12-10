package com.radiantridge.restoradiantridge.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.radiantridge.restoradiantridge.fragments.RestoListFragment;
import com.radiantridge.restoradiantridge.objects.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * GetRestosTask class is responsible to retrieve the restaurants
 * from back-end database on Heroku that are close to the provided
 * latitude and longitude.
 *
 * @author Alena Shulzhenko
 * @version 2016-12-09
 */
public class GetRestosTask extends AsyncTask<Double, Void, Restaurant[]> {
    public static final String TAG = "GetRestosTask";
    private RestoListFragment fragment;

    /**
     * Constructor.  Requires the fragment that will display the
     * list of restaurants.
     *
     * @param fragment      The fragment that will display the restaurants
     */
    public GetRestosTask(RestoListFragment fragment)
    {
        this.fragment = fragment;
    }

    /**
     * Using the provided latitude and logitude, retrieves the list
     * of restaurants from Heroku database.
     * @param params latitude and longitude the retrieved restaurants are close to.
     * @return the list of restaurants that are close to the provided latitude and longitude.
     */
    @Override
    public Restaurant[] doInBackground(Double... params) {
        Log.d(TAG, "in doInBackground()");
        List<Restaurant> restos = new ArrayList<>();
        double latitude = params[0];
        double longitude = params[1];
        try {
            //GET url to Heroku
            URL url = new URL("https://radiant-ridge-88291.herokuapp.com/api/api/restos?latitude="
                    + latitude + "&longitude=" + longitude);
            HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            //allow receiving data
            http.setDoInput(true);
            http.connect();
            int response = http.getResponseCode();
            if (response == HttpsURLConnection.HTTP_OK) {
                InputStream is = http.getInputStream();
                retrieveRestos(is, restos);
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

    /**
     * Retrieves the list of restaurants using the input stream.
     * @param is the InputStream object.
     * @param restos the list of restaurants to fill.
     * @throws IOException If there is a connection problem.
     * @throws JSONException If the received JSON format was invalid.
     */
    private void retrieveRestos(InputStream is, List<Restaurant> restos) throws IOException, JSONException {
        int bytesRead;
        byte[] buffer = new byte[3072];
        Log.d(TAG, "in retrieveRestos()");
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
        getRestoList(array, restos);
    }

    /**
     * Parses JSONArray in order to fill up the Retaurants list.
     * @param array the array to parse with the restaurants data.
     * @param restos the restaurants array to fill with data from heroku.
     * @throws JSONException If there is a problem when parsing JSON.
     */
    private void getRestoList(JSONArray array, List<Restaurant> restos) throws JSONException {
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
            restos.add(resto);
            Log.d(TAG, "Added resto: " + resto);
        }
    }

    /**
     * Overriden method.  Gives the list of Restaurants to
     * the fragment for display.
     *
     * @param list      The list of Restaurants to be displayed
     */
    @Override
    protected void onPostExecute(Restaurant[] list)
    {
        fragment.addToList(list, false);
    }
}
