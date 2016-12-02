package com.radiantridge.restoradiantridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import java.sql.Timestamp;

/**
 * This activity takes in user input to add a restaurant to the database.
 *
 * @author Rafia Anwar
 */
public class AddRestoActivity extends AppCompatActivity {
    private static final String TAG = "Add resto Act";
    private Restaurant resto;
    private String name;
    private int num;
    private String street;
    private String city;
    private String code;
    private String genre;
    private int price;
    private String notes;
    private Double longit;
    private Double latid;
    private float rating;
   private  DatabaseConnector dbconn;
    private boolean save; //keeps check of valid inputs
    /**
     * Overridden Lifecycle method.
     * @param savedInstanceState stores the value when screen rotated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resto);
        resto = new Restaurant();

    }

    /**
     * This method gets all the data from input fields, validates it,
     * and insert it into the database.
     * @param v
     */
    public void saveResto(View v) {
        //make labels work, stop eveyrhitng until valid input
        //getting the text values from fields
        //gets the name field and save it to name string
        handleNameField(v);
        handleNumField(v);
        handleStreetField(v);
        handleCityField(v);
        handleCodeField(v);
        handleGenreField(v);
        handlePriceField(v);
        handleNotesField(v);
        handleLongLatFields(v);
        handleRatingBar(v);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        resto.setCreatedTime(timestamp);
        Log.i(TAG,"time " +timestamp);
        dbconn = DatabaseConnector.getDatabaseConnector(this);
        dbconn.addResto(resto);
    }
    private void handleNameField(View v){
        EditText editName = (EditText) findViewById(R.id.editRestoName);
        name = editName.getText().toString();
        TextView nameErr= (TextView) findViewById(R.id.textNameError);
        if(name != null) {
            resto.setName(name);
            nameErr.setVisibility(View.INVISIBLE);
        }
        else
        {
            nameErr.setVisibility(View.VISIBLE);
        }
    }
    private void handleNumField(View v) {
        EditText editNumber = (EditText) findViewById(R.id.editNum);
        String number = editNumber.getText().toString();
        if (number != null && !(number.isEmpty())) {
            num = Integer.parseInt(number);
            resto.setAddNum(num);
        }
        Log.i(TAG, "NUM " + num);
    }
    private void handleStreetField(View v) {
        EditText editStreet = (EditText) findViewById(R.id.editStreet);
        street = editStreet.getText().toString();

        if (street != null && !(street.isEmpty())) {
            resto.setAddStreet(street);
        }
        Log.i(TAG, "STREET " + street);
    }
    private void handleCityField(View v) {
        EditText editCity = (EditText) findViewById(R.id.editCity);
        city = editCity.getText().toString();

        if (city != null && !(city.isEmpty())) {
            resto.setAddCity(city);
        }
        Log.i(TAG, "city " + city);
    }

    private void handleCodeField(View v) {
        EditText editCode = (EditText) findViewById(R.id.editCode);
        code = editCode.getText().toString();
        TextView codeErr= (TextView) findViewById(R.id.textCodeError);
        String regex = "^[A-Za-z][0-9][A-Za-z][ ]?[0-9][A-Za-z][0-9]$";
        if (code != null && !(code.isEmpty())) {
            if(code.matches(regex)) {
                codeErr.setVisibility(View.INVISIBLE);
                resto.setAddPostalCode(code);
                Log.i(TAG, "matches regex");
            }
        }
        else{
            codeErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "code " + code);
    }
    private void handleGenreField(View v) {
        EditText editGenre = (EditText) findViewById(R.id.editGenre);
        genre = editGenre.getText().toString();
        TextView genreErr= (TextView) findViewById(R.id.textGenreError);
        if (genre != null && !(genre.isEmpty())) {
            genreErr.setVisibility(View.INVISIBLE);
                resto.setGenre(genre);
        }
        else{
            genreErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "genre " + genre);
    }
    private void handlePriceField(View v) {
        EditText editPrice = (EditText) findViewById(R.id.editPrice);
        String priceRange = editPrice.getText().toString();
        TextView priceErr= (TextView) findViewById(R.id.textPriceError);
        String regex = "^/${1,4}$";
        if (priceRange != null && !(priceRange.isEmpty())) {
            if (priceRange.matches(regex)) {
                priceErr.setVisibility(View.INVISIBLE);
                price = Integer.parseInt(priceRange);
                resto.setPriceRange(price);
                Log.i(TAG, "matches regex");
            }
        } else{
            priceErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "price range " + price);
    }

    private void handleNotesField(View v)
    {
        EditText editNotes = (EditText) findViewById(R.id.editNotes);
        notes = editNotes.getText().toString();
        if (notes != null && !(notes.isEmpty())) {
            resto.setNotes(notes);
        }
        Log.i(TAG, "notes " + notes);
    }

    private void handleLongLatFields(View v) {
        EditText editLongitude = (EditText) findViewById(R.id.editLongitude);
        String longitude = editLongitude.getText().toString();
        EditText editLatitude = (EditText) findViewById(R.id.editLatitude);
        String latitude = editLatitude.getText().toString();
        if (longitude != null && !(longitude.isEmpty()) && latitude != null && !(latitude.isEmpty())) {
            longit = Double.parseDouble(longitude);
            latid = Double.parseDouble(latitude);
            resto.setLongitude(longit);
            resto.setLatitude(latid);
        }
        Log.i(TAG, "longitude & latitude " + longit +" & "+latid);


    }
    private void handleRatingBar(View v) {
        RatingBar editRating = (RatingBar) findViewById(R.id.ratingBar);
        rating = editRating.getRating();
            resto.setStarRating(rating);

        Log.i(TAG, "rating bar val" + rating);
    }
    }
