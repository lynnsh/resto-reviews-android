package com.radiantridge.restoradiantridge.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.radiantridge.restoradiantridge.helpers.DatabaseHelper;
import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.objects.Restaurant;

import java.sql.Timestamp;

/**
 * This activity adds a restaurant to the local database.
 * The restaurant can be a zomato or heroku resto.
 * It can also be created by providing input.
 *
 * @author Rafia Anwar
 * @version 02/12/2016
 */
public class AddRestoActivity extends MenuActivity {
    private static final String TAG = "Add resto Act";
    private Restaurant resto;
    private String name;
    private String genre;
    private int price;
    private String notes;
    private Double longit;
    private Double latid;
    private Double rating;
    private DatabaseHelper dbconn;
    private EditText editName, editAddress, txtphone, editGenre, editPrice, editNotes, editLongitude, editLatitude;
    private RatingBar editRating;
    private boolean exisitngRecord;
    private boolean save; //keeps check of valid inputs

    /**
     * Overridden Lifecycle method.
     * It receives a resto object and checks if it already exists in database
     *
     * @param savedInstanceState stores the value when screen rotated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resto);
        dbconn = DatabaseHelper.getDatabaseConnector(this);
        resto = new Restaurant();
        getFields();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            exisitngRecord = bundle.getBoolean("databaseResto");
            Log.i(TAG, "Received a resto object from ShowActitivty");
            resto = (Restaurant) bundle.getSerializable("resto");
            if (resto != null) {
                Log.i(TAG, "resto obj is not null");
                //sets the field to the input from zomato resto
                setEditFields();
            } else {
                Log.i(TAG, "resto obj is null");
            }
        }
    }

    /**
     * This method gets all the data from input fields, validates it,
     * and insert it into the database.
     *
     * @param v View which fired the event
     */
    public void saveResto(View v) {
        boolean isNameValid, isAddressValid, isPriceValid, isGenreValid;
        isNameValid = handleNameField();
        isAddressValid = handleAddress();
        isGenreValid = handleGenreField();
        isPriceValid = handlePriceField();
        handleNotesField();
        handleLongLatFields();
        handleRatingBar();
        String phone = txtphone.getText().toString();
        if (phone != null && !(phone.isEmpty())) {
            resto.setPhone(phone);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //keep the original creation time for the resto already present in database
        if (exisitngRecord)
            resto.setCreatedTime(resto.getCreatedTime());
        else
            resto.setCreatedTime(timestamp);
        resto.setModifiedTime(timestamp);
        Log.i(TAG, "time " + timestamp);
        if (isNameValid && isAddressValid && isPriceValid && isGenreValid) {
            if (exisitngRecord) {
                Log.i(TAG, "Updating resto..");
                dbconn.updateResto(resto);
                Toast.makeText(getApplicationContext(), R.string.edit_text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FavoritesActivty.class);
                startActivity(intent);
            } else {
                Log.i(TAG, "Saving resto..");
                int id = (int) dbconn.addResto(resto);
                //check that there was no errors during the addition
                if (id != -1) {
                    resto.setDbId(id);
                    Toast.makeText(getApplicationContext(), R.string.save_text, Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainRestoActivity.class);
                startActivity(intent);

            }
            //change it to last opened page
            Intent intent = new Intent(this, MainRestoActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method validates the name from user input.
     * If name field is null or empty , an error is displayed
     * It sets the value to resto object
     *
     * @return true if name field is not null or empty
     */
    private boolean handleNameField() {
        boolean isValid = false;
        name = editName.getText().toString();
        TextView nameErr = (TextView) findViewById(R.id.textNameError);
        if (name != null && !(name.isEmpty())) {
            resto.setName(name);
            isValid = true;
            nameErr.setVisibility(View.INVISIBLE);
        } else {
            isValid = false;
            nameErr.setVisibility(View.VISIBLE);
        }
        return isValid;
    }

    /**
     * This method validates the address from user input.
     * If address field is null or empty , an error is displayed
     * It sets the value to resto object
     *
     * @return true if address field is not null or empty
     */
    private boolean handleAddress() {
        boolean isValid;
        String address = editAddress.getText().toString();
        TextView addErr = (TextView) findViewById(R.id.textAddressError);

        if (address != null && !address.isEmpty()) {
            isValid = true;
            resto.setAddress(address);
            addErr.setVisibility(View.INVISIBLE);
        } else {
            // make error bar visible
            addErr.setVisibility(View.VISIBLE);
            isValid = false;
        }
        return isValid;
    }

    /**
     * This method validates the genre from user input.
     * If genre field is null or empty , an error is displayed
     * It sets the value to resto object
     *
     * @return true if genre field is not null or empty
     */
    private boolean handleGenreField() {
        boolean isValid = false;
        genre = editGenre.getText().toString();
        TextView genreErr = (TextView) findViewById(R.id.textGenreError);
        if (genre != null && !(genre.isEmpty())) {
            isValid = true;
            genreErr.setVisibility(View.INVISIBLE);
            resto.setGenre(genre);
        } else {
            isValid = false;
            genreErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "genre " + genre);
        return isValid;
    }

    /**
     * This method validates the price from user input.
     * If price field is null or empty , an error bar is displayed.
     * If price is not in range of 1-4, an error bar is displayed
     * and sets the value to resto object
     *
     * @return true if price field is not null,empty or out of range
     */
    private boolean handlePriceField() {
        boolean isValid = false;
        String priceRange = editPrice.getText().toString();
        TextView priceErr = (TextView) findViewById(R.id.textPriceError);
        String regex = "^[1-4]{1}$";
        if (priceRange != null && !(priceRange.isEmpty())) {
            if (priceRange.matches(regex)) {
                isValid = true;
                priceErr.setVisibility(View.INVISIBLE);
                price = Integer.parseInt(priceRange);
                resto.setPriceRange(price);
                Log.i(TAG, "matches regex");
            } else {
                isValid = false;
                priceErr.setVisibility(View.VISIBLE);
            }
        } else {
            isValid = false;
            priceErr.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "price range " + price);
        Log.i(TAG, "is price valid" + isValid);
        return isValid;
    }

    /**
     * This method validates that notes from user input
     * is not null or empty and sets the value to resto object
     */
    private void handleNotesField() {
        notes = editNotes.getText().toString();
        if (notes != null && !(notes.isEmpty())) {
            resto.setNotes(notes);
        }
        Log.i(TAG, "notes " + notes);
    }

    /**
     * This method validates that longitude and latitude is not null or empty
     * and sets the values to resto object
     */
    private void handleLongLatFields() {
        String longitude = editLongitude.getText().toString();
        String latitude = editLatitude.getText().toString();
        if (longitude != null && !(longitude.isEmpty()) && latitude != null && !(latitude.isEmpty())) {
            longit = Double.parseDouble(longitude);
            latid = Double.parseDouble(latitude);
            resto.setLongitude(longit);
            resto.setLatitude(latid);
        }
        Log.i(TAG, "longitude & latitude " + longit + " & " + latid);
    }

    /**
     * This method sets the rating from user input to resto object
     */
    private void handleRatingBar() {
        rating = (double) editRating.getRating();

        resto.setStarRating(rating);

        Log.i(TAG, "rating bar val" + rating);
    }

    /**
     * Overridden method
     * When back button is pressed, a dialog appears to confirm
     * if the user actually wants to leave the page.
     */
    @Override
    public void onBackPressed() {
        //code taken from Android Developers Site
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_msg);

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });
        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This method sets the field sto the input from zomato resto
     */
    private void setEditFields() {
        editName.setText(resto.getName());
        editAddress.setText(resto.getAddress());
        txtphone.setText(resto.getPhone());
        editGenre.setText(resto.getGenre());
        editPrice.setText(resto.getPriceRange() + "");
        editNotes.setText("");
        String lon = resto.getLongitude() + "";
        editLongitude.setText(lon);
        String lat = resto.getLatitude() + "";
        editLatitude.setText(lat);
        editRating.setRating((float) resto.getStarRating());

    }

    /**
     * This method gets the reference to all the EditText fields of form
     * and save them in private fields.
     */
    private void getFields() {
        editName = (EditText) findViewById(R.id.editRestoName);
        editAddress = (EditText) findViewById(R.id.editAddress);
        txtphone = (EditText) findViewById(R.id.editTextPhone);
        editGenre = (EditText) findViewById(R.id.editGenre);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editNotes = (EditText) findViewById(R.id.editNotes);
        editLongitude = (EditText) findViewById(R.id.editLongitude);
        editLatitude = (EditText) findViewById(R.id.editLatitude);
        editRating = (RatingBar) findViewById(R.id.ratingBar);
    }
}
