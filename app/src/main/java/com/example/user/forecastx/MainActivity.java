package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView agreementMessage;
    private CheckBox agreementCheckbox;
    private Button startAssessmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agreementMessage = findViewById(R.id.agree_message);
        agreementCheckbox = findViewById(R.id.agree_checkbox);
        startAssessmentButton = findViewById(R.id.start_assessment);

        agreementMessage.setText(Html.fromHtml(getString(R.string.agreement_message)));
        agreementMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View dialogView = inflater.inflate(R.layout.cdra__poha_dialog, null);
                dialogBuilder.setView(dialogView).setCancelable(true);
                final AlertDialog alertDialog = dialogBuilder.create();
                TextView t = dialogView.findViewById(R.id.cdra);
                t.setText(Html.fromHtml(getString(R.string.terms_and_condition)));
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

        agreementCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                startAssessmentButton.setEnabled(b);
            }
        });

        startAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContextActivity.class));
            }
        });
    }
}
