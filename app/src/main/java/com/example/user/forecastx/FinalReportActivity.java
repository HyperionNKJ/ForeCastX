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
    private double finalProbability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);

        tv_finalProbability = findViewById(R.id.final_probability);
        finalProbability = Constants.computeFinalProbability();
        tv_finalProbability.setText(String.valueOf((int) Math.ceil(finalProbability)) + "%");

        if (finalProbability >= 0 && finalProbability < 40) {
            tv_finalProbability.setTextColor(Color.parseColor("#FFFF3B3B"));
        } else if (finalProbability >= 40 && finalProbability < 75) {
            tv_finalProbability.setTextColor(Color.parseColor("#FFFFAD2A"));
        } else {
            tv_finalProbability.setTextColor(Color.parseColor("#FF07B20C"));
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
