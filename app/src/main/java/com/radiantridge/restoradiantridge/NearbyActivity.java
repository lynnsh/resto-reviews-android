package com.radiantridge.restoradiantridge;

import android.os.Bundle;

public class NearbyActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
    }

    // TODO: display lat/long on UI
    // TODO: backdoor btn to force certain lat/long
}
