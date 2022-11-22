package com.example.teachermanagement.DashBoard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.teachermanagement.R;

public class Dashboard extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        String Email = getIntent().getStringExtra("email");
        toolbar.setSubtitle(Email);
        setSupportActionBar(toolbar);
    }
}