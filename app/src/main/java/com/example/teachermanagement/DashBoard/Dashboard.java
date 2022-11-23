package com.example.teachermanagement.DashBoard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.teachermanagement.Fragment.Home;
import com.example.teachermanagement.Fragment.Notice;
import com.example.teachermanagement.Fragment.Profile;
import com.example.teachermanagement.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Dashboard extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {
Toolbar toolbar;
ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        chipNavigationBar = findViewById(R.id.menu);
        loadFragment(new Home());
        chipNavigationBar.setOnItemSelectedListener(this);
        String Email = getIntent().getStringExtra("email");
        toolbar.setSubtitle(Email);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onItemSelected(int i) {
        Fragment fragment =null;
        switch (i){
            case R.id.home:
                fragment = new Home();
                break;
            case R.id.notes:
                fragment = new Notice();
                break;
            case R.id.profile:
                fragment = new Profile();
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