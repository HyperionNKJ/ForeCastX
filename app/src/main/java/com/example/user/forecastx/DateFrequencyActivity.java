package com.example.user.forecastx;

import android.app.DatePickerDialog;
import android.provider.CalendarContract;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFrequencyActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView lastOccurrence;
    private Calendar lastOccurrenceDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_frequency);

        lastOccurrence = findViewById(R.id.lastOccurrence);
        lastOccurrenceDate = Calendar.getInstance();    // gets today's date
        lastOccurrence.setText(new SimpleDateFormat("dd / MM / yyyy").format(lastOccurrenceDate.getTime()));
        findViewById(R.id.calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
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
}
