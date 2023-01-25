package com.example.teachermanagement.Student.StudentDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teachermanagement.Fragment.Home;
import com.example.teachermanagement.Fragment.Notice;
import com.example.teachermanagement.Fragment.Profile;
import com.example.teachermanagement.R;
import com.example.teachermanagement.Student.Fragments.StudentHome;
import com.example.teachermanagement.Student.Fragments.StudentNotice;
import com.example.teachermanagement.Student.Fragments.StudentProfile;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class StudentDashboard extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {
    Toolbar toolbar;
    ChipNavigationBar chipNavigationBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        toolbar = findViewById(R.id.toolbar);
        chipNavigationBar = findViewById(R.id.stmenu);
        chipNavigationBar.setOnItemSelectedListener(this);
        loadFragment(new StudentHome());
        String Email = getIntent().getStringExtra("email");
        toolbar.setSubtitle(Email);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onItemSelected(int i) {
        Fragment fragment =null;
        switch (i){
            case R.id.home:
                fragment = new StudentHome();
                break;
            case R.id.notes:
                fragment = new StudentNotice();
                break;
            case R.id.profile:
                fragment = new StudentProfile();
                break;
        }
        loadFragment(fragment);
    }
    private void loadFragment(Fragment fragment)
    {
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.FrameLayout,fragment)
                    .commit();
        }
        else{
            Toast.makeText(this, "Fragment Error !!", Toast.LENGTH_SHORT).show();
        }
    }
}