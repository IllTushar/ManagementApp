package com.example.teachermanagement.DashBoard.Assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.Adapter.AssignmentRecyclerView;
import com.example.teachermanagement.Adapter.createClassRecyclerView;
import com.example.teachermanagement.Model.ClassScheduleModel;
import com.example.teachermanagement.Model.CreateClassModel;
import com.example.teachermanagement.Model.uploadAssignmentModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class show_Assignment extends AppCompatActivity {
FloatingActionButton btnCreateAssignment;
private long pressedTime;
RecyclerView rec_view;
TextView classCode;
AssignmentRecyclerView adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_assignment);
        btnCreateAssignment =findViewById(R.id.btnCreateAssignment);
        classCode = findViewById(R.id.code);
        String ClassCode = getIntent().getStringExtra("classCode");
        classCode.setText(ClassCode);
        rec_view = findViewById(R.id.rec_view1);
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        try {
            FirebaseRecyclerOptions<uploadAssignmentModel> options =
                    new FirebaseRecyclerOptions.Builder<uploadAssignmentModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Assignment Detail's")
                                            .child("classCodes")
                                         //   .child("assignmentTitle")
                                    , uploadAssignmentModel.class)
                            .build();
            adapter = new AssignmentRecyclerView(options);
            rec_view.setAdapter(adapter);
        }
        catch (Exception e){
            Log.d("##classCode",e.getMessage());
        }
        btnCreateAssignment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    String code =classCode.getText().toString();
                    Intent i = new Intent(show_Assignment.this,uploadAssignment.class);
                    i.putExtra("code",code);
                    startActivity(i);
                //    startActivity(new Intent(show_Assignment.this,uploadAssignment.class));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public void onBackPressed()
    {

        if (pressedTime + 2000 > System.currentTimeMillis())
        {
            super.onBackPressed();
            finish();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
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