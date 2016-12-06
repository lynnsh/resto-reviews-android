package com.radiantridge.restoradiantridge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private Gson gson;

    public SyncBackEnd(Context context) {
        this.context = context;
        this.dbh = DatabaseConnector.getDatabaseConnector(context);
        this.gson = new Gson();
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
            } else
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
                String json = "";
                for(Restaurant resto : restos) {
                    if(resto.getSource() != 2)

                        //converting the Resto object to JSON
                        json = gson.toJson(resto);
                        addRestoToHeroku(json);
                }

            return true;
        }

        private boolean addRestoToHeroku(String resto) {
            OutputStream out;
            //String contentAsString = "";
            //int response;
            try {
                HttpsURLConnection conn = (HttpsURLConnection) herokuCreateRestoUrl.openConnection();
                conn.setDoOutput(true);
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestProperty("Content-Type",
                        "application/json; charset=UTF-8");
                // set length of POST data to send
                conn.addRequestProperty("Content-Length", bytesLeng.toString());

                //send the POST out
                out = new BufferedOutputStream(conn.getOutputStream());

                out.write(bytes);
                out.flush();
                out.close();

                // logCertsInfo(conn);

                // now get response
                //response = conn.getResponseCode();
            }
            catch(IOException io) {
                Log.e(TAG, io.getMessage());
                return false;
            }
        }
    }
}
