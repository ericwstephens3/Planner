package ericstephens.planner;

import android.content.Context;
import android.view.LayoutInflater;
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

    public CalendarView(Context context, HashSet<Date> events) {
        super(context);
        this.event = events;
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
    }

    private void updateCalendar(HashSet<Date> events)
    {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells (42 days calendar as per our business logic)
        while (cells.size() < 42)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        gridView.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update grid
        ((CalendarAdapter)gridView.getAdapter()).notifyDataSetChanged();

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


