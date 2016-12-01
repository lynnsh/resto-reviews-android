package com.radiantridge.restoradiantridge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        // TODO: figure out why it is not working
        tracker = new GPSTracker(this);

        if (!tracker.canGetLocation())
        {
            Log.i(TAG, "Unable to get location.");
            //showSettingsAlert();
            // Attempt to get location again
            tracker.getLocation();
        }

        latitude = tracker.getLatitude();
        longitude = tracker.getLongitude();
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
        startActivity(new Intent(this, LatLongActivity.class));
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
}
