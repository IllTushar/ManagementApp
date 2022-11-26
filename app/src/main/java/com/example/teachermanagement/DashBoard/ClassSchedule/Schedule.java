package com.example.teachermanagement.DashBoard.ClassSchedule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.teachermanagement.Adapter.ClassScheduleRecyclerView;
import com.example.teachermanagement.Model.ClassScheduleModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class Schedule extends Fragment {
    FloatingActionButton fb, btn_search;
    RecyclerView recyclerView;
    EditText search;
    ClassScheduleRecyclerView adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_schedule, container, false);
        fb = view.findViewById(R.id.create);
        recyclerView = view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search = view.findViewById(R.id.searchoption);
        btn_search = view.findViewById(R.id.btnSearch);

        try {

            FirebaseRecyclerOptions<ClassScheduleModel> options =
                    new FirebaseRecyclerOptions.Builder<ClassScheduleModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Class Schedule's"), ClassScheduleModel.class)
                            .build();
            adapter = new ClassScheduleRecyclerView(options);
            recyclerView.setAdapter(adapter);


        }catch (Exception e){
            Log.d("##Checking",e.getMessage());
        }
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ScheduleForm();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Always use parent frameLayout id...
                fragmentTransaction.replace(R.id.FrameLayout,fragment);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Search = search.getText().toString().trim();
                SeachQuery(Search);
            }
        });

       return view;
    }
    private void SeachQuery(String search)
    {
        FirebaseRecyclerOptions<ClassScheduleModel> options =
                new FirebaseRecyclerOptions.Builder<ClassScheduleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Class Schedule's").orderByChild("subject")
                                .startAt(search), ClassScheduleModel.class)
                        .build();
        adapter = new ClassScheduleRecyclerView(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        try{

            adapter.startListening();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        try {

            adapter.stopListening();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}