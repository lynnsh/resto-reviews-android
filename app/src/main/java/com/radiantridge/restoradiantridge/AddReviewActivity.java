package com.radiantridge.restoradiantridge;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;

import java.net.URL;

public class AddReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
    }

    /**
     * AddReviewTask is the AsyncTask class that is responsible to
     * saves the reviews to the back-end database on heroku.
     */
    private class AddReviewTask extends AsyncTask<Review, Void, Boolean> {
        public static final String TAG = "AddReviewTask";

        /**
         * Saves user inputted reviews to the database on Heroku.
         * @param reviews the reviews to save.
         * @return true if the operation was successful; false otherwise.
         */
        @Override
        public Boolean doInBackground(Review... reviews) {
            boolean noErrors = true;
            String herokuAddReviewUrl = "https://radiant-ridge-88291.herokuapp.com/api/api/add-review";
            HttpPostSender sender = new HttpPostSender();
            for(Review review : reviews) {
                JsonObject json = review.toJsonObject();
                //add email and password to authenticate the user.
                SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
                json.addProperty("email", prefs.getString("email", ""));
                json.addProperty("password", prefs.getString("password", ""));
                Log.d(TAG, "Json object to send: " + json);
                //there was no error if the id returned was not -1
                noErrors = noErrors && (sender.send(json.toString(), herokuAddReviewUrl) != -1);
            }
            return noErrors;
        }
    }
}
