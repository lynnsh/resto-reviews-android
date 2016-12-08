package com.radiantridge.restoradiantridge.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.asynctasks.GetReviewsTask;
import com.radiantridge.restoradiantridge.objects.Review;

/**
 * Created by Rafia on 2016-12-08.
 */

public class ShowReviewActivity extends MenuActivity {
    private static final String TAG = "Show review Act";
    private Review review;
    /**
     * Overridden Lifecycle method.
     * @param savedInstanceState stores the value when screen rotated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);

        review = new Review();
        // TODO : MAKE A LIST VIEW FOR REVIEWS

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new GetReviewsTask().execute(id);
//        }
        //no connection
//        else {
//            Log.d(TAG, "No connection available.");
//            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
//        }


    }
}

