package com.radiantridge.restoradiantridge;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
    private Double rating;
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
        boolean isNameValid,isCodeValid,isPriceValid,isGenreValid;
        isNameValid =  handleNameField(v);
        handleNumField(v);
        handleStreetField(v);
        handleCityField(v);
        isCodeValid= handleCodeField(v);
        isGenreValid = handleGenreField(v);
        isPriceValid = handlePriceField(v);
        handleNotesField(v);
        handleLongLatFields(v);
        handleRatingBar(v);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        resto.setCreatedTime(timestamp);
        resto.setModifiedTime(timestamp);
        Log.i(TAG,"time " +timestamp);
        dbconn = DatabaseConnector.getDatabaseConnector(this);

        Log.i(TAG,""+isNameValid +" "+ isCodeValid +" " + isPriceValid +" "+ isGenreValid);
        if(isNameValid && isCodeValid && isPriceValid && isGenreValid) {
            Log.i(TAG , "Saving resto..");
            int id =(int) dbconn.addResto(resto);
            resto.setDbId(id);
            Intent intent = new Intent(this, MainRestoActivity.class);
            startActivity(intent);
        }
    }
    private boolean handleNameField(View v){
        boolean isValid=false;
        EditText editName = (EditText) findViewById(R.id.editRestoName);
        name = editName.getText().toString();
        TextView nameErr= (TextView) findViewById(R.id.textNameError);
        if(name != null&& !(name.isEmpty())) {
            resto.setName(name);
            isValid=true;
            nameErr.setVisibility(View.INVISIBLE);
        }
        else
        {
            isValid=false;
            nameErr.setVisibility(View.VISIBLE);
        }
        return isValid;
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

    private boolean handleCodeField(View v) {
        boolean isValid=false;
        EditText editCode = (EditText) findViewById(R.id.editCode);
        code = editCode.getText().toString();
        TextView codeErr= (TextView) findViewById(R.id.textCodeError);
        String regex = "^[A-Za-z][0-9][A-Za-z][ ]?[0-9][A-Za-z][0-9]$";
        if (code != null && !(code.isEmpty())) {
            if(code.matches(regex)) {
                isValid=true;
                codeErr.setVisibility(View.INVISIBLE);
                resto.setAddPostalCode(code);
                Log.i(TAG, "matches regex");
            }
        }
        else{
            isValid=false;
            codeErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "code " + code);
        return isValid;
    }
    private boolean handleGenreField(View v) {
        boolean isValid=false;
        EditText editGenre = (EditText) findViewById(R.id.editGenre);
        genre = editGenre.getText().toString();
        TextView genreErr= (TextView) findViewById(R.id.textGenreError);
        if (genre != null && !(genre.isEmpty())) {
            isValid=true;
            genreErr.setVisibility(View.INVISIBLE);
                resto.setGenre(genre);
        }
        else{
            isValid=false;
            genreErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "genre " + genre);
        return isValid;
    }
    private boolean handlePriceField(View v) {
        boolean isValid=false;
        EditText editPrice = (EditText) findViewById(R.id.editPrice);
        String priceRange = editPrice.getText().toString();
        TextView priceErr= (TextView) findViewById(R.id.textPriceError);
        String regex = "^[1-4]{1}$";
        if (priceRange != null && !(priceRange.isEmpty())) {
            if (priceRange.matches(regex)) {
                isValid=true;
                priceErr.setVisibility(View.INVISIBLE);
                price = Integer.parseInt(priceRange);
                resto.setPriceRange(price);
                Log.i(TAG, "matches regex");
            }
        } else{
            isValid=false;
            priceErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "price range " + price);
        Log.i(TAG, "is price valid" + isValid);
        return isValid;
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
        rating = (double)editRating.getRating();
            resto.setStarRating(rating);

        Log.i(TAG, "rating bar val" + rating);
    }
    @Override
    public void onBackPressed(){
        //code taken from Android Developers Site
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_msg);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Intent intent = new Intent(getApplicationContext(), MainRestoActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();

    }
    }
