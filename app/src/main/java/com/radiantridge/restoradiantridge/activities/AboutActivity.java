package com.radiantridge.restoradiantridge.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radiantridge.restoradiantridge.R;

/**
* This activity tells you what our application does.
* It displays text and an image icon at the bottom.

* @author Victor
*/

public class AboutActivity extends MenuActivity {

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

        final Context context = AboutActivity.this;

        LinearLayout ll2 = (LinearLayout)findViewById(R.id.linearlayout2);

        ImageView iv3 = (ImageView)findViewById(R.id.aline);
        iv3.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv3.setClickable(true);
        iv3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context); //New alert dialog based on this current context
                builder.setMessage(R.string.alertbox_text);
                builder.setTitle(R.string.alertbox_title);
                builder.setCancelable(true);

                /*When clicked, this will simply close the dialog and display what was shown on the AboutActivity Screen.*/
                builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //Dismisses the dialogue
                    }
                });

                /*When clicked, this will take the user back to the main menu.*/
                builder.setNegativeButton(R.string.to_main_menu, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        ImageView iv4 = (ImageView)findViewById(R.id.erika);
        iv4.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv4.setClickable(true);
        iv4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context); //New alert dialog based on this current context
                builder.setMessage(R.string.alertbox_text);
                builder.setTitle(R.string.alertbox_title);
                builder.setCancelable(true);

                /*When clicked, this will simply close the dialog and display what was shown on the AboutActivity Screen.*/
                builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //Dismisses the dialogue
                    }
                });

                /*When clicked, this will take the user back to the main menu.*/
                builder.setNegativeButton(R.string.to_main_menu, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        ImageView iv5 = (ImageView)findViewById(R.id.rafia);
        iv5.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv5.setClickable(true);
        iv5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context); //New alert dialog based on this current context
                builder.setMessage(R.string.alertbox_text);
                builder.setTitle(R.string.alertbox_title);
                builder.setCancelable(true);

                /*When clicked, this will simply close the dialog and display what was shown on the AboutActivity Screen.*/
                builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //Dismisses the dialogue
                    }
                });

                /*When clicked, this will take the user back to the main menu.*/
                builder.setNegativeButton(R.string.to_main_menu, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        ImageView iv6 = (ImageView)findViewById(R.id.victor);
        iv6.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv6.setClickable(true);
        iv6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context); //New alert dialog based on this current context
                builder.setMessage(R.string.alertbox_text);
                builder.setTitle(R.string.alertbox_title);
                builder.setCancelable(true);

                /*When clicked, this will simply close the dialog and display what was shown on the AboutActivity Screen.*/
                builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //Dismisses the dialogue
                    }
                });

                /*When clicked, this will take the user back to the main menu.*/
                builder.setNegativeButton(R.string.to_main_menu, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

    }
}
