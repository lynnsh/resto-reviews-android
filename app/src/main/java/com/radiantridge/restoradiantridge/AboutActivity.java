package com.radiantridge.restoradiantridge;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* This activity tells you what our application does.
* It displays text and an image icon at the bottom.

* @author Victor
*/

public class AboutActivity extends Menu {

	/**
	* Minimum override method
	*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout ll = (LinearLayout)findViewById(R.id.LL);
        TextView tv1 = new TextView(this);
        tv1.setText(R.string.about_header);
        tv1.setTextSize(40);
        tv1.setTextColor(Color.parseColor("#03CA00")); //Some nice forest GREEN.
        tv1.setClickable(true);
        tv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://www.dawsoncollege.qc.ca";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        ll.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(R.string.course_desc);
        tv2.setTextSize(24);
        tv2.setTextColor(Color.parseColor("#00A080")); //How about some San Jose Sharks TEAL?
        tv2.setClickable(true);
        tv2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://www.dawsoncollege.qc.ca/computer-science-technology/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        ll.addView(tv2);

        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.grouppic);
        iv3.setClickable(true);
        iv3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage(R.string.alertbox_text).setTitle(R.string.alertbox_title).setCancelable(true);
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
        ll.addView(iv3);

    }
}