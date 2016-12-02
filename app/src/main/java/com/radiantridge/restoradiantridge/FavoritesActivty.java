package com.radiantridge.restoradiantridge;

import android.os.Bundle;

public class FavoritesActivty extends MenuActivity {
    DatabaseConnector db;
    RestoListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        db = DatabaseConnector.getDatabaseConnector(this);
        fragment = (RestoListFragment) getFragmentManager().findFragmentById(R.id.favorites_list);
        fragment.setList(db.getAllRestos());
    }
}
