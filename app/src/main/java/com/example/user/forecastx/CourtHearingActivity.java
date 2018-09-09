package com.example.user.forecastx;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CourtHearingActivity extends AppCompatActivity {
    private TextView title;
    private TextView q7a;
    private RadioButton courtHearingYes;
    private TextView q8b;
    private RadioGroup epoBeforeRadioGroup;
    private ConstraintLayout constraintLayout;
    private StringBuilder savedString;
    private StringBuilder editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_hearing);

        savedString = new StringBuilder(Constants.systemMessage);
        editString = new StringBuilder(savedString);


        title = findViewById(R.id.court_hearing_title);
        q7a = findViewById(R.id.court_hearing_partA);
        courtHearingYes = findViewById(R.id.court_hearing_yes);
        q8b = findViewById(R.id.court_hearing_partB);
        epoBeforeRadioGroup = findViewById(R.id.epo_before_radioGroup);
        constraintLayout = findViewById(R.id.court_hearing_constraint);

        if (!Constants.isCdra) {
            title.setText("Step 8/8: Court Hearing");
            q7a.setText("8a) Will the respondent turn up for the court hearing?:");

            if (!Constants.isEpoApplication) {
                q8b.setVisibility(View.VISIBLE);
                epoBeforeRadioGroup.setVisibility(View.VISIBLE);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.back_next_layout,ConstraintSet.TOP,R.id.epo_before_radioGroup,ConstraintSet.BOTTOM,48);
                constraintSet.applyTo(constraintLayout);
            }
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
                Constants.respondentIsAttending = courtHearingYes.isChecked();
                Constants.hasEpoBefore = hasEpoBefore();
                Log.d("Respondent attending", String.valueOf(Constants.respondentIsAttending));
                Log.d("has Epo Before", String.valueOf(Constants.hasEpoBefore));
                Constants.systemMessage = new StringBuilder(editString);
                Intent intent = new Intent(CourtHearingActivity.this, FinalReportActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean hasEpoBefore() {
        RadioButton selectedButton = findViewById(epoBeforeRadioGroup.getCheckedRadioButtonId());
        return selectedButton.getText().equals("Yes");

    }
}
