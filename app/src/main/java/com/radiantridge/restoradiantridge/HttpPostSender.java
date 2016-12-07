package com.radiantridge.restoradiantridge;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * The helper class that is responsible for sending a message over a network
 * to a specified URL.
 *
 * @author Alena Shulzhenko
 * @version 2016-12-06
 */

public class HttpPostSender {
    public static final String TAG = "HttpPostSender";

    /**
     * Sends the string to the specified url using POST method.
     * @param message the string to send.
     * @param urlToSend the url where the string is sent.
     * @return true if transmission was successful, false otherwise.
     */
    public boolean send(String message, String urlToSend) {
        OutputStream out;
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(urlToSend);
            //get message as byte array
            byte[] toSend = message.getBytes();
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // set length of POST data to send
            conn.addRequestProperty("Content-Length", toSend.length+"");

            //send the POST out
            out = new BufferedOutputStream(conn.getOutputStream());

            out.write(toSend);
            out.flush();
            out.close();

            int response = conn.getResponseCode();
            //check if the transmission was successful
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
