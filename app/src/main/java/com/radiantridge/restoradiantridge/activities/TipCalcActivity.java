package com.radiantridge.restoradiantridge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.radiantridge.restoradiantridge.R;

/**
 * This activity calculates the amount of tip when user inputs
 * the bill amount.
 *
 * @author Rafia Anwar
 * @version 30/11/2016
 */

public class TipCalcActivity extends AppCompatActivity {
    private static final String TAG = "TipCalculatorActivity";

    /**
     * Overridden lifecycle method.
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calc);
    }

    /**
     * This method takes the user input from the form, calculates and
     * displays the amount of tip and total amount i.e. bill with tip included.
     *
     * @param v View that fired the event
     */
    public void calculateTip(View v) {
        EditText billAmount = (EditText) findViewById(R.id.editText);
        String billStr = billAmount.getText().toString();   // represents the amount of bill

        // checks for null or empty field
        if (billStr == null || billStr.isEmpty()) {
            //displays error label if null or empty
            TextView amountErr = (TextView) findViewById(R.id.textAmountError);
            amountErr.setVisibility(View.VISIBLE);
        } else {
            Double bill = Double.parseDouble(billStr);

            // displays error label when bill amount is not greater than 0
            if (bill <= 0) {
                TextView amountErr = (TextView) findViewById(R.id.textAmountError);
                amountErr.setVisibility(View.VISIBLE);
            } else {
                // var for radio group containing radio buttons for tip %
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

                int selectedId = radioGroup.getCheckedRadioButtonId();
                Log.i(TAG, "selected id " + selectedId);

                // find the radio button by returned id
                if (selectedId != -1) {
                    // selected radio button for tip %
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    String tipRate = radioButton.getText().toString();
                    String tipStr = tipRate.substring(0, 2); //gets the numerical value
                    int tip = Integer.parseInt(tipStr);

                    Double calcTip = (bill / 100) * tip;
                    Double roundedCalcTip = Math.round(calcTip * 100.0) / 100.0;

                    Double billPlusTip = bill + roundedCalcTip;
                    Double roundedBillPlusTip = Math.round(billPlusTip * 100.0) / 100.0;

                    TextView tipTextView = (TextView) findViewById(R.id.textView6);
                    tipTextView.setText(roundedCalcTip.toString());

                    TextView totalTextView = (TextView) findViewById(R.id.textView8);
                    totalTextView.setText(roundedBillPlusTip.toString());
                }
            }
        }
    }
}
