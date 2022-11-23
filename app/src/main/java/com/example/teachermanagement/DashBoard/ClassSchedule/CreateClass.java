package com.example.teachermanagement.DashBoard.ClassSchedule;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.teachermanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateClass extends AppCompatActivity {
FloatingActionButton fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        fb = findViewById(R.id.create);

    }
}