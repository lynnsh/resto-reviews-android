package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainRestoActivity extends Menu {
    private static final String TAG = "Resto Main Activity";

    /**
     * Overriden lifecycle method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resto);

        // change greetingTxt to greeting string + user name from sharedprefs
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
        startActivity(new Intent(this, AddRestoActivity.class));
    }
}
