package com.radiantridge.restoradiantridge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private float rating;
    private int id;  // get it
    private  DatabaseConnector dbconn;
    /**
     * Overridden Lifecycle method.
     * @param savedInstanceState stores the value when screen rotated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resto);
        resto = new Restaurant();

        dbconn = DatabaseConnector.getDatabaseConnector(this);

        Restaurant resto = dbconn.getResto(id);
        getFields();
    }
    private void getFields(){
        TextView txtname = (TextView) findViewById(R.id.editRestoName);
        TextView txtnum = (TextView) findViewById(R.id.editNum);
        TextView txtstreet = (TextView) findViewById(R.id.editStreet);
        TextView txtcity = (TextView) findViewById(R.id.editCity);
        TextView txtcode = (TextView) findViewById(R.id.editCode);
        TextView txtphone = (TextView) findViewById(R.id.editTextPhone);
        TextView txtgenre = (TextView) findViewById(R.id.editGenre);
        TextView txtprice = (TextView) findViewById(R.id.editPrice);
        TextView txtnotes = (TextView) findViewById(R.id.editNotes);
        TextView txtlongitude = (TextView) findViewById(R.id.editLongitude);
        TextView txtlatitude = (TextView) findViewById(R.id.editLatitude);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        setFields(resto);
        txtname.setText(name);
        //how to check for nulls
        txtnum.setText(num);
        txtstreet.setText(street);
        txtcity.setText(city);
        txtcode.setText(code);
        txtphone.setText(phone);
        txtgenre.setText(genre);
        txtprice.setText(price);
        txtnotes.setText(notes);
        String lon = longit+"";
        txtlongitude.setText(lon);
        String lat = latid+"";
        txtlatitude.setText(lat);
        ratingBar.setRating(rating);

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
