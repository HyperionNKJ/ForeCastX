package com.example.user.forecastx;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RespondentParticularActivity extends AppCompatActivity {
    private TextView title;
    private TextView q4a;
    private ArrayList<CheckBox> particularsCheckboxes;
    private ConstraintLayout constraintLayout;

    // cyberbullying for POHA
    private TextView q5b;
    private RadioGroup q5bRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respondent_particular);

        title = findViewById(R.id.respondent_title);
        q4a = findViewById(R.id.respondent_partA);
        constraintLayout = findViewById(R.id.respondent_constraint);

        if (!Constants.isCdra) {
            title.setText("Step 5/8: Respondent's particulars");
            q4a.setText("5a) Please tick all the particulars of the respondent that you are able to furnish:");
            q5b = findViewById(R.id.respondent_partB);
            q5bRadioGroup = findViewById(R.id.cyber_radioGroup);
            q5b.setVisibility(View.VISIBLE);
            q5bRadioGroup.setVisibility(View.VISIBLE);

            // move next back layout below q5b
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.respondent_back_next_layout,ConstraintSet.TOP,R.id.cyber_radioGroup,ConstraintSet.BOTTOM,48);
            constraintSet.applyTo(constraintLayout);
        }

        particularsCheckboxes = new ArrayList<>();
        particularsCheckboxes.add((CheckBox) this.findViewById(R.id.respondent_checkBox1));
        particularsCheckboxes.add((CheckBox) this.findViewById(R.id.respondent_checkBox2));
        particularsCheckboxes.add((CheckBox) this.findViewById(R.id.respondent_checkBox3));
        particularsCheckboxes.add((CheckBox) this.findViewById(R.id.respondent_checkBox4));

        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.hasName = particularsCheckboxes.get(0).isChecked();
                Constants.hasAddress = particularsCheckboxes.get(1).isChecked();
                float[] particularWeight;
                if (!Constants.isCdra && isCyberBullying()) {
                    particularWeight = Constants.CYBER_REPONDENT_PARTICULARS_WEIGHTS;
                } else {
                    particularWeight = Constants.RESPONDENT_PARTICULARS_WEIGHTS;
                }
                // this component has free initial "50" weight.
                double componentResult = (50 + ((particularsCheckboxes.get(2).isChecked()) ? particularWeight[0] : 0)
                        + ((particularsCheckboxes.get(3).isChecked()) ? particularWeight[1] : 0));
                if (componentResult > 100) {
                    Constants.componentResults[3] = 100;
                } else {
                    Constants.componentResults[3] = componentResult;
                }
                Log.d("Particulars results", String.valueOf(Constants.componentResults[3]));
                Intent intent = new Intent(RespondentParticularActivity.this, PersonalServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isCyberBullying() {
        RadioButton checkedRadioButton = findViewById(q5bRadioGroup.getCheckedRadioButtonId());
        return checkedRadioButton.getText().equals("Yes");
    }
}
