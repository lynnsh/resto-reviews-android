package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Menu for the restaurant application. All activities extend
 * this menu.
 *
 * @author Erika Bourque
 */
public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "Menu";

    /**
     * Overriden lifecycle method.  Displays the menu.
     *
     * @param menu      The menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    /**
     * Overriden lifecycle method.  Launches the activity
     * associated to the menuOption.
     *
     * @param menuOption    The menu option selected
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuOption)
    {
        switch(menuOption.getItemId()) {
            case R.id.about:
                Log.i(TAG, "About selected.");
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.dawson:
                Log.i(TAG, "Dawson selected.");
                // Victor this is where you launch the dawson website
            case R.id.settings: {
                Log.i(TAG, "Settings selected.");
                startActivity(new Intent(this, SettingsActivity.class));
            }
            default:
                return super.onOptionsItemSelected(menuOption);
        }
    }
}
