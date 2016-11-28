package com.radiantridge.restoradiantridge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends Menu {
    private static final String TAG = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        // Making sure we have all values
        if ((prefs.getString("fname", null) != null)
                && (prefs.getString("lname", null) != null)
                && (prefs.getString("email", null) != null)
                && (prefs.getString("password", null) != null))
        {
            Log.i(TAG, "SharedPrefs found, launching MainResto.");

            // Starting the main resto activity
            //startActivity(new Intent(this, MainRestoActivity.class));
        }
    }
}
