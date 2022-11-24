package com.example.teachermanagement.DashBoard.ClassSchedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.example.teachermanagement.Adapter.ClassScheduleRecyclerView;
import com.example.teachermanagement.Model.ClassScheduleModel;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.example.teachermanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class CreateClass extends AppCompatActivity {
FloatingActionButton fb, btn_search;
RecyclerView recyclerView;
EditText search;
    ClassScheduleRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        fb = findViewById(R.id.create);
        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search = findViewById(R.id.searchoption);
        btn_search = findViewById(R.id.btnSearch);

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
                startActivity(new Intent(CreateClass.this,ScheduleForm.class));
                finish();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Search = search.getText().toString().trim();
              SeachQuery(Search);
            }
        });
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
    protected void onStart() {
        super.onStart();
        try{

            adapter.startListening();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        try {

            adapter.stopListening();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}