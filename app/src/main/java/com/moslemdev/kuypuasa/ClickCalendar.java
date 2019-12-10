package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;

public class ClickCalendar extends AppCompatActivity {

    private TextView tvGregorianDate, tvIslamicDate;
    FloatingActionButton setAlarmFab;
    DatabaseHelper db = new DatabaseHelper(this);

    private RecyclerView puasaRecyclerView;
    private ListPuasaAdapter listPuasaAdapter;
    public ArrayList<Puasa> listPuasa;
    public ArrayList<Puasa> listPuasaInSpesificDay;
    ArrayList<Map<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_calendar);

        puasaRecyclerView = findViewById(R.id.recycler_puasa_click);
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
        if (detailTanggal.hasExtra("gregorian date") && detailTanggal.hasExtra("islamic date")
                && detailTanggal.hasExtra("time in milis")) {
            String gregorianDate = detailTanggal.getStringExtra("gregorian date");
            String islamicDate = detailTanggal.getStringExtra("islamic date");
            Long time = detailTanggal.getLongExtra("time in milis", 0)/(1000*86400)+1;

            arrayList = db.getPuasaInSpesificDay(time.toString());
            Log.d("waktu", String.valueOf(arrayList.size()));

            loadDataPuasaInSpesificDay();
            createRecyclerView();

            tvGregorianDate.setText(gregorianDate);
            tvIslamicDate.setText(islamicDate);
        }
    }

    private void loadDataPuasaInSpesificDay() {
        listPuasa = new ArrayList<>();
        listPuasaInSpesificDay = new ArrayList<>();
        listPuasa.addAll(DataPuasa.getListData());

        for (int i=0; i<arrayList.size(); i++) {
            for (int j=0; j<listPuasa.size(); j++) {
                if (arrayList.get(i).get("name").toString().equals(listPuasa.get(j).getPuasa().toString())){
                    listPuasaInSpesificDay.add(listPuasa.get(j));
                    continue;
                }
            }
        }
    }

    private void createRecyclerView() {
        listPuasaAdapter = new ListPuasaAdapter(this, listPuasaInSpesificDay);
        puasaRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));
        puasaRecyclerView.setAdapter(listPuasaAdapter);
    }
}
