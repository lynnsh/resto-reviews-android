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
 * This activity shows the details of a restaurant.
 *
 * @author Rafia Anwar
 * @version 02-12-2016
 */
public class ShowRestoActivity extends MenuActivity {
    private static final String TAG = "Show resto Act";
    private Restaurant resto;
    private String name;
    private String address;
    private String genre;
    private int price;
    private String phone;
    private String notes;
    private Double longit;
    private Double latid;
    private double rating;
    private int id;
    private DatabaseHelper dbconn;
    private EditText txtname, txtaddress, txtphone, txtgenre, txtprice, txtnotes, txtlongitude, txtlatitude;
    private RatingBar ratingBar;

    /**
     * Overridden Lifecycle method.
     * Receives a resto object, and after checking its source,
     * displays the appropriaate buttons
     *
     * @param savedInstanceState stores the value when screen rotated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resto);
        dbconn = DatabaseHelper.getDatabaseConnector(this);
        getFields();
        Bundle bundle = getIntent().getExtras();
        resto = (Restaurant) bundle.getSerializable("resto");
        if (resto == null) {
            Log.e(TAG, "resto is null");
        }
        setFields(resto);
        int source = resto.getSource();
        //local db resto
        if (source == 0) {
            id = resto.getDbId();
            Log.i(TAG, "the id of resto " + id);
            resto = dbconn.getResto(id);
            setFields(resto);
            showEditButton();
            showDeleteButton(id);
        }
        //zomato resto
        if (source == 1) {
            showAddButton();
            setFields(resto);
        }
        //heroku resto
        if (source == 2) {
            showAddButton();
            setFields(resto);
        }
        //to allow to add/view reviews for restos from heroku
        int herokuId = resto.getHerokuId();
        if (herokuId != 0 && herokuId != -1) {
            showAddReviewButton();
            showReviewButton();
        }
        handleFields();
    }

    /**
     * Allows the user to modify an existing restaurant and
     * sends the resto obj to addRestoActivity to be updated in database
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
                intent.putExtra("resto", resto);
                startActivity(intent);
            }
        });
    }

    /**
     * Displays the delete button and when given an id, deleted the resto
     * with that specific id
     *
     * @param restoId
     */
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
                        // TODO :  SWITCH TO START ACTIVITY FOR RESULT
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
                dialog.show();
            }
        });
    }

    /**
     * Displays the add button, when clicked it sends the resto object
     * to add activity which adds it to local database
     */
    private void showAddButton() {
        Button addButton = (Button) findViewById(R.id.buttonAdd);
        addButton.setVisibility(View.VISIBLE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRestoActivity.class);
                //sending resto obj
                intent.putExtra("databaseResto", false);
                intent.putExtra("resto", resto);
                startActivity(intent);
            }
        });
    }

    /**
     * Displays the button to add a review for heroku restaurant,
     * when clicked sends the resto object to add activity and initiates
     * addReviewActivity
     */
    private void showAddReviewButton() {
        Button addReviewButton = (Button) findViewById(R.id.buttonAddReview);
        addReviewButton.setVisibility(View.VISIBLE);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddReviewActivity.class);
                //sending resto obj
                Bundle bundle = new Bundle();
                intent.putExtra("resto", resto);
                startActivity(intent);
            }
        });
    }

    /**
     * Displays the button to show reviews for a heroku restaurant,
     * when clicked sends the resto obj to showReviewActivity
     */
    private void showReviewButton() {
        Button showReviewButton = (Button) findViewById(R.id.buttonShowReview);
        showReviewButton.setVisibility(View.VISIBLE);
        showReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowReviewsActivity.class);
                //sending resto obj
                intent.putExtra("resto", resto);
                startActivity(intent);
            }
        });
    }

    /**
     * This method gets the reference to all the TextView fields of form
     * and save them in private fields.
     */
    private void getFields() {
        txtname = (EditText) findViewById(R.id.editRestoName);
        txtaddress = (EditText) findViewById(R.id.editAddress);
        txtphone = (EditText) findViewById(R.id.editTextPhone);
        txtgenre = (EditText) findViewById(R.id.editGenre);
        txtprice = (EditText) findViewById(R.id.editPrice);
        txtnotes = (EditText) findViewById(R.id.editNotes);
        txtlongitude = (EditText) findViewById(R.id.editLongitude);
        txtlatitude = (EditText) findViewById(R.id.editLatitude);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    /**
     * This method fills the textview fields with resto details
     */
    private void handleFields() {
        txtname.setText(name);
        //when clicked, it launches a google search with resto name in search bar
        txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String googleUrl = "http://www.google.com/#q=" + name;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl));
                startActivity(intent);
            }
        });
        txtaddress.setText(address);
        txtphone.setText(phone);
        txtgenre.setText(genre);
        txtprice.setText(price + "");
        txtnotes.setText(notes);
        txtlongitude.setText(longit + "");
        txtlatitude.setText(latid + "");
        ratingBar.setRating((float) rating);
    }

    /**
     * This method inserts the resto attribute values to private fields
     *
     * @param resto the resto obj to be displayed
     */
    private void setFields(Restaurant resto) {
        if (resto.getName() != null)
            name = resto.getName();
        if (resto.getAddress() != null) {
            this.address = resto.getAddress();
        }
        if (resto.getGenre() != null)
            genre = resto.getGenre();
        price = resto.getPriceRange();
        if (resto.getNotes() != null)
            notes = resto.getNotes();
        longit = resto.getLongitude();
        latid = resto.getLatitude();
        rating = resto.getStarRating();
        phone = resto.getPhone();
    }
}
