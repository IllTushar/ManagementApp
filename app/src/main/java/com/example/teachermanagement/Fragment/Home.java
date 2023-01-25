package com.example.teachermanagement.Fragment;

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

import com.example.teachermanagement.DashBoard.Assignment.CreateClass;
import com.example.teachermanagement.DashBoard.Books.Books;
import com.example.teachermanagement.DashBoard.ClassSchedule.Schedule;
import com.example.teachermanagement.DashBoard.Quizz.ViewQuestions;
import com.example.teachermanagement.R;


public class Home extends Fragment {

CardView img,assignment,quizz,Book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img =view.findViewById(R.id.class_schedule);
        assignment =view.findViewById(R.id.assignment);
        quizz  = view.findViewById(R.id.quizz);
         Book = view.findViewById(R.id.book);

         Book.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Fragment fragment = new Books();
                 FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                 fragmentTransaction.replace(R.id.FrameLayout,fragment)
                         .addToBackStack(null)
                         .commit();
             }
         });

        quizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewQuestions();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FrameLayout,fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                  Fragment fragment = new Schedule();
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
        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CreateClass();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FrameLayout,fragment).addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}