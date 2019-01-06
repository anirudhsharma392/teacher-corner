package com.example.anirudhsharma392.teachercorner;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.graphics.Color.RED;

public class Events extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM-yyyy  ", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionbar= getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setTitle(null);

        compactCalendar=(CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        //set event for teacher

        Event ev1 =new Event(Color.GREEN, 1532078380000L,"Teachers Professional Day");
        compactCalendar.addEvent(ev1);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                if(dateClicked.toString().compareTo("Fri Jul 20 2018 09:00:00 IST 2018") ==0)
                {
                    Toast.makeText(context, "Teachers Professional Day", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "No Event planned for that Day", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionbar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        Event ev2 =new Event(Color.GREEN, 1530436780000L,"Seminar");
        compactCalendar.addEvent(ev2);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context=getApplicationContext();

                if(dateClicked.toString().compareTo("Sun Jul 1 09:00:00 IST 2018") ==0)
                {
                    Toast.makeText(context, "Seminar", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "No Event planned for that Day", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionbar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        Event ev3=new Event(Color.GREEN, 1533028780000L,"Visionary Conference");
        compactCalendar.addEvent(ev3);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();

                if(dateClicked.toString().compareTo("Tue Jul 31 2018 09:00:00 IST 2018") ==0)
                {
                    Toast.makeText(context, "Visionary Conference", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "No Event planned for that Day", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                actionbar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }
}
//https://youtu.be/xs5406vApTo
