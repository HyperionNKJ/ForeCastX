package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AggressorActivity extends AppCompatActivity {
    private RadioGroup aggressorRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggressor);

        aggressorRg = findViewById(R.id.aggressor_radioGroup);
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
                Log.d("Aggressor strength", String.valueOf(componentResult));
                Intent intent = new Intent(AggressorActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    private double generateComponentResult() {
        RadioButton selectedRb = findViewById(aggressorRg.getCheckedRadioButtonId());
        int position = aggressorRg.indexOfChild(selectedRb);
        return Constants.POHA_AGGRESSOR_WEIGHTS[position];
    }
}
