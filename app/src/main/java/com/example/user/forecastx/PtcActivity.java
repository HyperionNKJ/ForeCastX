package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class PtcActivity extends AppCompatActivity {
    private TextView title;
    private TextView q6a;
    private RadioButton ptcYesRb;
    private TextView q6b;
    private Spinner mediationSpinner;
    private TextView readMoreAboutPtc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptc);

        title = findViewById(R.id.Ptc_title);
        q6a = findViewById(R.id.Ptc_partA);
        ptcYesRb = findViewById(R.id.ptc_yes);
        q6b = findViewById(R.id.Ptc_partB);
        readMoreAboutPtc = findViewById(R.id.read_more_info);
        mediationSpinner = findViewById(R.id.mediation_spinner);

        ArrayAdapter<CharSequence> adapterForCell = ArrayAdapter.createFromResource(this,
                R.array.mediation, R.layout.dropdown_item);
        adapterForCell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mediationSpinner.setAdapter(adapterForCell);

        if (!Constants.isCdra) {
            title.setText("Step 7/8: Pre-Trial Conference (PTC)");
            q6a.setText("7a) Can you get the respondent down for Pre-Trial Conference?:");
            q6b.setText("7b) How many times have you tried mediation with the respondent?");
        }

        readMoreAboutPtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PtcActivity.this);
                LayoutInflater inflater = LayoutInflater.from(PtcActivity.this);
                View dialogView = inflater.inflate(R.layout.cdra__poha_dialog, null);
                dialogBuilder.setView(dialogView).setCancelable(true);
                final AlertDialog alertDialog = dialogBuilder.create();
                TextView t = dialogView.findViewById(R.id.cdra);
                t.setText(Html.fromHtml(getString(R.string.ptc_message)));
                alertDialog.show();
                ImageView closeButton = (ImageView) alertDialog.findViewById(R.id.cdra_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double componentResult = ((ptcYesRb.isChecked()) ? Constants.PTC_RESPONDENT_CAN_ATTEND_WEIGHT : 0)
                        + (mediationSpinner.getSelectedItemPosition() * Constants.PTC_PER_MEDIATION_WEIGHT);
                if (componentResult > 100) {
                    Constants.componentResults[4] = 100;
                } else {
                    Constants.componentResults[4] = componentResult;
                }
                Log.d("PtcActivity strength", String.valueOf(Constants.componentResults[4]));
                Intent intent = new Intent(PtcActivity.this, CourtHearingActivity.class);
                startActivity(intent);
            }
        });
    }
}