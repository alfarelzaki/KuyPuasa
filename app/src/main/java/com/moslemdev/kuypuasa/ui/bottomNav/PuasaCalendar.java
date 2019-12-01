package com.moslemdev.kuypuasa.ui.bottomNav;

import android.util.Log;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.moslemdev.kuypuasa.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PuasaCalendar {
    public static CalendarView materialCalendar;

    // membuat instance kalender untuk menyimpan waktu event (puasa)
    static Calendar puasa = Calendar.getInstance();

    // date as day
    static Date senin = new Date(17903);
    static Date kamis = new Date(17899);

    // dalam satu minggu terdapat 7 hari
    static long oneWeek = 7;

    // detik dalam sehari
    static long oneDay = 86400;

    // membuat array untuk menyimpan event
    static List<EventDay> events = new ArrayList<>();

    public static void setPuasaKamis() {
        for (int i=0; i<105; i++) {
            puasa = Calendar.getInstance();
            puasa.setTimeInMillis((kamis.getTime() + oneWeek*i)*oneDay*1000);
            Log.d("Tanggal puasa", puasa.toString());
            events.add(new EventDay(puasa, R.drawable.mark_puasa_senin_kamis));
        }
    }

    public static void setPuasaSenin() {
        for (int i=0; i<105; i++) {
            puasa = Calendar.getInstance();
            puasa.setTimeInMillis((senin.getTime() + oneWeek*i)*oneDay*1000);
            Log.d("Tanggal puasa", puasa.toString());
            events.add(new EventDay(puasa, R.drawable.mark_puasa_senin_kamis));
        }

    }
}
