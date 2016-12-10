package com.radiantridge.restoradiantridge.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.radiantridge.restoradiantridge.helpers.DatabaseHelper;
import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.fragments.RestoListFragment;
import com.radiantridge.restoradiantridge.objects.Restaurant;

/**
 * This activity provides a search functionality for the
 * user's database.  They can search by name, genre or city.
 *
 * @author Erika Bourque
 * @version 01/12/2016
 */
public class SearchActivity extends MenuActivity {
    private final String TAG = "Search";
    private RadioGroup radios;
    private int selectedId;
    private EditText searchBox;
    private DatabaseHelper db;
    private RestoListFragment fragment;
    private String query;

    /**
     * Overriden lifecycle method.  Sets up
     * the variables and spinner.
     *
     * @param savedInstanceState    savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initializing variables
        db = DatabaseHelper.getDatabaseConnector(this);
        fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.search_list);
        radios = (RadioGroup) findViewById(R.id.radioGroupSearch);
        searchBox = (EditText) findViewById(R.id.search_ET);

        // Setting up spinner with its options
//        setUpSpinner();

        if ((savedInstanceState != null) && (savedInstanceState.getString("query") != null))
        {
            selectedId = savedInstanceState.getInt("selectedRadioId");
            query = savedInstanceState.getString("query");

            searchBox.setText(query);
            radios.check(selectedId);

            searchDb();
        }
    }

    /**
     * Event handler for search button.  Queries the database
     * with the appropriate search type.
     *
     * @param view      The view that fired the event
     */
    public void search(View view)
    {
        selectedId = radios.getCheckedRadioButtonId();
        query = searchBox.getText().toString();
        Log.i(TAG, "Query: " + query);

        fragment.setListAdapter(null);

        searchDb();
    }

//    /**
//     * This method gives the spinner its adapter from
//     * a saved array of strings for the search type.
//     */
//    private void setUpSpinner()
//    {
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.search_types_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//    }

    /**
     * Overriden lifecycle method.  Saves the query
     * and selection chosen.
     *
     * @param outstate  The outgoing saved instance state
     */
    @Override
    protected void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        outstate.putInt("selectedRadioId", selectedId);
        outstate.putString("query", query);
    }

    /**
     * This method queries the database based on the
     * selection.
     */
    private void searchDb()
    {
        Restaurant[] results = null;

        // Sending appropriate query
        switch(selectedId)
        {
            case R.id.radbtnName:
                results = db.searchByName(query);
                break;
            case R.id.radbtnCity:
                results = db.searchByCity(query);
                break;
            case R.id.radbtnGenre:
                results = db.searchByGenre(query);
                break;
        }

        // Displaying results
        if (results != null)
        {
            fragment.addToList(results, true);
        }
    }
}
