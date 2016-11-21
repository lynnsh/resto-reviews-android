package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Menu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check saved prefs
        // send user name in intent
        startActivity(new Intent(this, MainRestoActivity.class));
    }

    // log in functionality

    // save the login in and redirect to MainRestoActivity
}
