package com.ankit.customcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomCalendarView extends LinearLayout {
    ImageButton nextButton, previousButton;
    TextView currentDate;
    GridView calGrid;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    CustomCalAdapter adapter;


    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/YYYY", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY", Locale.ENGLISH);

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createEvents();
        initializeView();
        setUpCalendar();


        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                setUpCalendar();
            }
        });
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                setUpCalendar();
            }
        });
    }


    private void initializeView(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_fragment, this);

        nextButton = view.findViewById(R.id.ib_next);
        previousButton = view.findViewById(R.id.ib_prev);
        currentDate = view.findViewById(R.id.tv_date);
        calGrid = view.findViewById(R.id.cal_grid);
    }


    private void setUpCalendar(){
        String date = simpleDateFormat.format(calendar.getTime());
        currentDate.setText(date);
        dates.clear();

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        int FIRST_DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        monthCalendar.add(Calendar.DAY_OF_MONTH, -FIRST_DAY_OF_MONTH);

        while (dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);

        }

        adapter = new CustomCalAdapter(context, dates, calendar, eventsList);
        calGrid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void createEvents(){
        Events events = new Events("my bday", "09:30", "2019-10-15","oct","2019");
        eventsList.add(events);

        events = new Events("my bday", "09:30", "2019-10-20","oct","2019");
        eventsList.add(events);
    }
}
