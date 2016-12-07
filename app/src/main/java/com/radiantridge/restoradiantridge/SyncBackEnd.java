package com.radiantridge.restoradiantridge;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

/**
 * SyncBackEnd class is responsible to sync all locally saved restos
 * to the back-end database.
 *
 * @author Alena Shulzhenko
 * @version 2016-12-06
 */

public class SyncBackEnd {
    public static final String TAG = "SyncBackEnd";
    private DatabaseConnector dbh;
    private Context context;
    private String herokuCreateRestoUrl;

    /**
     * Instantiates SyncBackEnd object.
     * @param context the Context object.
     */
    public SyncBackEnd(Context context) {
        this.context = context;
        this.dbh = DatabaseConnector.getDatabaseConnector(context);
    }

    /**
     * Starts the sync to the back-end database.
     */
    public void start() {
        //check to see if we can get on the network
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            herokuCreateRestoUrl = "https://radiant-ridge-88291.herokuapp.com/api/api/create";
            Restaurant[] restos = dbh.getAllRestos();
            new SyncHerokuTask().execute(restos);
        }
        //no connection
        else {
            Log.d(TAG, "No connection available.");
            Toast.makeText(context, R.string.net_error, Toast.LENGTH_SHORT);
        }
    }

    /**
     * The class that is responsible to send locally-created and zomato-saved
     * restaurants to the back-end database on Heroku.
     *
     */
    private class SyncHerokuTask extends AsyncTask<Restaurant, Void, Boolean> {

        /**
         * Indicates to the user that the sync has started.
         */
        @Override
        public void onPreExecute() {
            Toast.makeText(context, R.string.sync_start, Toast.LENGTH_SHORT);
        }

        /**
         * Informs the user whether the sync was successful.
         * @param noErrors true if there were no errors during the sync; false otherwise.
         */
        @Override
        public void onPostExecute(Boolean noErrors) {
            if(noErrors)
                Toast.makeText(context, R.string.sync_success, Toast.LENGTH_SHORT);
            else
                Toast.makeText(context, R.string.sync_error, Toast.LENGTH_SHORT);
        }

        /**
         * Sync local and zomato-saved resturants to Heroku database.
         * @param restos the restaurants to save on heroku.
         * @return true if there were no errors during the sync; false otherwise.
         */
        @Override
        public Boolean doInBackground(Restaurant... restos) {
            boolean noErrors = true;
            HttpPostSender sender = new HttpPostSender();
            for(Restaurant resto : restos) {
                //check if this resto was not from heroku initially
                if(resto.getSource() != 2) {
                    JsonObject json = resto.toJsonObject();
                    //add email and password to authenticate the user.
                    SharedPreferences prefs = context.getSharedPreferences("Settings", context.MODE_PRIVATE);
                    json.addProperty("email", prefs.getString("email", ""));
                    json.addProperty("password", prefs.getString("password", ""));
                    noErrors = noErrors && sender.send(json.toString(), herokuCreateRestoUrl);
                    Log.d(TAG, "Json object to send: " + json);
                }
            }
            return noErrors;
        }
    }
}
