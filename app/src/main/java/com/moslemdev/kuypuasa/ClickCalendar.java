package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ClickCalendar extends AppCompatActivity {

    private TextView tvGregorianDate, tvIslamicDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_calendar);

        getSupportActionBar().hide();
        tvGregorianDate = findViewById(R.id.gregorian_date);
        tvIslamicDate = findViewById(R.id.islamic_date);

        Intent detailTanggal = getIntent();
        if (detailTanggal.hasExtra("gregorian date") && detailTanggal.hasExtra("islamic date")) {
            String gregorianDate = detailTanggal.getStringExtra("gregorian date");
            String islamicDate = detailTanggal.getStringExtra("islamic date");
            tvGregorianDate.setText(gregorianDate);
            tvIslamicDate.setText(islamicDate);
        }
    }
}
