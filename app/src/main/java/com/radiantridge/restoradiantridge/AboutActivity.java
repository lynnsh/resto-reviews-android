package com.radiantridge.restoradiantridge;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutActivity extends Menu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Get outer layout.
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_about);

        //Get inner layout. I set the orientation to vertical in my XML.
        LinearLayout ll = (LinearLayout)findViewById(R.id.LL);
        TextView tv1 = new TextView(this);
        tv1.setText(R.string.menu_about);
        tv1.setTextSize(28);
        tv1.setTextColor(Color.RED);
        ll.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(R.string.about_menu_text);
        tv1.setTextSize(20);
        tv1.setTextColor(Color.parseColor("#03CA00")); /*Green*/
        ll.addView(tv2);

        rl.addView(ll);
    }
}
