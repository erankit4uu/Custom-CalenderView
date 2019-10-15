package com.ankit.customcalendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomCalAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;
    Context context;

    public CustomCalAdapter(@NonNull Context context,List<Date> dates, Calendar currentDate, List<Events> events) {
        super(context, R.layout.item_cal_cell);
        this.context = context;
        this.dates = dates;
        this.currentDate = currentDate;
        this.events = events;
        inflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthdate = dates.get(position);
        View view = convertView;
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthdate);
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        if (view == null){
            view = inflater.inflate(R.layout.item_cal_cell, parent, false);
        }
        if (displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        }else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        TextView day = view.findViewById(R.id.cal_day);
        day.setText(String.valueOf(dayNo));
        TextView event = view.findViewById(R.id.event_id);
        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < events.size(); i++){
            eventCalendar.setTime(convertStringToDate(events.get(i).DATE));
            if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH)+1
            && displayYear == eventCalendar.get(Calendar.YEAR)){
                arrayList.add(events.get(i).EVENT);
                event.setText(".");
            }
        }

        return view;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    private Date convertStringToDate(String date){
        Date date1 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date1 = format.parse(date);
            System.out.println(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1;
    }

}
