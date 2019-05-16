package ericstephens.planner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddItemFragment extends Fragment {

    EditText title;
    Button startDate;
    Button endDate;
    Spinner startTime;
    Spinner startAmPm;
    Spinner endTime;
    Spinner endAmPm;
    EditText notes;
    Button save;
    DatePickerDialog startDialog;
    Calendar calendar;
    int month;
    int day;
    int year;
    DatePickerDialog endDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_event_fragment, container, false);

        title = rootView.findViewById(R.id.title);
        startDate = rootView.findViewById(R.id.startButton);
        endDate = rootView.findViewById(R.id.endButton);
        startTime = rootView.findViewById(R.id.startSpinner);
        startAmPm = rootView.findViewById(R.id.startAmPm);
        endTime = rootView.findViewById(R.id.endSpinner);
        endAmPm = rootView.findViewById(R.id.endAmPm);
        notes = rootView.findViewById(R.id.notes);
        save = rootView.findViewById(R.id.save);

        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);

        startDate.setOnClickListener(onStartClick);
        endDate.setOnClickListener(onEndClick);

        return rootView;

    }

    private View.OnClickListener onStartClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startDialog = new DatePickerDialog(getContext(), startDatePickerListener, year, month, day );
            startDialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, selectedYear);
            c.set(Calendar.MONTH, selectedMonth);
            c.set(Calendar.DAY_OF_MONTH, selectedDay);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

            startDate.setText(sdf.format(c.getTime()));

        }
    };

    private View.OnClickListener onEndClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            endDialog = new DatePickerDialog(getContext(), endDatePickerListener, year, month, day );
            endDialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, selectedYear);
            c.set(Calendar.MONTH, selectedMonth);
            c.set(Calendar.DAY_OF_MONTH, selectedDay);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            endDate.setText(sdf.format(c.getTime()));

        }
    };

}
