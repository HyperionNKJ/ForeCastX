package com.example.user.forecastx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class PaymentActivity extends AppCompatActivity {
    private TextView title;
    private TextView q3a;
    private TextView epoMoreInfo;
    private RadioGroup epoRadioGroup;
    private TextView q3b;
    private EditText numSheets;
    private TextView q3c;
    private TotalCost totalCost;
    private RadioButton costYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        title = findViewById(R.id.payment_title);
        q3a = findViewById(R.id.payment_partA);
        epoMoreInfo = findViewById(R.id.epo_more_info);
        epoRadioGroup = findViewById(R.id.epo_radioGroup);
        q3b = findViewById(R.id.payment_partB);
        numSheets = findViewById(R.id.num_sheets);
        q3c = findViewById(R.id.payment_partC);
        costYes = findViewById(R.id.cost_yes);
        totalCost = new TotalCost();

        if (!Constants.isCdra) {
            title.setText("Step 4/8: Cost of application");
            q3a.setText("4a) Are you applying for an Expedited Protection Order (EPO)?:");
            q3b.setText("4b) How many sheets of evidence are you filing?:");
            q3c.setText("4c) Here is a breakdown of the total cost:");
        }

        epoMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PaymentActivity.this);
                LayoutInflater inflater = LayoutInflater.from(PaymentActivity.this);
                View dialogView = inflater.inflate(R.layout.cdra__poha_dialog, null);
                dialogBuilder.setView(dialogView).setCancelable(true);
                final AlertDialog alertDialog = dialogBuilder.create();
                TextView t = dialogView.findViewById(R.id.cdra);
                t.setText(Html.fromHtml(getString(R.string.epo_message)));
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

        epoRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selectedButton = findViewById(i);
                if (selectedButton.getText().equals("No")) {
                    totalCost.setEpo(false);
                } else {
                    totalCost.setEpo(true);
                }
            }
        });

        numSheets.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                int numOfSheets = 0;
                try {
                    numOfSheets = Integer.parseInt(editable.toString());
                    if (numOfSheets > Constants.MAX_NUM_OF_EVIDENCE_SHEETS) {
                        editable.replace(0, editable.length(), String.valueOf(Constants.MAX_NUM_OF_EVIDENCE_SHEETS));
                        numOfSheets = Constants.MAX_NUM_OF_EVIDENCE_SHEETS;
                    }
                }
                catch(NumberFormatException e) {}
                finally {
                    totalCost.setNumEvidenceSheet(numOfSheets);
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
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
                Constants.totalApplicationCost = totalCost.getTotalCost();
                Constants.canAfford = costYes.isChecked();
                if (!Constants.canAfford) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                    builder.setMessage("You are unable to afford the cost of application.\n\nChoosing yes will end the assessment and generate your report.")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PaymentActivity.this, FinalReportActivity.class);
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
                    RadioButton selectedEpoRb = findViewById(epoRadioGroup.getCheckedRadioButtonId());
                    Constants.isEpoApplication = selectedEpoRb.getText().equals("Yes");
                    Log.d("canAfford", String.valueOf(Constants.canAfford));
                    Intent intent = new Intent(PaymentActivity.this, RespondentParticularActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    class TotalCost {
        private boolean epoApplication;
        private int numEvidenceSheet;
        private double totalCost;
        private TextView evidenceQty;
        private TextView evidenceCost;
        private TextView epoQty;
        private TextView epoCost;
        private TextView tv_totalCost;

        public TotalCost() {
            evidenceQty = findViewById(R.id.evidence_quantity);
            evidenceCost = findViewById(R.id.evidence_cost);
            epoQty = findViewById(R.id.epo_quantity);
            epoCost = findViewById(R.id.epo_cost);
            tv_totalCost = findViewById(R.id.total_cost);
        }

        public double getTotalCost() {
            return totalCost;
        }

        public void setEpo(boolean epoApplication) {
            this.epoApplication = epoApplication;
            updateReceipt();
        }

        public void setNumEvidenceSheet(int numEvidenceSheet) {
            this.numEvidenceSheet = numEvidenceSheet;
            updateReceipt();
        }

        private void updateReceipt() {
            evidenceQty.setText(String.valueOf(numEvidenceSheet));
            evidenceCost.setText(String.valueOf("$" + numEvidenceSheet));
            double subTotal = Constants.GENERAL_COST_OF_APPLICATION + numEvidenceSheet;
            if (epoApplication) {
                epoQty.setText("1");
                epoCost.setText(String.valueOf("+16%"));
                totalCost = subTotal * Constants.PERCENTAGE_COST_OF_EPO_APPLICATION;
            } else {
                epoQty.setText("0");
                epoCost.setText(String.valueOf("$0"));
                totalCost = subTotal;
            }
            DecimalFormat df = new DecimalFormat("#.00");
            df.setRoundingMode(RoundingMode.CEILING);
            tv_totalCost.setText("$" + df.format(totalCost));
        }
    }
}
