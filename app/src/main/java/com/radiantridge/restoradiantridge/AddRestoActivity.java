package com.radiantridge.restoradiantridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import java.sql.Time;

/**
 * This acitivty takes in user input to add a restaurant to the database.
 */
public class AddRestoActivity extends AppCompatActivity {
    private static final String TAG = "Add resto Act";
  //  private static DAOObject dao;

    /**
     * Overridden Lifecycle method.
     * @param savedInstanceState stores the value when screen rotated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resto);
    }

    /**
     * This method gets all the data from input fields, validates it,
     * and insert it into the database.
     * @param v
     */
    public void saveResto(View v) {
        //do validation
        //check for nulls
        //getting the text values from fields
        EditText editName = (EditText) findViewById(R.id.editRestoName);
        String name = editName.getText().toString();
        EditText editNumber = (EditText) findViewById(R.id.editNum);
        String number = editNumber.getText().toString();
        int num=-1;
        try {
             num = Integer.parseInt(number);
        }
        catch(NumberFormatException nfe)
        {
            Log.e(TAG, nfe.getLocalizedMessage());
        }
        EditText editStreet = (EditText) findViewById(R.id.editStreet);
        String street = editStreet.getText().toString();
        EditText editCity = (EditText) findViewById(R.id.editCity);
        String city = editCity.getText().toString();
        EditText editCode = (EditText) findViewById(R.id.editCode);
        String code = editCode.getText().toString();
        EditText editGenre = (EditText) findViewById(R.id.editGenre);
        String genre = editGenre.getText().toString();
        EditText editPrice = (EditText) findViewById(R.id.editPrice);
        String priceRange = editPrice.getText().toString();
        EditText editNotes = (EditText) findViewById(R.id.editNotes);
        String notes = editNotes.getText().toString();
        EditText editLongitude = (EditText) findViewById(R.id.editLongitude);
        String longitude = editLongitude.getText().toString();
        EditText editLatitude = (EditText) findViewById(R.id.editLatitude);
        String latitude = editLatitude.getText().toString();
        Double longit,latid=0.0;
        try {
             longit = Double.parseDouble(longitude);

             latid = Double.parseDouble(latitude);
        }
        catch(NumberFormatException nfe)
        {
            Log.e(TAG, nfe.getLocalizedMessage());
        }
        RatingBar editRating = (RatingBar) findViewById(R.id.ratingBar);
        Float rating = editRating.getRating();
        Log.i(TAG, "rating bar val" + rating);
        long time = System.currentTimeMillis()/1000;
        Time t = new Time(time);
        Log.i(TAG,"time " +t);
      //  dao.insert(name,num,street,city,code,genre,priceRange,notes,longit,latid,rating,time );
    }
    }
