package com.radiantridge.restoradiantridge.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.radiantridge.restoradiantridge.R;

import java.util.Calendar;

/**
 * Initial activity for the restaurant application.  Logs in the
 * user if log details are not present in the shared preferences,
 * and launches the restaurant's main activity.
 *
 * @author Erika Bourque
 * @version 28/11/2016
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    private static final int MAINRESTO = 1;

    /**
     * Overriden lifecycle method.  Creates the view, and checks if login details
     * are present in the shared preferences.  If they are present, it will launch
     * the restaurant's main activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);

        // Making sure we have all values
        if ((prefs.getString("fname", null) != null)
                && (prefs.getString("lname", null) != null)
                && (prefs.getString("email", null) != null)
                && (prefs.getString("password", null) != null))
        {
            Log.i(TAG, "SharedPrefs found, launching MainResto.");

            // Starting the main resto activity
            startActivityForResult(new Intent(this, MainRestoActivity.class), MAINRESTO);
        }
    }

    /**
     * Event handler for the login button.  Validates the data and displays
     * appropriate error messages if the data is invalid.  Saves the data
     * and launches the main resto activity.
     *
     * @param v     The view that fired the event
     */
    public void login(View v)
    {
        // Retrieving Strings in two steps
        EditText fnameET = (EditText) findViewById(R.id.login_fname_et);
        EditText lnameET = (EditText) findViewById(R.id.login_lname_et);
        EditText emailET = (EditText) findViewById(R.id.login_email_et);
        EditText pwET = (EditText) findViewById(R.id.login_pw_et);
        String fname = fnameET.getText().toString();
        String lname = lnameET.getText().toString();
        String email = emailET.getText().toString();
        String pw = pwET.getText().toString();

        // Checking if all fields are valid
        if ((!fname.isEmpty()) && (!lname.isEmpty())
                && (!email.isEmpty()) && (!pw.isEmpty()))
        {
            // Checking if the email is in a valid format
            if (email.matches(".+@.+"))
            {
                // Saving the data
                saveData(fname, lname, email, pw);

                Log.i(TAG, "Launching MainResto.");
                // Starting the main resto activity
                startActivity(new Intent(this, MainRestoActivity.class));
            }
            else
            {
                // Display error message concerning invalid email
                Toast.makeText(this, getResources().getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // Display error message concerning missing fields
            Toast.makeText(this, getResources().getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method saves the given data in the Shared Preferences.
     *
     * @param fname     The first name
     * @param lname     The last name
     * @param email     The email
     * @param pw        The password
     */
    private void saveData(String fname, String lname, String email, String pw)
    {
        Log.i(TAG, "Saving data.");

        // Getting the shared prefs editor
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Inserting the values
        editor.putString("fname", fname);
        editor.putString("lname", lname);
        editor.putString("email", email);
        editor.putString("password", pw);
        editor.putString("timestamp", Calendar.getInstance().getTime().toString());

        // Saving the changes
        editor.commit();
    }

    /**
     * Overriden lifecycle method.  Finishes the main activity.
     *
     * @param requestCode   The request code
     * @param resultCode    The result code
     * @param data          The result's data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == MAINRESTO)
        {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
