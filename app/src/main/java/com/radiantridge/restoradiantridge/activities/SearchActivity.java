package com.radiantridge.restoradiantridge.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    private Spinner spinner;
    DatabaseHelper db;
    RestoListFragment fragment;

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

        // Setting up spinner with its options
        setUpSpinner();
    }

    /**
     * Event handler for search button.  Queries the database
     * with the appropriate search type.
     *
     * @param view      The view that fired the event
     */
    public void search(View view)
    {
        EditText searchBox = (EditText) findViewById(R.id.search_ET);
        String selection = (String) spinner.getSelectedItem();
        String query = searchBox.getText().toString();
        Restaurant[] results = null;

        // Sending appropriate query
        switch(selection)
        {
            case "Name":
                results = db.searchByName(query);
                break;
            case "City":
                results = db.searchByCity(query);
                break;
            case "Genre":
                results = db.searchByGenre(query);
                break;
        }
        
        // Displaying results
        if (results != null)
        {
            fragment.addToList(results, true);
        }
    }

    /**
     * This method gives the spinner its adapter from
     * a saved array of strings for the search type.
     */
    private void setUpSpinner()
    {
        spinner = (Spinner) findViewById(R.id.searchType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
