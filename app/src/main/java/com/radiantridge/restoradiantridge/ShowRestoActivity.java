package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Rafia on 2016-12-02.
 */

public class ShowRestoActivity  extends AppCompatActivity {
    private static final String TAG = "Show resto Act";
    private Restaurant resto;
    private String name;
    private int num;
    private String street;
    private String city;
    private String code;
    private String genre;
    private int price;
    private String phone;
    private String notes;
    private Double longit;
    private Double latid;
    private double rating;
    private int id;  // get it
    private  DatabaseConnector dbconn;
    private EditText txtname,txtnum, txtstreet,txtcity,txtcode,txtphone,txtgenre,txtprice,txtnotes,txtlongitude,txtlatitude;
    private RatingBar ratingBar;

    /**
     * Overridden Lifecycle method.
     * @param savedInstanceState stores the value when screen rotated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resto);
        resto = new Restaurant();

        Bundle bundle = getIntent().getExtras();

        boolean isZomato = bundle.getBoolean("isZomato");
        if(!isZomato)
        {
            Log.i(TAG,"is not a zomato resto");
             id = bundle.getInt("databaseId");
            Log.i(TAG, "the id os resto " + id);
            resto = dbconn.getResto(id);
            setFields(resto);

        }
        if(isZomato)
        {
            Log.i(TAG,"is  a zomato resto");
            setZomatoFields(bundle);
            // get each field
            showAddButton();
            //make resto obj
        }
        dbconn = DatabaseConnector.getDatabaseConnector(this);
        handleFields();
    }

    private void showAddButton() {
        Button addButton = (Button) findViewById(R.id.buttonAdd);

        addButton.setVisibility(View.VISIBLE);
        getFields();

        //makeAllFieldsEditable();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRestoActivity.class);
                //sending resto obj
                Bundle bundle = new Bundle();
              //  bundle.putSerializable("resto", resto);

                intent.putExtra("resto",resto);
                startActivity(intent);
            }
        });
    }


    private void getFields() {
        txtname = (EditText) findViewById(R.id.editRestoName);
        txtnum = (EditText) findViewById(R.id.editNum);
        txtstreet = (EditText) findViewById(R.id.editStreet);
        txtcity = (EditText) findViewById(R.id.editCity);
        txtcode = (EditText) findViewById(R.id.editCode);
        txtphone = (EditText) findViewById(R.id.editTextPhone);
        txtgenre = (EditText) findViewById(R.id.editGenre);
        txtprice = (EditText) findViewById(R.id.editPrice);
        txtnotes = (EditText) findViewById(R.id.editNotes);
        txtlongitude = (EditText) findViewById(R.id.editLongitude);
        txtlatitude = (EditText) findViewById(R.id.editLatitude);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }
    private void handleFields(){
        txtname.setText(name);
        //how to check for nulls
        txtnum.setText(num+"");
        txtstreet.setText(street);
        txtcity.setText(city);
        txtcode.setText(code);
        txtphone.setText(phone);
        txtgenre.setText(genre);
        txtprice.setText(price+"");
        txtnotes.setText(notes);
        String lon = longit+"";
        txtlongitude.setText(lon);
        String lat = latid+"";
        txtlatitude.setText(lat);
        ratingBar.setRating((float)rating);

    }
    private void setFields(Restaurant resto)
    {
        name = resto.getName();
        num=resto.getAddNum();
        street=resto.getAddStreet();
        city=resto.getAddCity();
        code=resto.getAddPostalCode();
        genre=resto.getGenre();
        price=resto.getPriceRange();
        notes=resto.getNotes();
        longit=resto.getLongitude();
        latid=resto.getLatitude();
        rating=resto.getStarRating();
    }
    private void setZomatoFields(Bundle bundle) {
        name = (String) bundle.get("name");

        num = (int) bundle.get("addNum");
        street = (String) bundle.get("addStreet");
        city = (String) bundle.get("addCity");
        code = (String) bundle.get("addPostalCode");
        genre = (String) bundle.get("genre");
        price = (int) bundle.get("priceRange");
        longit = (double) bundle.get("longitude");
        latid = (double) bundle.get("latitude");
        rating = (double) bundle.get("starRating");
    }
}
