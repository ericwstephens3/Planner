package ericstephens.planner;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarAdapter extends BaseAdapter {
    // for view inflation
    private LayoutInflater inflater;
    private HashSet<Date> eventDays;

    private ArrayList<Date> days;
    private Context context;
    public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
    {
        this.eventDays = eventDays;
        this.days = days;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Date getItem(int i) {
        return days.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        View v;
        TextView num;
        ImageView notification;
        // day in question
        Calendar calendar = Calendar.getInstance();
        Date date = getItem(position);
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // today
        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        // inflate item if it does not exist yet
        if (view == null) {
            v = inflater.inflate(R.layout.calendar_item, null);
            num  = v.findViewById(R.id.DayTextView);
            notification = v.findViewById(R.id.notif);
            if (month != calendarToday.get(Calendar.MONTH) || year != calendarToday.get(Calendar.YEAR)) {
                // if this day is outside current month, grey it out
                num.setTextColor(Color.GRAY);
            } else if (day == calendarToday.get(Calendar.DATE)) {
                // if it is today, set it to blue/bold
                num.setTextColor(Color.GREEN);
                v.setBackground(context.getDrawable(R.drawable.grid_border_selected));
            }

            // set text
            num.setText(String.valueOf(calendar.get(Calendar.DATE)));
        }
        else
            return view;

        return v;
    }
}


