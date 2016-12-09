package com.radiantridge.restoradiantridge.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.activities.MenuActivity;

import java.util.Calendar;

/**
 * This activity allows a user to change their login settings for the
 * application.
 *
 * @author Erika Bourque
 * @version 01/12/2016
 */
public class SettingsActivity extends MenuActivity {
    private static final String TAG = "Settings";
    private EditText fnameET;
    private EditText lnameET;
    private EditText emailET;
    private EditText pwET;

    /**
     * Overriden lifecycle method.  Instantiates class variables and
     * sets the current values of the settings to the EditTexts.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Instantiate EditTexts
        fnameET = (EditText) findViewById(R.id.settings_fname_et);
        lnameET = (EditText) findViewById(R.id.settings_lname_et);
        emailET = (EditText) findViewById(R.id.settings_email_et);
        pwET = (EditText) findViewById(R.id.settings_pw_et);

        setEditTextValues();
    }

    /**
     * This method sets the values from the Settings SharedPrefs to the EditTexts.
     */
    private void setEditTextValues() {
        Log.i(TAG, "Displaying current values");
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);

        fnameET.setText(prefs.getString("fname", null));
        lnameET.setText(prefs.getString("lname", null));
        emailET.setText(prefs.getString("email", null));
        pwET.setText(prefs.getString("password", null));
    }

    /**
     * Event handler for the save button.  Validates the data before saving and
     * closing the activity.
     *
     * @param v The view that fired the event
     */
    public void save(View v) {
        boolean dataIsValid = validateData();

        if (dataIsValid) {
            saveValidatedData();
            Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * This method validates that the data in the EditText is not empty and is in
     * the correct format.
     *
     * @return True if the data is valid
     */
    private boolean validateData() {
        boolean isValid = false;

        String fname = fnameET.getText().toString();
        String lname = lnameET.getText().toString();
        String email = emailET.getText().toString();
        String pw = pwET.getText().toString();

        // Checking if all fields are valid
        if ((!fname.isEmpty()) && (!lname.isEmpty())
                && (!email.isEmpty()) && (!pw.isEmpty())) {
            // Checking if the email is in a valid format
            if (email.matches(".+@.+")) {
                isValid = true;
            } else {
                // Display error message concerning invalid email
                Toast.makeText(this, getResources().getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Display error message concerning missing fields
            Toast.makeText(this, getResources().getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    /**
     * This method saves the data in the EditTexts to the Settings
     * SharedPrefs.
     */
    private void saveValidatedData() {
        Log.i(TAG, "Saving data.");

        // Getting the shared prefs editor
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Inserting the values
        editor.putString("fname", fnameET.getText().toString());
        editor.putString("lname", lnameET.getText().toString());
        editor.putString("email", emailET.getText().toString());
        editor.putString("password", pwET.getText().toString());
        editor.putString("timestamp", Calendar.getInstance().getTime().toString());

        // Saving the changes
        editor.commit();
    }

    /**
     * Overriden lifecycle method.  Asks the user to confirm before
     * exiting the activity.
     */
    @Override
    public void onBackPressed() {
        Log.i(TAG, "Back pressed.");

        // Building the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_exit_title);
        builder.setMessage(R.string.dialog_exit_text);

        // Setting buttons on the alert dialog
        builder.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // End activity
                finish();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Cancel the dialog
                dialog.cancel();
            }
        });

        // Displaying the dialog
        builder.show();
    }
}
