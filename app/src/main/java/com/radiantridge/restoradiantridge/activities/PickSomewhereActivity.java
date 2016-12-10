package com.radiantridge.restoradiantridge.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.radiantridge.restoradiantridge.asynctasks.ZomatoTask;
import com.radiantridge.restoradiantridge.asynctasks.GetRestosTask;
import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.fragments.RestoListFragment;

public class PickSomewhereActivity extends MenuActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private final int PLACE_PICKER_REQUEST = 1;
    private final String TAG = "PickSomewhere";
    private RestoListFragment fragment;
    private double latitude;
    private double longitude;

    /**
     * Overriden lifecycle method.  Immediately launches
     * the Google Place Picker to get a place from
     * the user.
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_somewhere);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.picker_list);

        // When rotating, do not want to restart place picker, list saved automatically
        if ((savedInstanceState == null) || (savedInstanceState.getDouble("latitude") == 0.0)
                || (savedInstanceState.getDouble("longitude") == 0.0))
        {
            // Check that network is available
            if (!isInternetAvailable())
            {
                // TODO: force close even if back pressed
                displayNetworkError();
            }
            else {
                startPlacePicker();
            }
        }
        else
        {
            latitude = savedInstanceState.getDouble("latitude");
            longitude = savedInstanceState.getDouble("longitude");
            getData();
        }
    }

    /**
     * Overriden method.
     *
     * @param connectionResult  The connection result
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection to google play services failed.");
        // TODO: implement this?
    }

    /**
     * This method launches the Google Place Picker.
     */
    private void startPlacePicker()
    {
        Log.i(TAG, "Starting PlacePicker");
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Overriden lifecycle method.  If the place picker returns a result,
     * it sends the result to get data.
     *
     * @param requestCode   The request code
     * @param resultCode    The result code
     * @param data          The result's data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                getData();
            }
        }
    }

    /**
     * This method uses the latitude and longitude to
     * request restaurants near that location from
     * Zomato and Heroku.
     */
    private void getData()
    {
        Log.i(TAG, "Retrieving data from Zomato and Heroku");
        ZomatoTask conn = new ZomatoTask(fragment);
        conn.execute(latitude, longitude);

        GetRestosTask task = new GetRestosTask(fragment);
        task.execute(latitude, longitude);
    }

    /**
     * Overriden lifecycle method.  Saves the latitude and longitude
     * chosen.
     *
     * @param outstate  The outgoing saved instance state
     */
    @Override
    protected void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        outstate.putDouble("latitude", latitude);
        outstate.putDouble("longitude", longitude);
    }

    /**
     * This method checks if the device has access to the internet.
     *
     * @return      True if the device is connected to the internet
     */
    private boolean isInternetAvailable()
    {
        boolean isAvailable = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if ((activeNetwork != null) &&
                activeNetwork.isConnectedOrConnecting())
        {
            // There is an internet connection
            isAvailable = true;
        }

        return isAvailable;
    }

    /**
     * This method displays an error alert dialog to inform
     * the user that the device is not connected to the internet,
     * and closes the activity.
     */
    private void displayNetworkError()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Fields
        alertDialog.setTitle(R.string.dialog_network_title).setMessage(R.string.dialog_network_text);

        alertDialog.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        // Displays the dialog
        alertDialog.show();
    }
}
