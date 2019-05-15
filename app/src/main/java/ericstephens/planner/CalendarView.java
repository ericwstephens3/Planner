package ericstephens.planner;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarView extends LinearLayout {
    // calendar components
    LinearLayout header;
    Button btnToday;
    ImageView btnPrev;
    ImageView btnNext;
    TextView txtDateDay;
    TextView txtDisplayDate;
    TextView txtDateYear;
    GridView gridView;
    HashSet<Date> event;
    ArrayList<Date> cells = new ArrayList<>();
    int currentMonth = 0; //the current month being displayed as an integer
    CalendarAdapter adapter;
    Calendar calToSend;
    Handler handler = new Handler();



    public CalendarView(Context context, AttributeSet set) {
        super(context, set);

        initControl(context);
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        header = findViewById(R.id.calendar_header);
        btnPrev = findViewById(R.id.calendar_prev_button);
        btnNext = findViewById(R.id.calendar_next_button);
        txtDateDay = findViewById(R.id.date_display_day);
        txtDateYear = findViewById(R.id.date_display_year);
        txtDisplayDate = findViewById(R.id.date_display_date);
        btnToday = findViewById(R.id.date_display_today);
        gridView = findViewById(R.id.calendar_grid);

        btnToday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCalendar(null, 0);
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCalendar(null, 1);
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCalendar(null, -1);
            }
        });
    }

    public void updateCalendar(HashSet<Date> events, int month){

        cells.clear();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        if (month == 0){
            currentMonth = 0;
        }
        else{
            currentMonth = currentMonth + month;
            Log.d("DEBUG", Integer.toString(currentMonth));
            calendar.add(Calendar.MONTH, currentMonth);

        }

        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        // fill cells
        while (cells.size() < 42)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        //TODO Make updateDataSetChanged work somehow. This is too messy
        calToSend = calendar;
        adapter = new CalendarAdapter(getContext(), cells, event, calToSend, currentMonth);
        gridView.setAdapter(adapter);

        if (month != 0) {
            calendar.add(Calendar.MONTH, -1);
            while (calendar.get(Calendar.DAY_OF_MONTH) > 1) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }

        }
        if(month == 0 || currentMonth == 0){
            calendar.setTime(currentDate);
        }
        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        txtDateYear.setText(sdf.format(calendar.getTime()));

        sdf = new SimpleDateFormat("MMM dd");
        txtDisplayDate.setText(sdf.format(calendar.getTime()));


        Log.d("DEBUG", calendar.getTime().toString());
        sdf = new SimpleDateFormat("EEEE");
        txtDateDay.setText(sdf.format(calendar.getTime()));

    }

    public void updateCalendar(HashSet<Date> events)
    {
        cells.clear();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < 42)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.setTime(currentDate);
        calToSend = calendar;
        adapter = new CalendarAdapter(getContext(), cells, events, calToSend, currentMonth);
        gridView.setAdapter(adapter);
        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        txtDateYear.setText(sdf.format(calendar.getTime()));

        sdf = new SimpleDateFormat("MMM dd");
        txtDisplayDate.setText(sdf.format(calendar.getTime()));

        sdf = new SimpleDateFormat("EEEE");
        txtDateDay.setText(sdf.format(calendar.getTime()));
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar, this);
        assignUiElements();
        updateCalendar(event);
    }

}


