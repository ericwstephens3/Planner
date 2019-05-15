package ericstephens.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    CalendarView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);

        //calendar = rootView.findViewById(R.id.calendarView);

        return rootView;

    }

}
