package com.example.teachermanagement.Student.Schedule;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.teachermanagement.Adapter.ClassScheduleRecyclerView;
import com.example.teachermanagement.Student.Adapters.student_class_schedule;
import com.example.teachermanagement.Model.ClassScheduleModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ClassSchedule extends Fragment {

    FloatingActionButton  btn_search;
    RecyclerView recyclerView;
    EditText search;
    student_class_schedule adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_class_schedule, container, false);
        recyclerView = view.findViewById(R.id.strec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search = view.findViewById(R.id.stsearchoption);
        btn_search = view.findViewById(R.id.btnstSearch);
        try {

            FirebaseRecyclerOptions<ClassScheduleModel> options =
                    new FirebaseRecyclerOptions.Builder<ClassScheduleModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Class Schedule's"), ClassScheduleModel.class)
                            .build();
            adapter = new student_class_schedule(options);
            recyclerView.setAdapter(adapter);


        }catch (Exception e){
            Log.d("##Checking",e.getMessage());
        }
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
        adapter = new student_class_schedule(options);
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