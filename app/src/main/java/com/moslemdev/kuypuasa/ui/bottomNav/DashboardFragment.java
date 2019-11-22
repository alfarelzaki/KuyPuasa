package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.moslemdev.kuypuasa.ClickCalendar;
import com.moslemdev.kuypuasa.DataPuasa;
import com.moslemdev.kuypuasa.ListPuasaAdapter;
import com.moslemdev.kuypuasa.MainActivity;
import com.moslemdev.kuypuasa.Puasa;
import com.moslemdev.kuypuasa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class DashboardFragment extends Fragment{

    private CalendarView materialCalendar;
    private RecyclerView puasaRecyclerView;
    private ListPuasaAdapter listPuasaAdapter;
    public ArrayList<Puasa> listPuasa;


    // membuat objek berupa calendar
    Calendar calendar = Calendar.getInstance();

    // membuat instance kalender untuk menyimpan waktu event (puasa)
    Calendar puasa = Calendar.getInstance();

    // membuat array untuk menyimpan event
    List<EventDay> events = new ArrayList<>();

    // date as day
    Date senin = new Date(17903);
    Date kamis = new Date(17899);

    // dalam satu minggu terdapat 7 hari
    long oneWeek = 7;

    // detik dalam sehari
    long oneDay = 86400;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        materialCalendar = root.findViewById(R.id.material_calendar_view);

        // membuat recycler view puasa
        puasaRecyclerView = root.findViewById(R.id.recycler_puasa);
        loadDataPuasa();
        createRecyclerView();

        materialCalendar.setOnDayClickListener(eventDay -> {
            calendar.setTime(eventDay.getCalendar().getTime());

            // membuat format untuk mengambil nama bulan
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            String month_name = month_date.format(calendar.getTime());

            // membuat date dalam tanggal gregorian
            int gregorianDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int gregorianMonth = calendar.get(Calendar.MONTH);
            int gregorianYear = calendar.get(Calendar.YEAR);
            String gregorianDate = gregorianDayOfMonth + " " + month_name + " " + gregorianYear;

            // membuat instance gregorian calendar untuk kemudian diconvert ke islamic
            GregorianCalendar gCal = new GregorianCalendar(gregorianYear, gregorianMonth+1, gregorianDayOfMonth);
            String islamicDate = getIslamicDate(gCal);

            // menyimpan date hari klik
            Date date = new Date(calendar.getTimeInMillis());

            // mengirim data hari ini ke activity pop up
            Intent detailTanggal = new Intent(getActivity(), ClickCalendar.class);
            detailTanggal.putExtra("gregorian date", gregorianDate);
            detailTanggal.putExtra("islamic date", islamicDate);
            Log.d("date", String.valueOf(calendar.getTimeInMillis()));
            startActivity(detailTanggal);
        });

        // mengeset batas awal kalender
        Calendar min = Calendar.getInstance();
        min.set(2018, 11, 31);
        materialCalendar.setMinimumDate(min);

        // mengeset batas akhir kalender
        Calendar max = Calendar.getInstance();
        max.set(2020, 11, 31);
        materialCalendar.setMaximumDate(max);

        return root;
    }

    private void createRecyclerView() {
        listPuasaAdapter = new ListPuasaAdapter(getActivity(), listPuasa);
        puasaRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        puasaRecyclerView.setAdapter(listPuasaAdapter);
    }

    private void loadDataPuasa() {
        listPuasa = new ArrayList<>();
        listPuasa.addAll(DataPuasa.getListData());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPuasaSenin();
        setPuasaKamis();
        materialCalendar.setEvents(events);
    }

    private void setPuasaKamis() {
        for (int i=0; i<105; i++) {
            puasa = Calendar.getInstance();
            puasa.setTimeInMillis((kamis.getTime() + oneWeek*i)*oneDay*1000);
            Log.d("Tanggal puasa", puasa.toString());
            events.add(new EventDay(puasa, R.drawable.mark_puasa_senin_kamis));
        }
    }

    private void setPuasaSenin() {
        for (int i=0; i<105; i++) {
            puasa = Calendar.getInstance();
            puasa.setTimeInMillis((senin.getTime() + oneWeek*i)*oneDay*1000);
            Log.d("Tanggal puasa", puasa.toString());
            events.add(new EventDay(puasa, R.drawable.mark_puasa_senin_kamis));
        }

    }

    private String getIslamicDate(GregorianCalendar gCal) {
        UmmalquraCalendar islamicCal = new UmmalquraCalendar();
        islamicCal.setTime(gCal.getTime()); // mengeset tanggal islam berdasarkan tanggal gregorian

        int uYear = islamicCal.get(Calendar.YEAR);
        String uMonth = islamicCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH); // membuat tanggal islam
        int uDay = islamicCal.get(Calendar.DAY_OF_MONTH);
        String islamicDate = uDay + " " + uMonth + " " + uYear;

        return islamicDate;
    }
}