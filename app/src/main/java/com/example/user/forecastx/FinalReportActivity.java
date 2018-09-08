package com.example.user.forecastx;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

public class FinalReportActivity extends AppCompatActivity {
    private TextView tv_finalProbability;
    private TextView tv_system_message;
    private double finalProbability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);

        tv_finalProbability = findViewById(R.id.final_probability);
        tv_system_message = findViewById(R.id.system_message);

        finalProbability = Constants.computeFinalProbability();
        Log.d("Final probability", String.valueOf(finalProbability));

        if (finalProbability >= 0 && finalProbability < 26) {
            tv_finalProbability.setText("Unlikey");
            tv_finalProbability.setTextColor(Color.parseColor("#FFFF0000"));
        } else if (finalProbability >= 26 && finalProbability < 51) {
            tv_finalProbability.setText("Probable");
            tv_finalProbability.setTextColor(Color.parseColor("#FFF3A034"));
        } else if (finalProbability >= 51 && finalProbability < 76 ){
            tv_finalProbability.setText("Likely");
            tv_finalProbability.setTextColor(Color.parseColor("#FFFFF12A"));
        } else {
            tv_finalProbability.setText("Highly Likely");
            tv_finalProbability.setTextColor(Color.parseColor("#FF29B902"));
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalReportActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
