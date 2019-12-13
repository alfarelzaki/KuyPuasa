package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.gson.Gson;
import com.moslemdev.kuypuasa.Animation;
import com.moslemdev.kuypuasa.ClickCalendar;
import com.moslemdev.kuypuasa.DataPuasa;
import com.moslemdev.kuypuasa.DatabaseHelper;
import com.moslemdev.kuypuasa.LandingPage;
import com.moslemdev.kuypuasa.ListPuasaAdapter;
import com.moslemdev.kuypuasa.Puasa;
import com.moslemdev.kuypuasa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KalenderFragment extends Fragment{
    public static CalendarView materialCalendar;
    private RecyclerView puasaRecyclerView;
    private ListPuasaAdapter listPuasaAdapter;
    public ArrayList<Puasa> listPuasa;
    DatabaseHelper db;

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
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        materialCalendar = root.findViewById(R.id.material_calendar_view);

        db = new DatabaseHelper(getActivity());

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
            GregorianCalendar gCal = new GregorianCalendar(gregorianYear, gregorianMonth, gregorianDayOfMonth);
            String islamicDate = getIslamicDate(gCal);


            // menyimpan date hari klik
            Date date = new Date(calendar.getTimeInMillis());

            // mengirim data hari ini ke activity pop up
            Intent detailTanggal = new Intent(getActivity(), ClickCalendar.class);
            detailTanggal.putExtra("gregorian date", gregorianDate);
            detailTanggal.putExtra("islamic date", islamicDate);
            detailTanggal.putExtra("time in milis", Long.valueOf(calendar.getTimeInMillis()));
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

        // jika inisialisasi pertama, maka buat database terlebih dahulu
        if (!initializeCalendarData()) {
            haramPuasaSaveToDatabase();
            puasaRamadhanSavetoDatabase();
            puasaArafahSaveToDatabase();
            puasaAsyuraSaveToDatabase();
            puasaAyyamulBidhSaveToDatabase();
            puasaSeninSaveToDatabase();
            puasaKamisSaveToDatabase();

            // save state (intinya calendar data sudah pernah di inisialisasi
            SharedPreferences sharedPreferences =
                    getActivity().getSharedPreferences("DataUser", getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userCalendar", "initialized");
            editor.commit();
        }

        setPuasaColor();
        materialCalendar.setEvents(events);

        return root;
    }

    private boolean initializeCalendarData() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences("DataUser", getActivity().MODE_PRIVATE);
        if (!sharedPreferences.contains("userCalendar")) {
            return false;
        }
        return true;
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

    private String getIslamicDate(GregorianCalendar gCal) {
        UmmalquraCalendar islamicCal = new UmmalquraCalendar();
        islamicCal.setTime(gCal.getTime()); // mengeset tanggal islam berdasarkan tanggal gregorian

        int uYear = islamicCal.get(Calendar.YEAR);
        String uMonth = islamicCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH); // membuat tanggal islam
        int uDay = islamicCal.get(Calendar.DAY_OF_MONTH);
        String islamicDate = uDay + " " + uMonth + " " + uYear;

        return islamicDate;
    }

    private void setPuasaColor() {
        ArrayList<Map<String, String>> arrayList;
        arrayList = db.getAllPuasa();
        for (int i=0; i<arrayList.size(); i++) {
            puasa = Calendar.getInstance();
            puasa.setTimeInMillis(Long.parseLong(arrayList.get(i).get("time"))*oneDay*1000);
            switch (arrayList.get(i).get("name")) {
                case "Haram Berpuasa":
                    events.add(new EventDay(puasa, R.drawable.mark_haram_berpuasa));
                    break;
                case "Puasa Ramadhan":
                    events.add(new EventDay(puasa, R.drawable.mark_puasa_ramadhan));
                    break;
                case "Puasa Arafah":
                    events.add(new EventDay(puasa, R.drawable.mark_puasa_arafah));
                    break;
                case "Puasa Asyura Tasu'a":
                    events.add(new EventDay(puasa, R.drawable.mark_puasa_asyura_tasua));
                    break;
                case "Puasa Ayyamul Bidh":
                    events.add(new EventDay(puasa, R.drawable.mark_puasa_ayyamul_bidh));
                    break;
                case "Puasa Senin Kamis":
                    events.add(new EventDay(puasa, R.drawable.mark_puasa_senin_kamis));
                    break;
            }
        }
    }

    private void puasaAsyuraSaveToDatabase() {
        long time = (Long.valueOf(18503));
        db.addPuasa("Puasa Asyura Tasu'a", time);
    }

    private void puasaArafahSaveToDatabase() {
        long time = (Long.valueOf(18473));
        db.addPuasa("Puasa Arafah", time);
    }

    private void puasaRamadhanSavetoDatabase() {
        for (int i=0; i<30; i++) {
            long time = (Long.valueOf(18376)+i);
            db.addPuasa("Puasa Ramadhan", time);
        }
    }

    private void haramPuasaSaveToDatabase() {
        for (int i=0; i<3; i++) {
            long time = (Long.valueOf(18475)+i);
            db.addPuasa("Haram Berpuasa", time);
        }
    }

    private void puasaKamisSaveToDatabase() {
        for (int i=0; i<105; i++) {
            long time = (kamis.getTime() + oneWeek*i);
            db.addPuasa("Puasa Senin Kamis", time);
        }
    }

    private void puasaSeninSaveToDatabase() {
        for (int i=0; i<105; i++) {
            long time = (senin.getTime() + oneWeek*i);
            db.addPuasa("Puasa Senin Kamis", time);
        }
    }

    private void puasaAyyamulBidhSaveToDatabase() {
        ArrayList<Long> listPuasaAyyamulBidh = new ArrayList<>();
        listPuasaAyyamulBidh.add(Long.valueOf(18270));
        listPuasaAyyamulBidh.add(Long.valueOf(18299));
        listPuasaAyyamulBidh.add(Long.valueOf(18329));
        listPuasaAyyamulBidh.add(Long.valueOf(18359));
        listPuasaAyyamulBidh.add(Long.valueOf(18418));
        listPuasaAyyamulBidh.add(Long.valueOf(18448));
        listPuasaAyyamulBidh.add(Long.valueOf(18478));
        listPuasaAyyamulBidh.add(Long.valueOf(18506));
        listPuasaAyyamulBidh.add(Long.valueOf(18536));
        listPuasaAyyamulBidh.add(Long.valueOf(18565));
        listPuasaAyyamulBidh.add(Long.valueOf(18595));
        listPuasaAyyamulBidh.add(Long.valueOf(18624));

        for (int i=0; i<listPuasaAyyamulBidh.size(); i++) {
            // puasa ayyamul bidh biasanya berlangsung 3 hari
            for (int j=0; j<3; j++) {
                long time = (listPuasaAyyamulBidh.get(i)+j);
                db.addPuasa("Puasa Ayyamul Bidh", time);
            }
        }
    }
}