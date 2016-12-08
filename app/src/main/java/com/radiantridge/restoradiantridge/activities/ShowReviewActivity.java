package com.radiantridge.restoradiantridge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.objects.Review;

/**
 * Created by Rafia on 2016-12-08.
 */

public class ShowReviewActivity extends AppCompatActivity {
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

    }
}

