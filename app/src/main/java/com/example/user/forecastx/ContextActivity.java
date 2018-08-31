package com.example.user.forecastx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class ContextActivity extends AppCompatActivity {
    private RadioButton rb_cdra;
    private RadioButton rb_poha;
    private TextView read_more;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;
    private CheckBox cb7;
    private CheckBox cb8;
    private CheckBox cb9;
    private CheckBox cb10;
    private CheckBox cb11;
    private CheckBox cb12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);

        rb_cdra = this.findViewById(R.id.radioButton1);
        rb_poha = this.findViewById(R.id.radioButton2);
        read_more = this.findViewById(R.id.read_more_info);
        cb1 = this.findViewById(R.id.checkBox1);
        cb2 = this.findViewById(R.id.checkBox2);
        cb3 = this.findViewById(R.id.checkBox3);
        cb4 = this.findViewById(R.id.checkBox4);
        cb5 = this.findViewById(R.id.checkBox5);
        cb6 = this.findViewById(R.id.checkBox6);
        cb7 = this.findViewById(R.id.checkBox7);
        cb8 = this.findViewById(R.id.checkBox8);
        cb9 = this.findViewById(R.id.checkBox9);
        cb10 = this.findViewById(R.id.checkBox10);
        cb11 = this.findViewById(R.id.checkBox11);
        cb12 = this.findViewById(R.id.checkBox12);
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

            }
        });

    }
}
