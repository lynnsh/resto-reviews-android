package com.radiantridge.restoradiantridge;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
public class NearbyActivity extends MenuActivity implements LocationListener {
    private final String TAG = "Nearby";
    RestoListFragment fragment;
    private final int CHANGE_LAT_LONG = 1;
    private final int MY_PERMISSION_REQUEST = 1;
    double latitude;
    double longitude;

    // GPS Related Variables
    private Location location;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 500 * 60 * 1; // 1 minute
    private boolean isGPSEnabled = false;
    private boolean canGetLocation = false;
    private LocationManager locationManager;

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
        initLocation();

//        if (!canGetLocation)
//        {
//            Log.i(TAG, "Unable to get location.");
//            //showSettingsAlert();
//            // Attempt to get location again
//            initLocation();
//        }
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
                dialog.dismiss();
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
            getListFromZomato();
        }
    }

    /**
     * Overriden method.  This method will update the tracker if the
     * permission for GPS access has been allowed.
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
                Log.i(TAG, "Permissions granted");
                // Get new location
                initLocation();
                updateLatLong();
                getListFromZomato();
            }
        }
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

    /**
     * This method retrieves the current location and sets up the location
     * manager to listen for updates on the location.
     *
     * @return The current location
     */
    private void initLocation() {
        Log.i(TAG, "initLocation");
        // Check if permissions were granted by user
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Requesting location permissions");
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST);
            // Can't continue, return
            return;
        }

        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                this.canGetLocation = true;
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d(TAG, "GPS Enabled");
                if (locationManager != null) {
                    Log.d(TAG, "LocationManager was not null");
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.d(TAG, "Location was not null");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.i(TAG, "Latitude: " + latitude);
                        Log.i(TAG, "Longitude: " + longitude);
                    }
                }
            }
            else
            {
                showSettingsAlert();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * This method will remove the update listener from the
     * location manager.
     */
    private void stopUsingGPS() {
        // Check if permissions were revoked by user
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Can't remove updates if its already disabled
            return;
        }
        // Making sure locationManager exists
        if (locationManager != null) {
            Log.i(TAG, "Removing location updates.");
            locationManager.removeUpdates(this);
        }
    }

    /**
     * Overriden method.  Updates the location, latitude and longitude
     * when the location changes.
     *
     * @param location      The new location
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Location changed");
        this.location = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        updateLatLong();
        getListFromZomato();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    /**
     * Overriden lifecycle method.  Stops the GPS listener.
     */
    @Override
    protected void onPause()
    {
        Log.i(TAG, "onPause");
        super.onPause();
        stopUsingGPS();
    }

    @Override
    protected void onResume()
    {
        Log.i(TAG, "onResume");
        super.onResume();

        // Getting latitude and longitude data
        setUpTracker();
        updateLatLong();

        // Start Zomato query
        getListFromZomato();
    }

//    private void queryLocation()
//    {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // Can't remove updates if its already disabled
//            return;
//        }
//
//        if (locationManager != null) {
//            Log.d(TAG, "LocationManager was not null");
//            location = locationManager
//                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                Log.d(TAG, "Location was not null");
//                latitude = location.getLatitude();
//                longitude = location.getLongitude();
//                Log.i(TAG, "Latitude: " + latitude);
//                Log.i(TAG, "Longitude: " + longitude);
//            }
//        }
//    }
}