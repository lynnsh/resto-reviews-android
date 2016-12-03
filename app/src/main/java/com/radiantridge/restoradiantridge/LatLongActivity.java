package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This activity provides the user with a method to set
 * their latitude and longitude for nearby restaurants manually.
 *
 * @author Erika Bourque
 * @version 01/12/2016
 */
public class LatLongActivity extends MenuActivity {

    /**
     * Overriden lifecycle method.
     *
     * @param savedInstanceState    savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lat_long);
    }

    /**
     * Event handler for the save button.  Verifies
     * that the fields have been set and sends the data for
     * saving.
     *
     * @param view      The view that fired the event
     */
    public void save(View view)
    {
        // Getting the data from the EditTexts
        EditText latitudeET = (EditText) findViewById(R.id.latitude_ET);
        EditText longitudeET = (EditText) findViewById(R.id.longitude_ET);
        String latStr = latitudeET.getText().toString();
        String longStr = longitudeET.getText().toString();

        // Making sure the fields were not empty
        if (!latStr.isEmpty() && !longStr.isEmpty())
        {
            // Edittexts force decimals only, can simply parse
            sendData(Double.parseDouble(latStr), Double.parseDouble(longStr));
        }
        else
        {
            // Display error message
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT);
        }
    }

    /**
     * This method saves the data in the result intent
     * for the calling activity, and ends this activity.
     *
     * @param latitude      The latitude to be sent
     * @param longitude     The longitude to be sent
     */
    private void sendData(Double latitude, Double longitude)
    {
        // Creating Intent
        Intent data = new Intent();

        // Saving data
        data.putExtra("latitude", latitude);
        data.putExtra("longitude", longitude);

        // Setting result and ending activity
        setResult(RESULT_OK, data);
        finish();
    }
}
