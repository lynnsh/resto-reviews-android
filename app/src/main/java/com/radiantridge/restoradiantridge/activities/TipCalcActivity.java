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
 *
 * @author Rafia Anwar
 */
public class TipCalcActivity extends AppCompatActivity {
    private static final String TAG = "Tip Act";

    private String billStr;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calc);
    }
    public void onCalculateTip(View v) {
        //switch between modes

        EditText billAmount = (EditText) findViewById(R.id.editText);
        //check for null and empty string
//show error, make a new field, hide show it

        billStr = billAmount.getText().toString();
        if(billStr==null || billStr.isEmpty()) {
            //show error label
            TextView amountErr= (TextView) findViewById(R.id.textAmountError);
            amountErr.setVisibility(View.VISIBLE);

        }
        else {
            Double bill = Double.parseDouble(billStr);
            if(bill <= 0)
            {
                TextView amountErr= (TextView) findViewById(R.id.textAmountError);
                amountErr.setVisibility(View.VISIBLE);
            }
            else {
                radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Log.i(TAG, "selected id " + selectedId);
                // find the radiobutton by returned id
                if(selectedId != -1) {
                    radioButton = (RadioButton) findViewById(selectedId);
                    String tipRate = radioButton.getText().toString();
                    String tipStr = tipRate.substring(0, 2); //gets the numerical value
                    int tip = Integer.parseInt(tipStr);

                    Double calcTip = (bill / 100) * tip;
                    Double calculatedTip = Math.round(calcTip * 100.0) / 100.0;

                    Double billPlusTip = bill + calculatedTip;
                    Double billAndTip = Math.round(billPlusTip * 100.0) / 100.0;

                    TextView tipTextView = (TextView) findViewById(R.id.calc_tip_tv);
                    tipTextView.setText(calculatedTip.toString());

                    TextView totalTextView = (TextView) findViewById(R.id.calc_total_tv);
                    totalTextView.setText(billAndTip.toString());
                }
            }
        }
    }

    }
