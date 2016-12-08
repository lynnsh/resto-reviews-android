package com.radiantridge.restoradiantridge;

import android.os.Bundle;
import android.util.Log;

/**
 * This activity displays all the restaurants saved
 * in the user's favorites database.
 *
 * @author Erika Bourque
 * @version 01/12/2016
 */
public class FavoritesActivty extends MenuActivity {
    private final String TAG = "Favorites";

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
        DatabaseConnector db = DatabaseConnector.getDatabaseConnector(this);
        RestoListFragment fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.favorites_list);

        // Displaying the restaurants
        Log.i(TAG, "Displaying all restaurants");
        fragment.setNewList(db.getAllRestos());
    }
}
