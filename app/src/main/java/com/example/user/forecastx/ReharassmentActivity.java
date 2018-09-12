package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ReharassmentActivity extends AppCompatActivity {
    private RadioGroup reoccurrenceRg;
    private StringBuilder savedString;
    private StringBuilder editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reharassment);

        savedString = new StringBuilder(Constants.systemMessage);
        editString = new StringBuilder(savedString);

        reoccurrenceRg = findViewById(R.id.reoccurrence_radioGroup);
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double componentResult = generateComponentResult();
                Constants.componentResults[2] = componentResult;
                Log.d("Reoccurrence strength", String.valueOf(componentResult));
                Constants.systemMessage = new StringBuilder(editString);
                Intent intent = new Intent(ReharassmentActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    private double generateComponentResult() {
        RadioButton selectedRb = findViewById(reoccurrenceRg.getCheckedRadioButtonId());
        int position = reoccurrenceRg.indexOfChild(selectedRb);
        return Constants.POHA_REOCCURRENCE_WEIGHTS[position];
    }
}
