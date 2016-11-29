package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Menu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check saved prefs
    }
    public void onTip(View view){
        Intent i = new Intent(this, TipCalcActivity.class);
        startActivity(i);
    }
    // log in functionality

    // save the login in and redirect to MainRestoActivity
}
