package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class PersonalServiceActivity extends AppCompatActivity {
    private TextView title;
    private TextView q5a;
    private RadioButton personalDeliveryRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_service);

        title = findViewById(R.id.personal_service_title);
        q5a = findViewById(R.id.personal_service_partA);
        personalDeliveryRb = findViewById(R.id.personal_service_yes);

        if (!Constants.isCdra) {
            title.setText("Step 6/8: Personal Service");
            q5a.setText("6a) Do you have the ability to serve legal documents to him personally?:");
        }

        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.canDeliver = personalDeliveryRb.isChecked() || Constants.hasAddress;
                Log.d("canDeliver", String.valueOf(Constants.canDeliver));
                Intent intent = new Intent(PersonalServiceActivity.this, PtcActivity.class);
                startActivity(intent);
            }
        });
    }
}
