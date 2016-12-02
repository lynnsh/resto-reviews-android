package com.radiantridge.restoradiantridge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * This activity gets the location from the device's GPS and uses it to get and display a list of
 * nearby restaurants using the ZomatoConnector.
 *
 * @author Erika Bourque
 * @version 01/12/2016
 */
public class NearbyActivity extends MenuActivity {
    private final String TAG = "Nearby";
    GPSTracker tracker;
    RestoListFragment fragment;
    double latitude;
    double longitude;
    private final int CHANGE_LAT_LONG = 1;
    private final int MY_PERMISSION_REQUEST = 1;

    /**
     * Overriden lifecycle method.  Starts the GPS Tracker,
     * and starts the query process for the ZomatoConnector.
     *
     * @param savedInstanceState    savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.nearby_list);

        // Getting latitude and longitude data
        setUpTracker();
        updateLatLong();

        // Start Zomato query
        getListFromZomato();
    }

    /**
     * This method sets up the GPS Tracker and checks if the GPS is turned on.
     */
    private void setUpTracker()
    {
        tracker = new GPSTracker(this);

        if (!tracker.canGetLocation())
        {
            Log.i(TAG, "Unable to get location.");
            showSettingsAlert();
            // Attempt to get location again
            tracker.getLocation();
        }
        getTrackerLatLong();
    }

    /**
     * This method displays an alert dialog asking the user if they wish
     * to turn on their GPS through the settings.
     */
    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Fields
        alertDialog.setTitle(R.string.dialog_GPS_title).setMessage(R.string.dialog_GPS_text);

        // Setting up handlers for the buttons
        alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Displays the dialog
        alertDialog.show();
    }

    /**
     * Event handler for the change latitude and longitude
     * button.  Launches the LatLongActivity.
     *
     * @param view      The view that fired the event
     */
    public void launchLatLong(View view)
    {
        Log.i(TAG, "Setting lat and long manually.");
        startActivityForResult(new Intent(this, LatLongActivity.class), CHANGE_LAT_LONG);
    }

    /**
     * This method updates the latitude and longitude displayed
     * on the activity's UI.
     */
    private void updateLatLong()
    {
        TextView latitudeTV = (TextView) findViewById(R.id.nearby_lat);
        TextView longitudeTV = (TextView) findViewById(R.id.nearby_long);
        String latStr = getResources().getString(R.string.latitude) + latitude;
        String longStr = getResources().getString(R.string.longitude) + longitude;

        latitudeTV.setText(latStr);
        longitudeTV.setText(longStr);
    }

    /**
     * Overriden lifecycle method.  Receives the result
     * from the LatLongActivity and updates the latitude
     * and longitude to the values given.
     *
     * @param requestCode   Code for result success
     * @param resultCode    Code for result type
     * @param data          Intent for the data received back
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANGE_LAT_LONG)
        {
            latitude = data.getDoubleExtra("latitude", 0.0);
            longitude = data.getDoubleExtra("longitude", 0.0);
            updateLatLong();
        }
    }

    /**
     * Overriden method.  This method will update the tracker if the
     * permission for GPS access has been allowed.
     * TODO: Overriden here because GPSTracker is not an activity, to change?
     * TODO: make the gps in here instead of outside object
     *
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST)
        {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                tracker.getLocation();
                getTrackerLatLong();
            }
        }
    }

    /**
     * This method gets the latitude and longitude from the
     * tracker.
     */
    private void getTrackerLatLong()
    {
        latitude = tracker.getLatitude();
        longitude = tracker.getLongitude();
    }

    /**
     * This method starts the Zomato Asynchronous Task with
     * the latitude and longitude.
     */
    private void getListFromZomato()
    {
        ZomatoConnector conn = new ZomatoConnector(fragment);
        conn.execute(latitude, longitude);
    }
}
