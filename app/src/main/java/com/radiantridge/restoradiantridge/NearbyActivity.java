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

public class NearbyActivity extends MenuActivity {
    private final String TAG = "Nearby";
    GPSTracker tracker;
    RestoListFragment fragment;
    double latitude;
    double longitude;
    private final int CHANGE_LAT_LONG = 1;
    private final int MY_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        // Instantiate Variables
        setUpTracker();
        fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.nearby_list);
        updateLatLong();
    }

    private void setUpTracker()
    {
        tracker = new GPSTracker(this);

        if (!tracker.canGetLocation())
        {
            Log.i(TAG, "Unable to get location.");
            //showSettingsAlert();
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

        // Setting Dialog Title
        alertDialog.setTitle(R.string.dialog_GPS_title);

        // Setting Dialog Message
        alertDialog.setMessage(R.string.dialog_GPS_text);

        // On pressing Settings button
        alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void launchLatLong(View view)
    {
        Log.i(TAG, "Setting lat and long manually.");
        // start for result
        startActivityForResult(new Intent(this, LatLongActivity.class), CHANGE_LAT_LONG);
    }

    private void updateLatLong()
    {
        TextView latitudeTV = (TextView) findViewById(R.id.nearby_lat);
        TextView longitudeTV = (TextView) findViewById(R.id.nearby_long);
        String latStr = getResources().getString(R.string.latitude) + latitude;
        String longStr = getResources().getString(R.string.longitude) + longitude;

        latitudeTV.setText(latStr);
        longitudeTV.setText(longStr);
    }

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
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                tracker.getLocation();
                getTrackerLatLong();
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
    }

    private void getTrackerLatLong()
    {
        latitude = tracker.getLatitude();
        longitude = tracker.getLongitude();
    }
}
