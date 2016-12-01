package com.radiantridge.restoradiantridge;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * GPS Tracker for use with the Nearby Activity.  Provides the current
 * Latitude and Longitude of the device.
 *
 * @author Erika Bourque
 */
public class GPSTracker extends Service implements LocationListener {
    // TODO: ask about ActivityCompat.checkSelfPermission
    private final String TAG = "GPSTracker";
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for location manager's ability to get location
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    /**
     * Constructor.  Sets the context for the
     * tracker and gets the initial location.
     *
     * @param context The tracker's context
     */
    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * This method retrieves the current location and sets up the location
     * manager to listen for updates on the location.
     *
     * @return The current location
     */
    public Location getLocation() {
        // Check if permissions were revoked by user
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

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
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return location;
    }

    /**
     * This method will remove the update listener from the
     * location manager.
     */
    public void stopUsingGPS() {
        // Check if permissions were revoked by user
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // Making sure locationManager exists
        if (locationManager != null) {
            Log.i(TAG, "Removing location updates.");
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * This method returns the latitude of the current location.
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * This method returns the longitude of the current location.
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * This method checks if the location manager is able to
     * get the current location.
     *
     * @return boolean      True if the location manager can get the location
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * This method displays an alert dialog asking the user if they wish
     * to turn on their GPS through the settings.
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle(R.string.dialog_GPS_title);

        // Setting Dialog Message
        alertDialog.setMessage(R.string.dialog_GPS_text);

        // On pressing Settings button
        alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
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

    @Override
    public void onLocationChanged(Location location) {
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

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
