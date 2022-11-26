package com.example.teachermanagement.DashBoard.ClassSchedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachermanagement.R;


public class Reschedule extends Fragment {
String name,subject,year,time,date;

    public Reschedule() {
        // Required empty public constructor
    }

    public Reschedule(String name,String subject,String year,String date,String time){
        this.name =name;
        this.subject = subject;
        this.year =year;
        this.date =date;
        this.time =time;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_reschedule, container, false);
        return v;
    }
}