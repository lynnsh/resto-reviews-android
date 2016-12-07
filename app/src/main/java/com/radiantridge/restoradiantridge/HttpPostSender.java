package com.radiantridge.restoradiantridge;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * The helper class that is responsible for sending a message over a network
 * to a specified URL.
 *
 * @author Alena Shulzhenko
 * @version 2016-12-07
 */

public class HttpPostSender {
    public static final String TAG = "HttpPostSender";

    /**
     * Sends the string to the specified url using POST method.
     * @param message the string to send.
     * @param urlToSend the url where the string is sent.
     * @return the id of the sent object or -1 if the transmission was not successful.
     */
    public int send(String message, String urlToSend) {
        OutputStream out;
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(urlToSend);
            //get message as byte array
            byte[] toSend = message.getBytes();
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //to send data
            conn.setDoOutput(true);
            //to receive data
            conn.setDoInput(true);
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
                return -1;
            }
            else {
                InputStream is = conn.getInputStream();
                return getId(is);
            }
        }
        catch(IOException io) {
            Log.e(TAG, io.getMessage());
            return -1;
        }
        catch(JSONException e) {
            Log.e(TAG, e.getMessage());
            return -1;
        }
        finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (IllegalStateException ignore) {}
            }
        }
    }

    /**
     * Returns the id of the added to Heroku database object.
     * @param is the InputStream object.
     * @return the id of the added to Heroku database object.
     * @throws IOException If there is a connection problem.
     * @throws JSONException If the received JSON format was invalid.
     */
    private int getId(InputStream is) throws IOException, JSONException {
        int bytesRead;
        byte[] buffer = new byte[1024];

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
        JSONObject json = new JSONObject(byteArrayOutputStream.toString());
        return json.getInt("id");
    }
}
