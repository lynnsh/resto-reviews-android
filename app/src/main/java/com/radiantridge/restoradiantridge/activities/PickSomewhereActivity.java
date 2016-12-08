package com.radiantridge.restoradiantridge.activities;

import android.content.Intent;
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
    RestoListFragment fragment;

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

        startPlacePicker();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection to google play services failed.");
        // TODO: implement this?
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                getData(place.getLatLng().latitude, place.getLatLng().longitude);
            }
        }
    }

    private void getData(Double latitude, Double longitude)
    {
        Log.i(TAG, "Retrieving data from Zomato and Heroku");
        ZomatoTask conn = new ZomatoTask(fragment);
        conn.execute(latitude, longitude);

        GetRestosTask task = new GetRestosTask(fragment);
        task.execute(latitude, longitude);
    }
}
