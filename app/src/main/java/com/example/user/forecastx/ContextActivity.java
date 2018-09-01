package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ContextActivity extends AppCompatActivity {
    private RadioButton rb_cdra;
    private RadioButton rb_poha;
    private TextView read_more;
    private ArrayList<CheckBox> checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);

        rb_cdra = this.findViewById(R.id.radioButton1);
        rb_poha = this.findViewById(R.id.radioButton2);
        read_more = this.findViewById(R.id.read_more_info);
        checkBoxes = new ArrayList<>();
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
                Intent intent = new Intent(ContextActivity.this, MainActivity.class);
                startActivity(intent);
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
                } else {
                    interferencesOrHarassment = new boolean[Constants.NUM_OF_HARASSMENT_TYPE];
                    for (int i = 0; i < Constants.NUM_OF_HARASSMENT_TYPE; i++) {
                        interferencesOrHarassment[i] = checkBoxes.get(i + Constants.NUM_OF_UNREASONABLE_INTERFERENCE).isChecked();
                    }
                    act = new Poha(interferencesOrHarassment);
                }
                Intent intent = new Intent(ContextActivity.this, EvidenceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("act", act);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.d("Before", act.toString());
                finish();
            }
        });

    }
}
