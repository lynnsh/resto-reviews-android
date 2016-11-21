package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainRestoActivity extends Menu {
    private static final String TAG = "Resto Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resto);

        // change greetingTxt to greeting string + user name
    }

    public void launchFavorites(View view)
    {
        startActivity(new Intent(this, FavoritesActivty.class));
    }

    public void launchSearch(View view)
    {
        startActivity(new Intent(this, SearchActivity.class));
    }

    public void launchNearby(View view)
    {
        startActivity(new Intent(this, NearbyActivity.class));
    }

    public void launchTipCalc(View view)
    {
        startActivity(new Intent(this, TipCalcActivity.class));
    }

    public void launchAddResto(View view)
    {
        startActivity(new Intent(this, AddRestoActivity.class));
    }
}
