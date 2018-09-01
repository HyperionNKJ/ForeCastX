package com.example.user.forecastx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EvidenceActivity extends AppCompatActivity {
    private Act act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            act = bundle.getParcelable("act");
        }
        Log.d("After", act.toString());
    }
}
