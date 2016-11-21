package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Menu extends AppCompatActivity {
    private static final String TAG = "Menu";

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

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
                // to do
            }
            default:
                return super.onOptionsItemSelected(menuOption);
        }
    }
}