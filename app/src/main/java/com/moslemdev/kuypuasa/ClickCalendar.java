package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClickCalendar extends AppCompatActivity {

    private TextView tvGregorianDate, tvIslamicDate;
    FloatingActionButton setAlarmFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_calendar);

        tvGregorianDate = findViewById(R.id.gregorian_date);
        tvIslamicDate = findViewById(R.id.islamic_date);
        setAlarmFab = findViewById(R.id.floating_action_button_alarm);

        setAlarmFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Kuy Puasa");
                intent.putExtra(AlarmClock.EXTRA_HOUR, 20);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 0);
                startActivity(intent);
            }
        });

        Intent detailTanggal = getIntent();
        if (detailTanggal.hasExtra("gregorian date") && detailTanggal.hasExtra("islamic date")) {
            String gregorianDate = detailTanggal.getStringExtra("gregorian date");
            String islamicDate = detailTanggal.getStringExtra("islamic date");
            tvGregorianDate.setText(gregorianDate);
            tvIslamicDate.setText(islamicDate);
        }
    }
}
