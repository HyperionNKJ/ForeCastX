package com.example.user.forecastx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
    private StringBuilder savedString;
    private StringBuilder editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_service);

        savedString = new StringBuilder(Constants.systemMessage);
        editString = new StringBuilder(savedString);

        title = findViewById(R.id.personal_service_title);
        q5a = findViewById(R.id.personal_service_partA);
        personalDeliveryRb = findViewById(R.id.personal_service_yes);

        if (Constants.hasAddress && Constants.isCdra) {
            q5a.setText("5a) Are you able to find the Respondent to serve him the legal documents in person, or do so through a process server via his address?:");
        } else if (Constants.hasAddress && !Constants.isCdra) {
            title.setText("Step 6/8: Personal Service");
            q5a.setText("6a) Are you able to find the Respondent to serve him the legal documents in person, or do so through a process server via his address?:");
        } else if (!Constants.hasAddress && !Constants.isCdra) {
            title.setText("Step 6/8: Personal Service");
            q5a.setText("6a) Are you able to find the Respondent to serve him the legal documents in person?:");
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
                Constants.canDeliver = personalDeliveryRb.isChecked();
                if (!Constants.canDeliver) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PersonalServiceActivity.this);
                    builder.setMessage("There is no way for the Respondent to receive the legal documents.\n\nChoosing yes will end the assessment and generate your report.")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PersonalServiceActivity.this, FinalReportActivity.class);
                                    intent.putExtra("Forced probability", "Not Applicable");
                                    editString.append("<h6><u>Personal Service</u></h6>");
                                    if (Constants.hasAddress) {
                                        editString.append(getString(R.string.personal_service_case2));
                                    } else {
                                        editString.append(getString(R.string.personal_service_case1));
                                    }
                                    Constants.systemMessage = new StringBuilder(editString);
                                    editString = new StringBuilder(savedString);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Are you sure?");
                    alert.show();
                } else {
                    Log.d("canDeliver", String.valueOf(Constants.canDeliver));
                    Constants.systemMessage = new StringBuilder(editString);
                    Intent intent = new Intent(PersonalServiceActivity.this, PtcActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
