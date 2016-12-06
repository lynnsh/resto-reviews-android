package com.radiantridge.restoradiantridge;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by aline on 05/12/16.
 */

public class AddReviewTask extends AsyncTask<JsonObject, Void, Boolean> {
    public static final String TAG = "AddReviewTask";
    private URL herokuAddReviewUrl;
    @Override
    public Boolean doInBackground(JsonObject... params) {
        try {
            herokuAddReviewUrl = new URL("https://radiant-ridge-88291.herokuapp.com/api/api/add-review");
        }
        catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        JsonObject json = params[0];
        /*SharedPreferences prefs = context.getSharedPreferences("Settings", context.MODE_PRIVATE);
        json.addProperty("email", prefs.getString("email", ""));
        json.addProperty("password", prefs.getString("password", ""));*/
        return addReviewToHeroku(json.toString());
    }

    private boolean addReviewToHeroku(String review) {
        OutputStream out;
        HttpsURLConnection conn = null;
        try {
            byte[] toSend = review.getBytes();
            conn = (HttpsURLConnection) herokuAddReviewUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8");
            // set length of POST data to send
            conn.addRequestProperty("Content-Length", toSend.length+"");

            //send the POST out
            out = new BufferedOutputStream(conn.getOutputStream());

            out.write(toSend);
            out.flush();
            out.close();

            int response = conn.getResponseCode();
            if (response != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Server returned: " + response + " aborting read.");
                return false;
            }
            return true;

        }
        catch(IOException io) {
            Log.e(TAG, io.getMessage());
            return false;
        }
        finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (IllegalStateException ignore) {}
            }
        }
    }
}
