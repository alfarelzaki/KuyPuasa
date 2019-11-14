package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.moslemdev.kuypuasa.ClickCalendar;
import com.moslemdev.kuypuasa.MainActivity;
import com.moslemdev.kuypuasa.R;

import com.marcohc.robotocalendar.RobotoCalendarView;
import com.marcohc.robotocalendar.RobotoCalendarView.RobotoCalendarListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DashboardFragment extends Fragment implements RobotoCalendarListener{

    private RobotoCalendarView robotoCalendarView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        robotoCalendarView = root.findViewById(R.id.roboto_calendar_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        robotoCalendarView.setRobotoCalendarListener(DashboardFragment.this);
    }

    @Override
    public void onDayClick(Date date) {
        robotoCalendarView.markCircleImage2(date);
    }

    @Override
    public void onDayLongClick(Date date) {
        Calendar calendar = Calendar.getInstance(); // membuat objek berupa calendar
        calendar.setTime(date);

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM"); // membuat format.
        String month_name = month_date.format(calendar.getTime()); // return nama bulan sebagai string
        int gregorianDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int gregorianMonth = calendar.get(Calendar.MONTH);
        int gregorianYear = calendar.get(Calendar.YEAR);
        String gregorianDate = gregorianDayOfMonth + " " + month_name + " " + gregorianYear;

        GregorianCalendar gCal = new GregorianCalendar(gregorianYear, gregorianMonth+1, gregorianDayOfMonth);
        String islamicDate = getIslamicDate(gCal);

        Intent detailTanggal = new Intent(getActivity(), ClickCalendar.class);
        detailTanggal.putExtra("gregorian date", gregorianDate);
        detailTanggal.putExtra("islamic date", islamicDate);
        startActivity(detailTanggal);
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

    @Override
    public void onRightButtonClick() {

    }

    @Override
    public void onLeftButtonClick() {

    }
}