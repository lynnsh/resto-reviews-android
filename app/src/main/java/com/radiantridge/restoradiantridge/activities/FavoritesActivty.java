package com.radiantridge.restoradiantridge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.radiantridge.restoradiantridge.helpers.DatabaseHelper;
import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.fragments.RestoListFragment;

/**
 * This activity displays all the restaurants saved
 * in the user's favorites database.
 *
 * @author Erika Bourque
 * @version 01/12/2016
 */
public class FavoritesActivty extends MenuActivity {
    private final String TAG = "Favorites";
    private final int SHOW_RESTO = 1;
    RestoListFragment fragment;
    DatabaseHelper db;

    /**
     * Overriden lifecycle method.  Gets the list of restaurants
     * and gives it to the fragment for display.
     *
     * @param savedInstanceState    savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize the variables
        db = DatabaseHelper.getDatabaseConnector(this);
        fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.favorites_list);

        // Displaying the restaurants
        Log.i(TAG, "Displaying all restaurants");
        fragment.addToList(db.getAllRestos(), true);
    }

    /**
     * Overriden lifecycle method.  Activites launched
     * by the fragment, and return, will update the db list.
     *
     * @param requestCode   The request code
     * @param resultCode    The result code
     * @param data          The data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "Received result");
        if (requestCode == SHOW_RESTO)
        {
            if (resultCode == RESULT_OK) {
                fragment.addToList(db.getAllRestos(), true);
            }
        }
    }
}
