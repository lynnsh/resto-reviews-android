package com.radiantridge.restoradiantridge;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends MenuActivity {
    private Spinner spinner;
    DatabaseConnector db;
    RestoListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = DatabaseConnector.getDatabaseConnector(this);
        fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.favorites_list);
        setUpSpinner();
    }

    public void search(View view)
    {
        EditText searchBox = (EditText) findViewById(R.id.search_ET);
        String selection = (String) spinner.getSelectedItem();
        String query = searchBox.getText().toString();
        Restaurant[] results = null;

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
        
        if (results != null)
        {
            fragment.setList(results);
        }
    }

    private void setUpSpinner()
    {
        spinner = (Spinner) findViewById(R.id.searchType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
