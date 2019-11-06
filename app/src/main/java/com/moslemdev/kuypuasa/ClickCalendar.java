package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ClickCalendar extends AppCompatActivity {

    private TextView tvGregorianDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_calendar);

        getSupportActionBar().hide();

        Intent i = getIntent();
        tvGregorianDate = findViewById(R.id.gregorian_date);
    }
}
