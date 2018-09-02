package com.example.user.forecastx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFrequencyActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView lastOccurrence;
    private Calendar lastOccurrenceDate;
    private TextView question3b;
    private TextView question3bFrequency;
    private SeekBar question3bSeekbar;
    private TextView question3c;
    private TextView question3cMessage;
    private Spinner question3cSpinner;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_frequency);

        question3b = findViewById(R.id.three_3b);
        question3bFrequency = findViewById(R.id.three_3b_frequency);
        question3bSeekbar = findViewById(R.id.three_3b_seekBar);
        question3c = findViewById(R.id.three_3c);
        question3cMessage = findViewById(R.id.three_3c_message);
        question3cSpinner = findViewById(R.id.three_3c_spinner);
        lastOccurrence = findViewById(R.id.lastOccurrence);
        constraintLayout = findViewById(R.id.constraint_layout);

        if (!Constants.isCdra) {
            question3b.setText("How many times were you harassed?:");
            question3bSeekbar.setMax(Constants.NUM_OF_OPTIONS_FOR_POHA_FREQUENCY - 1);
            question3c.setText("3c) On average, what was the frequency of harassment?");
            question3cMessage.setText("I was harassed once every ");
        }

        question3bSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String frequencyToShow;
                if (i == 0) {
                    frequencyToShow = "1 time";
                    toggleQuestion3c(View.INVISIBLE);
                } else if (Constants.isCdra && (i == Constants.NUM_OF_OPTIONS_FOR_CDRA_FREQUENCY - 1)) {
                    frequencyToShow = "10 times and more";
                    toggleQuestion3c(View.VISIBLE);
                } else if (!Constants.isCdra && (i == Constants.NUM_OF_OPTIONS_FOR_POHA_FREQUENCY - 1)) {
                    frequencyToShow = "5 times and more";
                    toggleQuestion3c(View.VISIBLE);
                }
                else {
                    frequencyToShow = i + 1 + " times";
                    toggleQuestion3c(View.VISIBLE);
                }
                question3bFrequency.setText(frequencyToShow); // to set seekbar's minimum, simply change its maximum to maximum-minimum. Then in code, simply progress + minimum.
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        lastOccurrenceDate = Calendar.getInstance();    // gets today's date
        lastOccurrence.setText(new SimpleDateFormat("dd / MM / yyyy").format(lastOccurrenceDate.getTime()));
        findViewById(R.id.calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
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
                double componentResult = generateComponentResult();
                Constants.componentResults[1] = componentResult;
                Log.d("Frequency strength", String.valueOf(componentResult));
                Intent intent = new Intent(DateFrequencyActivity.this, AggressorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        lastOccurrenceDate.set(Calendar.YEAR, i);
        lastOccurrenceDate.set(Calendar.MONTH, i1);
        lastOccurrenceDate.set(Calendar.DAY_OF_MONTH, i2);
        lastOccurrence.setText(new SimpleDateFormat("dd / MM / yyyy").format(lastOccurrenceDate.getTime()));
    }

    private void toggleQuestion3c(int visibility) {
        question3c.setVisibility(visibility);
        question3cMessage.setVisibility(visibility);
        question3cSpinner.setVisibility(visibility);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        if (visibility == View.VISIBLE) {
            constraintSet.connect(R.id.back_next_layout,ConstraintSet.TOP,R.id.three_3c_message,ConstraintSet.BOTTOM,24);
        } else {
            constraintSet.connect(R.id.back_next_layout,ConstraintSet.TOP,R.id.three_3b_frequency,ConstraintSet.BOTTOM,24);
        }
        constraintSet.applyTo(constraintLayout);
    }

    private double generateComponentResult() {

    }
}
