package com.radiantridge.restoradiantridge.activities;

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
import android.widget.Toast;

import com.radiantridge.restoradiantridge.helpers.DatabaseHelper;
import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.objects.Restaurant;

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
   private DatabaseHelper dbconn;
    private EditText editName,editAddLineOne, editAddLineTwo,txtphone,editGenre,editPrice,editNotes,editLongitude,editLatitude;
    private RatingBar editRating;
    private boolean exisitngRecord;
    private boolean save; //keeps check of valid inputs
    /**
     * Overridden Lifecycle method.
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
        if(bundle != null)
        {
            exisitngRecord =  bundle.getBoolean("databaseResto");
            Log.i(TAG, "Received a resto object from ShowActitivty");
            resto =(Restaurant) bundle.getSerializable("resto");
            if(resto != null)
            {
                Log.i(TAG, "resto obj is not null");
                Log.i(TAG, "name " + resto.getName());
                //Log.i(TAG, "POSTAL code after being sent " + resto.getAddPostalCode());
                //sets the field to the input froom zomato resto
                setEditFields();
            }
            else {
                Log.i(TAG, "resto obj is null");
            }
        }

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
        boolean isNameValid,isAddressValid,isPriceValid,isGenreValid;
        isNameValid =  handleNameField(v);
//        handleNumField(v);
//        handleStreetField(v);
//        handleCityField(v);
//        isAddressValid= handleCodeField(v);
        isAddressValid = handleAddress(v);
        isGenreValid = handleGenreField(v);
        isPriceValid = handlePriceField(v);
        handleNotesField(v);
        handleLongLatFields(v);
        handleRatingBar(v);
        String phone = txtphone.getText().toString();
        if (phone != null && !(phone.isEmpty())) {
            resto.setPhone(phone);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //keep the original creation time for the resto already present in database
        if(exisitngRecord)
            resto.setCreatedTime(resto.getCreatedTime());
        else
        resto.setCreatedTime(timestamp);
        resto.setModifiedTime(timestamp);
        Log.i(TAG,"time " +timestamp);

        Log.i(TAG,""+isNameValid +" "+ isAddressValid +" " + isPriceValid +" "+ isGenreValid);
        if(isNameValid && isAddressValid && isPriceValid && isGenreValid) {
            if(exisitngRecord)
            {
                Log.i(TAG , "Updating resto..");
                dbconn.updateResto(resto);
                Toast.makeText(getApplicationContext(), R.string.edit_text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FavoritesActivty.class);
                startActivity(intent);

            }
            else {
                Log.i(TAG, "Saving resto..");
                int id = (int) dbconn.addResto(resto);
                resto.setDbId(id);
                Toast.makeText(getApplicationContext(), R.string.save_text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainRestoActivity.class);
                startActivity(intent);

            }
            //change it to last opened page
            Intent intent = new Intent(this, MainRestoActivity.class);
            startActivity(intent);
        }
    }

    private boolean handleNameField(View v){
        boolean isValid=false;
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

    private boolean handleAddress(View v)
    {
        boolean isValid;
        String addOne = editAddLineOne.getText().toString();
        String addTwo = editAddLineTwo.getText().toString();

        if (!addOne.isEmpty() && !addTwo.isEmpty())
        {
            isValid = true;
            resto.setAddress(addOne + "," + addTwo);
        }
        else
        {
            // make error bar visible
            isValid = false;
        }

        return isValid;
    }
//    private void handleNumField(View v) {
//        String number = editNumber.getText().toString();
//        if (number != null && !(number.isEmpty())) {
//            num = Integer.parseInt(number);
//            resto.setAddNum(num);
//        }
//        Log.i(TAG, "NUM " + num);
//    }
//    private void handleStreetField(View v) {
//        street = editStreet.getText().toString();
//
//        if (street != null && !(street.isEmpty())) {
//            resto.setAddStreet(street);
//        }
//        Log.i(TAG, "STREET " + street);
//    }
//    private void handleCityField(View v) {
//        city = editCity.getText().toString();
//
//        if (city != null && !(city.isEmpty())) {
//            resto.setAddCity(city);
//        }
//        Log.i(TAG, "city " + city);
//    }

//    private boolean handleCodeField(View v) {
//        boolean isValid=false;
//        code = editCode.getText().toString();
//        TextView codeErr= (TextView) findViewById(R.id.textCodeError);
//        String regex = "^[A-Za-z][0-9][A-Za-z][ ]?[0-9][A-Za-z][0-9]$";
//        if (code != null && !(code.isEmpty())) {
//            if (code.matches(regex)) {
//                isValid = true;
//                codeErr.setVisibility(View.INVISIBLE);
//                resto.setAddPostalCode(code);
//                Log.i(TAG, "matches regex");
//            } else {
//                isValid = false;
//                codeErr.setVisibility(View.VISIBLE);
//                Log.i(TAG, " regex failed");
//                //editCode.setText("");
//            }
//        }
//        Log.i(TAG, "code " + code);
//        return isValid;
//    }
    private boolean handleGenreField(View v) {
        boolean isValid=false;
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
        notes = editNotes.getText().toString();
        if (notes != null && !(notes.isEmpty())) {
            resto.setNotes(notes);
        }
        Log.i(TAG, "notes " + notes);
    }

    private void handleLongLatFields(View v) {
        String longitude = editLongitude.getText().toString();
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
        rating = (double) editRating.getRating();

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
//                Intent intent = new Intent(getApplicationContext(), MainRestoActivity.class);
//                startActivity(intent);
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
    private void setEditFields(){
        editName.setText(resto.getName());
        //how to check for nulls
        String[] address = resto.getAddress().split(",", 2);

        editAddLineOne.setText(address[0]);
        editAddLineTwo.setText(address[1]);
        txtphone.setText(resto.getPhone());
        editGenre.setText(resto.getGenre());
        editPrice.setText(resto.getPriceRange()+"");
        editNotes.setText("");
        String lon = resto.getLongitude()+"";
        editLongitude.setText(lon);
        String lat = resto.getLatitude()+"";
        editLatitude.setText(lat);
        editRating.setRating((float) resto.getStarRating());

    }
    private void getFields() {
        editName = (EditText) findViewById(R.id.editRestoName);
        editAddLineOne = (EditText) findViewById(R.id.editAddLineOne);
        editAddLineTwo = (EditText) findViewById(R.id.editAddLineTwo);
        txtphone = (EditText) findViewById(R.id.editTextPhone);
        editGenre = (EditText) findViewById(R.id.editGenre);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editNotes = (EditText) findViewById(R.id.editNotes);
        editLongitude = (EditText) findViewById(R.id.editLongitude);
        editLatitude = (EditText) findViewById(R.id.editLatitude);
        editRating = (RatingBar) findViewById(R.id.ratingBar);
    }
//    private void makeAllFieldsEditable() {
//        txtname.setFocusableInTouchMode(true);
//        txtname.setFocusable(true);
//        txtnum.setFocusableInTouchMode(true);
//        txtnum.setFocusable(true);
//        txtstreet.setFocusableInTouchMode(true);
//        txtstreet.setFocusable(true);
//        txtcity.setFocusableInTouchMode(true);
//        txtcity.setFocusable(true);
//        txtcode.setFocusableInTouchMode(true);
//        txtcode.setFocusable(true);
//        txtphone.setFocusableInTouchMode(true);
//        txtphone.setFocusable(true);
//        txtgenre.setFocusableInTouchMode(true);
//        txtgenre.setFocusable(true);
//        txtprice.setFocusableInTouchMode(true);
//        txtprice.setFocusable(true);
//        txtnotes.setFocusableInTouchMode(true);
//        txtnotes.setFocusable(true);
//        txtlongitude.setFocusableInTouchMode(true);
//        txtlongitude.setFocusable(true);
//        txtlatitude.setFocusableInTouchMode(true);
//        txtlatitude.setFocusable(true);
//        ratingBar.setIsIndicator(true);
//
//    }

}
