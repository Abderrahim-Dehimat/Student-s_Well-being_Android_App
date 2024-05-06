package com.example.happyuapp;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import org.eazegraph.lib.models.PieModel;
public class StatistiqueFragment extends Fragment {

    private Button click;
    private BarChart chart;
    private int i1 = 15;
    private int i2 = 25;
    private int i3 = 35;
    private int i4 = 45;


    public StatistiqueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistique, container, false);

        click = view.findViewById(R.id.btn_click);
        chart = (BarChart) view.findViewById(R.id.bar_chart);
        addToBarChart();

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

        private void addToBarChart() {
            // add to bar chart
            chart.addBar(new BarModel("Sun", i1, Color.parseColor("#F9B34C")));
            chart.addBar(new BarModel("Mon", i2, Color.parseColor("#F9B34C")));
            chart.addBar(new BarModel("Tue", i3, Color.parseColor("#F9B34C")));
            chart.addBar(new BarModel("Wed", i4, Color.parseColor("#F9B34C")));
            chart.addBar(new BarModel("Thi", i2, Color.parseColor("#F9B34C")));
            chart.addBar(new BarModel("Fri", i4, Color.parseColor("#F9B34C")));
            chart.addBar(new BarModel("Sat", i1, Color.parseColor("#F9B34C")));

            chart.startAnimation();
            click.setClickable(false);
        }
    }