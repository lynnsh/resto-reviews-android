package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Initial activity for the restaurant application.  Logs in the
 * user if log details are not present in the shared preferences,
 * and launches the restaurant's main activity.
 *
 * @author Erika Bourque
 */
public class MainActivity extends Menu {

    /**
     * Overriden lifecycle method.  Creates the view, and checks if login details
     * are present in the shared preferences.  If they are present, it will launch
     * the restaurant's main activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // should this be in the if-else?
        setContentView(R.layout.activity_main);

        // check saved prefs here, put next in if statement

        // Starting the restaurant's main activity
        startActivity(new Intent(this, MainRestoActivity.class));
    }

    // log in functionality

    // save the login in and redirect to MainRestoActivity
}
