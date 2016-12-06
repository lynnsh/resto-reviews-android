package com.radiantridge.restoradiantridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class SyncBackEnd {
    public static final String TAG = "SyncBackEnd";
    private DatabaseConnector dbh;
    private Context context;
    private URL herokuCreateRestoUrl;

    public SyncBackEnd(Context context) {
        this.context = context;
        this.dbh = DatabaseConnector.getDatabaseConnector(context);


    }

    public boolean start() {
        //check to see if we can get on the network
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        try {
            if (networkInfo != null && networkInfo.isConnected()) {
                herokuCreateRestoUrl = new URL("https://radiant-ridge-88291.herokuapp.com/api/api/create");
                Restaurant[] restos = dbh.getAllRestos();
                new SyncHerokuTask().execute(restos);
                return true;
            }
            else
                return false;
        }
        catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }


    private class SyncHerokuTask extends AsyncTask<Restaurant, Void, Boolean> {
        @Override
        public Boolean doInBackground(Restaurant... restos) {
            boolean noErrors = true;
            for(Restaurant resto : restos) {
                if(resto.getSource() != 2) {
                    JsonObject json = resto.toJsonObject();
                    SharedPreferences prefs = context.getSharedPreferences("Settings", context.MODE_PRIVATE);
                    json.addProperty("email", prefs.getString("email", ""));
                    json.addProperty("password", prefs.getString("password", ""));
                    noErrors = noErrors && addRestoToHeroku(json.toString());
                }
            }

            return noErrors;
        }

        private boolean addRestoToHeroku(String resto) {
            OutputStream out;
            HttpsURLConnection conn = null;
            try {
                byte[] toSend = resto.getBytes();
                conn = (HttpsURLConnection) herokuCreateRestoUrl.openConnection();
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
}
