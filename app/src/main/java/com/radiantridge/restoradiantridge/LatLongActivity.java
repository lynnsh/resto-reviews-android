package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LatLongActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lat_long);
    }

    public void save(View view)
    {
        EditText latitudeET = (EditText) findViewById(R.id.latitude_ET);
        EditText longitudeET = (EditText) findViewById(R.id.longitude_ET);
        String latStr = latitudeET.getText().toString();
        String longStr = longitudeET.getText().toString();

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
