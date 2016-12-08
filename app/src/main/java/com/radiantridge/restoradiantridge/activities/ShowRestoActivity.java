package com.radiantridge.restoradiantridge.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.radiantridge.restoradiantridge.helpers.DatabaseHelper;
import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.objects.Restaurant;

/**
 * Created by Rafia on 2016-12-02.
 */

public class ShowRestoActivity  extends AppCompatActivity {
    private static final String TAG = "Show resto Act";
    private Restaurant resto;
    private String name;
    private int num;
    private String addLineOne;
    private String addLineTwo;
    private String code;
    private String genre;
    private int price;
    private String phone;
    private String notes;
    private Double longit;
    private Double latid;
    private double rating;
    private int id;  // get it
    private DatabaseHelper dbconn;
    private EditText txtname,txtaddone,txtaddtwo,txtphone,txtgenre,txtprice,txtnotes,txtlongitude,txtlatitude;
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
        dbconn = DatabaseHelper.getDatabaseConnector(this);
        getFields();

        Bundle bundle = getIntent().getExtras();

       // boolean isZomato = bundle.getBoolean("isZomato");

        resto = (Restaurant) bundle.getSerializable("resto");
        if (resto == null)
        {
            Log.e(TAG, "resto is null");
        }
        setFields(resto);

        int source = resto.getSource();
        //showAddButton();

        // TODO: change this to receive objs only
        // TODO: check to show appropriate buttons (zomato, local db, heroku)
        //local db
        if(source==0)
        {
            Log.i(TAG,"is not a zomato resto");
             id = bundle.getInt("databaseId");
            Log.i(TAG, "the id os resto " + id);
            resto = dbconn.getResto(id);
            //Log.i(TAG , "postal code before being sent " + resto.getAddPostalCode());
            setFields(resto);
            showEditButton();
            showDeleteButton(id);


        }
        //zomato
        if(source==1)
        {
            Log.i(TAG,"is  a zomato resto");
            // get each field
            showAddButton();
            //make resto obj
            //createRestoObj(bundle);
            setFields(resto);

        }
        //heroku
        if(source==2)
        {
            showAddButton();
            showAddReviewButton();
            showReviewButton();
            setFields(resto);


        }
        handleFields();
    }

    /**
     * Allows the user to modify an existing restaurant
     * sends the resto obj to addRestoActivity to be added to
     */
    private void showEditButton() {
        Button editButton = (Button) findViewById(R.id.buttonEdit);
        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRestoActivity.class);
                //sending resto obj
                Bundle bundle = new Bundle();
                intent.putExtra("databaseResto", true);
                intent.putExtra("resto",resto);
                startActivity(intent);
            }
        });
    }
    private void showDeleteButton(final int restoId) {
        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowRestoActivity.this);
                builder.setTitle(R.string.dialog_title);
                builder.setMessage(R.string.delete_msg);
                builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        // delete the resto
                        dbconn.deleteResto(restoId);
                        Toast.makeText(getApplicationContext(), R.string.delete_text, Toast.LENGTH_SHORT).show();
                        // go back to list of restos
                        Intent intent = new Intent(getApplicationContext(), FavoritesActivty.class);
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
                dialog.show();
            }
        });
    }
    private void showAddButton() {
        Button addButton = (Button) findViewById(R.id.buttonAdd);

        addButton.setVisibility(View.VISIBLE);


        //makeAllFieldsEditable();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRestoActivity.class);
                //sending resto obj
                Bundle bundle = new Bundle();
              //  bundle.putSerializable("resto", resto);
                intent.putExtra("databaseResto", false);
                intent.putExtra("resto",resto);
                startActivity(intent);
            }
        });
    }

    private void showAddReviewButton() {
        Button addReviewButton = (Button) findViewById(R.id.buttonAddReview);

        addReviewButton.setVisibility(View.VISIBLE);


        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddReviewActivity.class);
                //sending resto obj
                Bundle bundle = new Bundle();
                //  bundle.putSerializable("resto", resto);
              //  intent.putExtra("databaseResto", false);
                intent.putExtra("resto",resto);
                startActivity(intent);
            }
        });
    }
    private void showReviewButton() {
        Button showReviewButton = (Button) findViewById(R.id.buttonShowReview);

        showReviewButton.setVisibility(View.VISIBLE);

        showReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowReviewActivity.class);
                //sending resto obj
                Bundle bundle = new Bundle();
                //  bundle.putSerializable("resto", resto);
                //  intent.putExtra("databaseResto", false);
                intent.putExtra("resto",resto);
                startActivity(intent);
            }
        });
    }

    private void getFields() {
        txtname = (EditText) findViewById(R.id.editRestoName);
        txtaddone = (EditText) findViewById(R.id.editAddLineOne);
        txtaddtwo = (EditText) findViewById(R.id.editAddLineTwo);
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
        txtname.setOnClickListener(new View.OnClickListener(){
           @Override
             public void onClick(View v) {
                                           String googleUrl = "http://www.google.com/#q="+name;
             Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl));
               startActivity(intent);

           }});
        //txtnum.setText(num+"");
        txtaddone.setText(addLineOne);
        txtaddtwo.setText(addLineTwo);
        //txtcode.setText(code);
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
        if(resto.getName()!=null)
        name = resto.getName();
        //num=resto.getAddNum();
        if(resto.getAddress()!=null) {
            String[] address = resto.getAddress().split(",", 2);

            // TODO: make address one big field
            addLineOne = address[0];
            if (address.length == 2) {
                addLineTwo = address[1];
            }
        }
        //code=resto.getAddPostalCode();
        if(resto.getGenre()!=null)
        genre=resto.getGenre();
        price=resto.getPriceRange();
        if(resto.getNotes()!=null)
        notes=resto.getNotes();
        longit=resto.getLongitude();
        latid=resto.getLatitude();
        rating=resto.getStarRating();
    }

//    private void createRestoObj(Bundle bundle) {
//        resto.setDbId(-1);
//        if(bundle.get("name") != null)
//        {
//            name = (String) bundle.get("name");
//            resto.setName(name);
//        }
//        if(bundle.get("addNum") != null)
//        {
//            num = (int) bundle.get("addNum");
//            resto.setAddNum(num);
//        }
//        if(bundle.get("addStreet") != null)
//        {
//            addLineOne = (String) bundle.get("addStreet");
//            resto.setAddStreet(addLineOne);
//        }
//        if(bundle.get("addCity") != null)
//        {
//            addLineTwo = (String) bundle.get("addCity");
//            resto.setAddCity(addLineTwo);
//        }
//        if(bundle.get("addPostalCode") != null)
//        {
//            code = (String) bundle.get("addPostalCode");
//            resto.setAddPostalCode(code);
//        }
//        if(bundle.get("genre") != null)
//        {
//            genre = (String) bundle.get("genre");
//            resto.setGenre(genre);
//        }
//        if(bundle.get("priceRange") != null)
//        {
//            price = (int) bundle.get("priceRange");
//            resto.setPriceRange(price);
//        }
//        if( bundle.get("longitude") != null)
//        {
//            longit = (double) bundle.get("longitude");
//            resto.setLongitude(longit);
//        }
//        if(bundle.get("latitude") != null)
//        {
//            latid = (double) bundle.get("latitude");
//            resto.setLatitude(latid);
//        }
//        if(bundle.get("starRating") != null)
//        {
//            rating = (double) bundle.get("starRating");
//            resto.setStarRating(rating);
//        }
//    }

}
