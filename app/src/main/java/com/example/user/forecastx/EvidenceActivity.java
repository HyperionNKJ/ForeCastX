package com.example.user.forecastx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class EvidenceActivity extends AppCompatActivity {
    private Act act;
    private ArrayList<CheckBox> evidenceCheckboxes;
    private StringBuilder savedString;
    private StringBuilder editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence);

        savedString = new StringBuilder(Constants.systemMessage);
        editString = new StringBuilder(savedString);

        TextView title = findViewById(R.id.evidence_title);
        if (!Constants.isCdra) {
            title.setText("Step 1/8: Evidence");
        }

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
                editString.append("<h6><u>Evidence</u></h6>");
                boolean hasEvidence = false;
                String[] evidenceMessages = getResources().getStringArray(R.array.evidence_message);
                for (int i = 0; i < Constants.NUM_OF_EVIDENCE; i++) {
                    boolean hasThisEvidence = evidenceCheckboxes.get(i).isChecked();
                    if (hasThisEvidence) {
                        hasEvidence = true;
                        editString.append(evidenceMessages[i]);
                    }
                    evidences[i] = hasThisEvidence;
                }
                if (!hasEvidence) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EvidenceActivity.this);
                    builder.setMessage("You did not select any evidence.\n\nChoosing yes will end the assessment and generate your report.")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(EvidenceActivity.this, FinalReportActivity.class);
                                    intent.putExtra("Forced probability", "Unlikely");
                                    editString.append(getString(R.string.evidence_absence));
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
                    double componentResult = act.generateEvidenceStrength(evidences);
                    Constants.componentResults[0] = componentResult;
                    Log.d("Evidence strength", String.valueOf(componentResult));
                    Constants.systemMessage = new StringBuilder(editString);
                    editString = new StringBuilder(savedString);
                    Intent intent = new Intent(EvidenceActivity.this, DateFrequencyActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
