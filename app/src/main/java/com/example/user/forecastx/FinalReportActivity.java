package com.example.user.forecastx;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

        Bundle extras = getIntent().getExtras();    // have extra = assessment abruptly ended due to certain unmet condition
        if (extras != null) {
            String forcedProbability = extras.getString("Forced probability");
            tv_finalProbability.setText(forcedProbability);
        } else {
            finalProbability = Constants.computeFinalProbability();
            setProbability();
            Log.d("Component strength", Arrays.toString(Constants.componentResults));
            Log.d("Final probability", String.valueOf(finalProbability));
        }

        setProbabilityColour();
        showSystemMessage();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalReportActivity.this, MainActivity.class);
                Constants.systemMessage = new StringBuilder();
                startActivity(intent);
                finish();
            }
        });
    }

    private void setProbability() {
        if (finalProbability >= 0 && finalProbability < 21) {
            tv_finalProbability.setText("Highly Unlikely");
        } else if (finalProbability >= 21 && finalProbability < 41) {
            tv_finalProbability.setText("Unlikely");
        } else if (finalProbability >= 41 && finalProbability < 61) {
            tv_finalProbability.setText("Probable");
        } else if (finalProbability >= 61 && finalProbability < 81 ){
            tv_finalProbability.setText("Likely");
        } else {
            tv_finalProbability.setText("Highly Likely");
        }
    }

    private void setProbabilityColour() {
        switch(tv_finalProbability.getText().toString()) {
            case "Highly Unlikely":
                tv_finalProbability.setTextColor(Color.parseColor("#FFFF0000"));
                break;
            case "Unlikely":
                tv_finalProbability.setTextColor(Color.parseColor("#FFEE00FF"));
                break;
            case "Probable":
                tv_finalProbability.setTextColor(Color.parseColor("#FFFF9900"));
                break;
            case "Likely":
                tv_finalProbability.setTextColor(Color.parseColor("#FF00FF0D"));
                break;
            case "Highly Likely":
                tv_finalProbability.setTextColor(Color.parseColor("#FF00C800"));
                break;
            default:
                tv_finalProbability.setTextColor(Color.parseColor("#FF5F50FF"));
                break;
        }
    }

    private void showSystemMessage() {
        Constants.systemMessage.append("<h6><u>Disclaimer</u></h6>").append(getString(R.string.disclaimer));
        Constants.systemMessage.append("<h6><u>Closing</u></h6>").append(getString(R.string.closing));
        tv_system_message.setText(Html.fromHtml(Constants.systemMessage.toString()));
    }
}
