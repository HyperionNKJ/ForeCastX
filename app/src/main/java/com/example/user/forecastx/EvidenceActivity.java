package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class EvidenceActivity extends AppCompatActivity {
    private Act act;
    private ArrayList<CheckBox> evidenceCheckboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            act = bundle.getParcelable("act");
        }
        Log.d("After", act.toString());

        evidenceCheckboxes = new ArrayList<>();
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox1));
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox2));
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox3));
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox4));
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox5));
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox6));
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox7));
        evidenceCheckboxes.add((CheckBox) this.findViewById(R.id.evidence_checkBox8));

        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] evidences = new boolean[Constants.NUM_OF_EVIDENCE];
                for (int i = 0; i < Constants.NUM_OF_EVIDENCE; i++) {
                    evidences[i] = evidenceCheckboxes.get(i).isChecked();
                }
                double componentResult = act.generateEvidenceStrength(evidences);
                Constants.componentResults[0] = componentResult;
                Log.d("Evidence strength", String.valueOf(componentResult));
                Intent intent = new Intent(EvidenceActivity.this, DateFrequencyActivity.class);
                startActivity(intent);
            }
        });
    }
}
