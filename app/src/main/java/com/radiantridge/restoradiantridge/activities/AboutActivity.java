package com.radiantridge.restoradiantridge.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radiantridge.restoradiantridge.R;

/**
 * This activity tells you what our application does.
 * It displays text and an image icon at the bottom.
 *
 * @author Victor Ruggi
 * @author Erika Bourque
 */
public class AboutActivity extends MenuActivity {

    /**
     * Minimum override method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout ll = (LinearLayout) findViewById(R.id.LL);
        TextView schoolName = new TextView(this);
        schoolName.setText(R.string.about_header);
        schoolName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
        schoolName.setTextColor(ContextCompat.getColor(this, R.color.about_and_calc));
        schoolName.setClickable(true);
        schoolName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.dawsoncollege.qc.ca";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        schoolName.setGravity(Gravity.CENTER);
        ll.addView(schoolName);

        TextView courseName = new TextView(this);
        courseName.setText(R.string.about_course_desc);
        courseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        courseName.setTextColor(ContextCompat.getColor(this, R.color.text));
        courseName.setClickable(true);
        courseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.dawsoncollege.qc.ca/computer-science-technology/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        courseName.setGravity(Gravity.CENTER);
        ll.addView(courseName);

        ImageView groupPic = (ImageView) findViewById(R.id.group_picture);
        groupPic.setScaleType(ImageView.ScaleType.FIT_CENTER);
        groupPic.setClickable(true);
        groupPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });

    }

    /**
     * Method to display the alert dialog containing the group's details.
     */
    private void popup() {
        //New alert dialog based on this current context
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_about_text);
        builder.setTitle(R.string.dialog_about_title);
        builder.setCancelable(true);

        /*When clicked, this will simply close the dialog and display what was shown on the AboutActivity Screen.*/
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //Dismisses the dialogue
            }
        });

        /*When clicked, this will take the user back to the main menu.*/
        builder.setNegativeButton(R.string.to_main_menu, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }
}
