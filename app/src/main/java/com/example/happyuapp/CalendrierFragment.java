package com.example.happyuapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CalendrierFragment extends Fragment {

    private CalendarView calendarView;
    private EditText eventEditText;
    private Button addEventButton;
    private TextView eventTextView;
    private TextView allEventsTextView;

    private HashMap<Long, String> eventsMap;
    private List<Event> allEventsList;
    private long selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        eventEditText = view.findViewById(R.id.eventEditText);
        addEventButton = view.findViewById(R.id.addEventButton);
        eventTextView = view.findViewById(R.id.eventTextView);
        allEventsTextView = view.findViewById(R.id.allEventsTextView);

        eventsMap = new HashMap<>();
        allEventsList = new ArrayList<>();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = getDateInMillis(year, month, dayOfMonth);
                Event event = findEventByDate(selectedDate);
                if (event != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = sdf.format(new Date(event.getDateInMillis()));
                    eventTextView.setText("Événement pour le " + formattedDate + " : " + event.getEventName());
                } else {
                    eventTextView.setText("Pas d'événement pour la date sélectionnée");
                }
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventEditText.getText().toString().trim();

                if (!eventName.isEmpty()) {
                    Event newEvent = new Event(selectedDate, eventName);
                    eventsMap.put(selectedDate, String.valueOf(newEvent));
                    allEventsList.add(newEvent);
                    eventEditText.setText("");
                    eventTextView.setText("Événement ajouté pour le " + getFormattedDate(selectedDate));
                    updateAllEventsTextView();
                } else {
                    eventTextView.setText("Veuillez entrer un événement");
                }
            }
        });

        updateAllEventsTextView();

        return view;
    }

    private void updateAllEventsTextView() {
        StringBuilder allEventsText = new StringBuilder("Tous les événements :\n");
        for (Event event : allEventsList) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = sdf.format(event.getDateInMillis());
            allEventsText.append("- ").append(formattedDate).append(": ").append(event.getEventName()).append("\n");
        }
        allEventsTextView.setText(allEventsText.toString());
    }

    private long getDateInMillis(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTimeInMillis();
    }

    private Event findEventByDate(long dateInMillis) {
        for (Event event : allEventsList) {
            if (event.getDateInMillis() == dateInMillis) {
                return event;
            }
        }
        return null;
    }

    private String getFormattedDate(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date(dateInMillis));
    }
}
