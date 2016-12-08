package com.radiantridge.restoradiantridge.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.radiantridge.restoradiantridge.helpers.HttpsPostSenderHelper;
import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.objects.Restaurant;
import com.radiantridge.restoradiantridge.objects.Review;

public class AddReviewActivity extends AppCompatActivity {
    private static final String TAG = "Add review Act";
    private Context context;

    private Review review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        this.context = this;
        review = new Review();

        Bundle bundle = getIntent().getExtras();

        Restaurant  resto = (Restaurant) bundle.getSerializable("resto");

        review.setHerokuRestoId(resto.getHerokuId());

    }
    /**
     * This method gets all the data from input fields, validates it,
     * and sends it to heroku.
     * @param v
     */
    public void handleSaveReview(View v){
        EditText editTitle =(EditText) findViewById(R.id.editTitle);
        String title = editTitle.getText().toString();
        if(title != null&& !(title.isEmpty())) {
            review.setTitle(title);
        }
        EditText editContent =(EditText) findViewById(R.id.editContent);
        String content = editContent.getText().toString();
        if(content != null&& !(content.isEmpty())) {
            review.setContent(content);
        }
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBarReview);
        Double rating = (double) ratingBar.getRating();
        review.setRating(rating);
        Log.i(TAG,"rating " + rating);
        AddReviewTask addReviewTask = new AddReviewTask();
        addReviewTask.execute(review);
        Log.i(TAG,"review added.. " + review);

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
            HttpsPostSenderHelper sender = new HttpsPostSenderHelper();
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

        /**
         * Informs the user whether the review addition was successful.
         * @param noErrors true if there were no errors during the review addition; false otherwise.
         */
        @Override
        public void onPostExecute(Boolean noErrors) {
            //leave the activity if the addition of the review was successful
            if(noErrors) {
                Toast.makeText(context, R.string.review_success, Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
            }
            else
                Toast.makeText(context, R.string.review_error, Toast.LENGTH_SHORT).show();
        }
    }
}
