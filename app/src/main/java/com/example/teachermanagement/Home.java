package com.example.teachermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teachermanagement.Authentication.Login;
import com.example.teachermanagement.Student.Authentication.LoginStudent;

public class Home extends AppCompatActivity {
Button Student,Teacher;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Student = findViewById(R.id.Student);
        Teacher = findViewById(R.id.Teacher);
        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, LoginStudent.class));
            }
        });
        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Login.class));
            }
        });
    }
}