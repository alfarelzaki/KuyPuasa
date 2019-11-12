package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.moslemdev.kuypuasa.ClickCalendar;
import com.moslemdev.kuypuasa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DashboardFragment extends Fragment {

    private CalendarView calendarView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        calendarView = root.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Calendar calendar = Calendar.getInstance(); // membuat objek berupa calendar
                calendar.set(year, month, dayOfMonth); // untuk mengeset calendar sesuai dengan date yang diklik

                SimpleDateFormat month_date = new SimpleDateFormat("MMMM"); // membuat format.
                String month_name = month_date.format(calendar.getTime()); // return nama bulan sebagai string
                String gregorianDate = dayOfMonth + " " + month_name + " " + year;

                GregorianCalendar gCal = new GregorianCalendar(year, month+1, dayOfMonth);
                UmmalquraCalendar islamicCal = new UmmalquraCalendar();
                islamicCal.setTime(gCal.getTime()); // mengeset tanggal islam berdasarkan tanggal gregorian

                int uYear = islamicCal.get(Calendar.YEAR);
                String uMonth = islamicCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH); // membuat tanggal islam
                int uDay = islamicCal.get(Calendar.DAY_OF_MONTH);
                String islamicDate = uDay + " " + uMonth + " " + uYear;

                Intent detailTanggal = new Intent(getContext(), ClickCalendar.class);
                detailTanggal.putExtra("gregorian date", gregorianDate);
                detailTanggal.putExtra("islamic date", islamicDate);
                startActivity(detailTanggal);
            }
        });
        return root;

    }




}