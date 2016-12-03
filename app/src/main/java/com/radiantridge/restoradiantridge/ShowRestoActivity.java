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

        Bundle bundle = getIntent().getExtras();

        boolean isZomato = bundle.getBoolean("isZomato");
        if(!isZomato)
        {
            Log.i(TAG,"is not a zomato resto");
             id = bundle.getInt("databaseId");
            Log.i(TAG, "the id os resto " + id);
        }
        if(isZomato)
        {
            Log.i(TAG,"is  a zomato resto");

            showAddButton();
        }
        dbconn = DatabaseConnector.getDatabaseConnector(this);

        resto = new Restaurant();

        //

        resto = dbconn.getResto(id);
        Log.i(TAG, "IS RESTO PRESENT" + resto);
        getFields();
    }

    private void showAddButton() {
        Button addButton = (Button) findViewById(R.id.buttonAdd);

        addButton.setVisibility(View.VISIBLE);

        makeAllFieldsEditable();

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

    private void makeAllFieldsEditable() {
        txtname.setFocusableInTouchMode(true);
        txtname.setFocusable(true);
        txtnum.setFocusableInTouchMode(true);
        txtnum.setFocusable(true);
        txtstreet.setFocusableInTouchMode(true);
        txtstreet.setFocusable(true);
        txtcity.setFocusableInTouchMode(true);
        txtcity.setFocusable(true);
        txtcode.setFocusableInTouchMode(true);
        txtcode.setFocusable(true);
        txtphone.setFocusableInTouchMode(true);
        txtphone.setFocusable(true);
        txtgenre.setFocusableInTouchMode(true);
        txtgenre.setFocusable(true);
        txtprice.setFocusableInTouchMode(true);
        txtprice.setFocusable(true);
        txtnotes.setFocusableInTouchMode(true);
        txtnotes.setFocusable(true);
        txtlongitude.setFocusableInTouchMode(true);
        txtlongitude.setFocusable(true);
        txtlatitude.setFocusableInTouchMode(true);
        txtlatitude.setFocusable(true);
        ratingBar.setIsIndicator(true);

    }

    private void getFields(){
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
        setFields(resto);
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
}
