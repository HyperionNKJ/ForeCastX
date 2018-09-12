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
import android.widget.ArrayAdapter;
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
import java.util.concurrent.TimeUnit;

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
    private StringBuilder savedString;
    private StringBuilder editString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_frequency);

        savedString = new StringBuilder(Constants.systemMessage);
        editString = new StringBuilder(savedString);

        TextView title = findViewById(R.id.date_and_frequency_title);
        question3b = findViewById(R.id.three_3b);
        question3bFrequency = findViewById(R.id.three_3b_frequency);
        question3bSeekbar = findViewById(R.id.three_3b_seekBar);
        question3c = findViewById(R.id.three_3c);
        question3cMessage = findViewById(R.id.three_3c_message);
        question3cSpinner = findViewById(R.id.three_3c_spinner);
        lastOccurrence = findViewById(R.id.lastOccurrence);
        constraintLayout = findViewById(R.id.constraint_layout);

        if (!Constants.isCdra) {
            title.setText("Step 2/8: Date and Frequency of Occurrence");
            question3b.setText("2b) How many times were you harassed?:");
            question3bSeekbar.setMax(Constants.NUM_OF_OPTIONS_FOR_POHA_FREQUENCY - 1);
            question3c.setText("2c) On average, what was the frequency of harassment?");
            question3cMessage.setText("I was harassed once every ");
            ArrayAdapter<CharSequence> adapterForCell = ArrayAdapter.createFromResource(this,
                    R.array.poha_interval, R.layout.dropdown_item);
            adapterForCell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            question3cSpinner.setAdapter(adapterForCell);
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
                Log.d("DateFrequency strength", String.valueOf(componentResult));

                if ((!Constants.isCdra && Constants.isBefore2014) || Constants.isOver2YearsAgo || Constants.hasLowFrequency) {
                    editString.append("<h6><u>Date and Frequency of Occurrence</u></h6>");
                }
                if (!Constants.isCdra && Constants.isBefore2014) {
                    editString.append(getString(R.string.time_before_2014));
                }
                if (Constants.isOver2YearsAgo) {
                    editString.append(getString(R.string.time_last_occurence));
                }
                if (Constants.hasLowFrequency) {
                    editString.append(getString(R.string.time_low_frequency));
                }

                Intent intent;
                if (Constants.isCdra) {  // if CDRA, then no need for aggressor component. Jump straight to payment component.
                    intent = new Intent(DateFrequencyActivity.this, PaymentActivity.class);
                } else {
                    intent = new Intent(DateFrequencyActivity.this, AggressorActivity.class);
                }
                Constants.systemMessage = new StringBuilder(editString);
                editString = new StringBuilder(savedString);
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
            constraintSet.connect(R.id.back_next_layout,ConstraintSet.TOP,R.id.three_3c_layout,ConstraintSet.BOTTOM,48);
        } else {
            constraintSet.connect(R.id.back_next_layout,ConstraintSet.TOP,R.id.three_3b_frequency,ConstraintSet.BOTTOM,24);
        }
        constraintSet.applyTo(constraintLayout);
    }

    private double generateComponentResult() {
        // calculate num of days between now and last occurrence
        long diffInMillies = Calendar.getInstance().getTimeInMillis() - lastOccurrenceDate.getTimeInMillis();
        long numOfDaysAgo = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        // checks if last occurrence date is 2 years ago
        Constants.isOver2YearsAgo = numOfDaysAgo > 730;

        // checks if last occurrence date is before POHA's effective date of 2014
        Constants.isBefore2014 = lastOccurrenceDate.get(Calendar.YEAR) < 2014;

        // applies linear formula f(x) = -100/730 * x + 100 to find last occurrence's weight. f(x) = weight, x = num of days ago. Assumes f(0) = 100, f(2 years) = 0
        double fx = (-100 / 730.0) * numOfDaysAgo + 100;
        double ageWeight = (fx < 0) ? 0 : fx;

        // gets the multiplier for num of time and frequency
        int seekbarProgress = question3bSeekbar.getProgress();
        int spinnerProgress = question3cSpinner.getSelectedItemPosition();
        Constants.hasLowFrequency = Constants.isCdra ? spinnerProgress > 3 : spinnerProgress > 2;

        double numOfTimeMultiplier = (Constants.isCdra) ? Constants.CDRA_NUM_OF_TIME_MULTIPLIER[seekbarProgress] : Constants.POHA_NUM_OF_TIME_MULTIPLIER[seekbarProgress];
        double frequencyMultiplier;
        if (seekbarProgress == 0) {
            frequencyMultiplier = 1.0;
        } else {
            frequencyMultiplier =  (Constants.isCdra) ? Constants.CDRA_FREQUENCY_MULTIPLIER[spinnerProgress] : Constants.POHA_FREQUENCY_MULTIPLIER[spinnerProgress];
        }
        Log.d("f multiplier", String.valueOf(frequencyMultiplier));
        double finalDateFrequencyStrength = ageWeight * numOfTimeMultiplier * frequencyMultiplier;
        return (finalDateFrequencyStrength > 100) ? 100 : finalDateFrequencyStrength;
    }
}
