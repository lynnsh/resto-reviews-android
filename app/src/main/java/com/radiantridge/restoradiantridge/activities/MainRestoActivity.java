package com.radiantridge.restoradiantridge.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.radiantridge.restoradiantridge.R;

/**
 * This activity represents the main activity, where users can decide
 * what they want to do in the application.  It is the central launcher for
 * most other activities.
 *
 * @author Erika Bourque
 * @version 30/11/2016
 */
public class MainRestoActivity extends MenuActivity {
    private static final String TAG = "Resto Main Activity";

    /**
     * Overriden lifecycle method.  Displays the user's name
     * in the greeting.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resto);

        updateGreeting();
    }

    /**
     * Event handler for the favorites button.  Launches
     * the favorites activity.
     *
     * @param view      The view that fired the event
     */
    public void launchFavorites(View view)
    {
        startActivity(new Intent(this, FavoritesActivty.class));
    }

    /**
     * Event handler for the search button.  Launches
     * the search activity.
     *
     * @param view      The view that fired the event
     */
    public void launchSearch(View view)
    {
        startActivity(new Intent(this, SearchActivity.class));
    }

    /**
     * Event handler for the nearby button.  Launches
     * the nearby activity.
     *
     * @param view      The view that fired the event
     */
    public void launchNearby(View view)
    {
        startActivity(new Intent(this, NearbyActivity.class));
    }

    /**
     * Event handler for the tip calculator button.
     * Launches the tip calculator activity.
     *
     * @param view      The view that fired the event
     */
    public void launchTipCalc(View view)
    {
        startActivity(new Intent(this, TipCalcActivity.class));
    }

    /**
     * Event handler for the add restaurant button.
     * Launches the add restaurant activity.
     *
     * @param view      The view that fired the event
     */
    public void launchAddResto(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("isNew", true);
        startActivity(new Intent(this, AddRestoActivity.class));
    }

    /**
     * Updates the greeting to display the user's name.
     */
    private void updateGreeting()
    {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        TextView greetingTV = (TextView) findViewById(R.id.main_greeting);

        String greetingMess = getResources().getString(R.string.greeting)
                + prefs.getString("fname", null) + " " + prefs.getString("lname", null);

        greetingTV.setText(greetingMess);
    }

    /**
     * Event handler for the logo button.  Launches
     * the about activity.
     *
     * @param view      The view that fired the event
     */
    public void launchAbout(View view)
    {
        startActivity(new Intent(this, AboutActivity.class));
    }

    /**
     * Event handler for the logo button.  Launches
     * the place picker activity.
     *
     * @param view      The view that fired the event
     */
    public void launchPicker(View view)
    {
        startActivity(new Intent(this, PickSomewhereActivity.class));
    }
}
