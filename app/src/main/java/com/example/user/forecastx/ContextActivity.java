package com.example.user.forecastx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ContextActivity extends AppCompatActivity {
    private RadioGroup rg;
    private RadioButton rb_cdra;
    private RadioButton rb_poha;
    private TextView read_more;
    private TextView cdra_message;
    private TextView poha_message;
    private LinearLayout cdra_checkboxes;
    private LinearLayout poha_checkboxes;
    private ArrayList<CheckBox> checkBoxes;
    private StringBuilder savedString;
    private StringBuilder editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);

        savedString = new StringBuilder(Constants.systemMessage);
        editString = new StringBuilder(savedString);

        rg = this.findViewById(R.id.radioGroup);
        rb_cdra = this.findViewById(R.id.radioButton1);
        rb_poha = this.findViewById(R.id.radioButton2);
        read_more = this.findViewById(R.id.read_more_info);
        checkBoxes = new ArrayList<>();
        cdra_message = this.findViewById(R.id.cdra_message);
        poha_message = this.findViewById(R.id.poha_message);
        cdra_checkboxes = this.findViewById(R.id.CDRA_checkboxes);
        poha_checkboxes = this.findViewById(R.id.POHA_checkboxes);
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox1));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox2));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox3));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox4));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox5));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox6));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox7));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox8));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox9));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox10));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox11));
        checkBoxes.add((CheckBox) this.findViewById(R.id.checkBox12));

        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] interferencesOrHarassment;
                Act act;
                if (rb_cdra.isChecked()) {
                    interferencesOrHarassment = new boolean[Constants.NUM_OF_UNREASONABLE_INTERFERENCE];
                    for (int i = 0; i < Constants.NUM_OF_UNREASONABLE_INTERFERENCE; i++) {
                        interferencesOrHarassment[i] = checkBoxes.get(i).isChecked();
                    }
                    act = new Cdra(interferencesOrHarassment);
                    Constants.isCdra = true;
                } else {
                    interferencesOrHarassment = new boolean[Constants.NUM_OF_HARASSMENT_TYPE];
                    for (int i = 0; i < Constants.NUM_OF_HARASSMENT_TYPE; i++) {
                        interferencesOrHarassment[i] = checkBoxes.get(i + Constants.NUM_OF_UNREASONABLE_INTERFERENCE).isChecked();
                    }
                    act = new Poha(interferencesOrHarassment);
                    Constants.isCdra = false;
                }

                if (act.hasNoContext()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContextActivity.this);
                    builder.setMessage("You did not specify any cause of action.\n\nChoosing yes will end the assessment and generate your report.")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ContextActivity.this, FinalReportActivity.class);
                                    intent.putExtra("Forced probability", "Not Applicable");
                                    editString.append("<h6><u>Context</u></h6>").append(getString(R.string.context_absence));
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
                    Intent intent = new Intent(ContextActivity.this, EvidenceActivity.class);
                    Bundle bundle = new Bundle();
                    Constants.systemMessage = new StringBuilder(editString);    // need to update else next component will repeat system message when retrieving from Constants.systemMessage
                    bundle.putParcelable("act", act);
                    intent.putExtras(bundle);
                    Log.d("Before", act.toString());
                    startActivity(intent);
                }
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == rb_cdra.getId()) {
                    setVisibility(View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                    resetCheckboxes(Constants.NUM_OF_UNREASONABLE_INTERFERENCE, Constants.NUM_OF_UNREASONABLE_INTERFERENCE + Constants.NUM_OF_HARASSMENT_TYPE - 1);
                } else {
                    setVisibility(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
                    resetCheckboxes(0, Constants.NUM_OF_UNREASONABLE_INTERFERENCE - 1);
                }
            }
        });
        read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ContextActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ContextActivity.this);
                View dialogView = inflater.inflate(R.layout.cdra__poha_dialog, null);
                dialogBuilder.setView(dialogView).setCancelable(true);
                final AlertDialog alertDialog = dialogBuilder.create();
                TextView t = dialogView.findViewById(R.id.cdra);
                t.setText(Html.fromHtml(getString(R.string.cdra_message) + getString(R.string.poha_message)));
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
    }

    private void setVisibility(int cdraMessageV, int cdraCheckboxesV, int pohaMessageV, int pohaCheckboxesV) {
        cdra_message.setVisibility(cdraMessageV);
        cdra_checkboxes.setVisibility(cdraCheckboxesV);
        poha_message.setVisibility(pohaMessageV);
        poha_checkboxes.setVisibility(pohaCheckboxesV);
    }

    private void resetCheckboxes(int start, int end) {
        for (int i = start; i <= end; i++) {
            checkBoxes.get(i).setChecked(false);
        }
    }
}
