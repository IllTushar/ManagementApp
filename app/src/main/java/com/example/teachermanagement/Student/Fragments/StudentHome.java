package com.example.teachermanagement.Student.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachermanagement.DashBoard.ClassSchedule.Schedule;
import com.example.teachermanagement.R;
import com.example.teachermanagement.Student.Schedule.ClassSchedule;

public class StudentHome extends Fragment {

    CardView img,assignment,quizz;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_home, container, false);
        img =view.findViewById(R.id.st_class_schedule);
        assignment =view.findViewById(R.id.st_assignment);
        quizz  = view.findViewById(R.id.st_quizz);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Fragment fragment = new ClassSchedule();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.FrameLayout,fragment);
                    fragmentTransaction.addToBackStack(null).commit();
                }
                catch (Exception e){
                    Log.d("##Check",e.getMessage());
                }
            }
        });
    return view;
    }
}