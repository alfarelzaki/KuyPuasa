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

    }

    @Override
    public void onDayLongClick(Date date) {

    }

    @Override
    public void onRightButtonClick() {

    }

    @Override
    public void onLeftButtonClick() {

    }
}