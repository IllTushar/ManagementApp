package com.example.teachermanagement.DashBoard.ClassSchedule;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.teachermanagement.Model.ClassScheduleModel;
import com.example.teachermanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ScheduleForm extends AppCompatActivity {
TextView dates,time;
ImageView calenders,clock;
EditText Name,Class,Subject;
Button Schedule;
FloatingActionButton fb;
int y,m,d;
int hr,min;
FirebaseDatabase firebaseDatabase;
DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
        dates = findViewById(R.id.Dates);
        time = findViewById(R.id.time);
        clock = findViewById(R.id.clock);
        Name = findViewById(R.id.teacherName);
        Class = findViewById(R.id.Class);
        Subject = findViewById(R.id.Subject);
        Schedule = findViewById(R.id.btnSchedule);
         fb = findViewById(R.id.btn_back);
         fb.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(ScheduleForm.this,CreateClass.class));
                 finish();
             }
         });
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Class Schedule's");
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        calenders = findViewById(R.id.calender);
        final Calendar calendar = Calendar.getInstance();
        calenders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y = calendar.get(calendar.YEAR);
                m = calendar.get(calendar.MONTH);
                d = calendar.get(calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ScheduleForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dates.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },y,m,d);
                datePickerDialog.show();
            }
        });
      Schedule.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ProgressDialog pd  = new ProgressDialog(ScheduleForm.this);
              pd.show();
              pd.setMessage("Please wait..");
              String name = Name.getText().toString().trim();
              String classes = Class.getText().toString().trim();
              String subject = Subject.getText().toString().trim();
              String Date = dates.getText().toString().trim();
              String Time = time.getText().toString().trim();
              if (name.isEmpty()){
                  Toast.makeText(ScheduleForm.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                  pd.dismiss();
                  return;
              }
              else if(classes.isEmpty()){
                  Toast.makeText(ScheduleForm.this, "Enter Year !!", Toast.LENGTH_SHORT).show();
                  pd.dismiss();
                  return;
              }
              else if(subject.isEmpty()){
                  Toast.makeText(ScheduleForm.this, "Enter Subject !!", Toast.LENGTH_SHORT).show();
                  pd.dismiss();
                  return;
              }
              else {
                  ClassScheduleModel classScheduleModel = new ClassScheduleModel(name,subject,classes,Date,Time);
                  reference.child(subject).setValue(classScheduleModel);
                  Intent i = new Intent(ScheduleForm.this,CreateClass.class);
                  startActivity(i);
                  pd.dismiss();
                  Name.setText("");
                  Class.setText("");
                  Subject.setText("");
                  dates.setText("");
                  time.setText("");

              }

          }
      });
    }

    private void setTime() {
        final  Calendar calendar = Calendar.getInstance();
        hr = calendar.get(calendar.HOUR);
        min = calendar.get(calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                  Calendar calendars = Calendar.getInstance();
                calendars.set(Calendar.HOUR,hourOfDay);
                 calendars.set(Calendar.MINUTE,minute);

                time.setText(hourOfDay+":"+minute);
            }
        },hr,min,true);
        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this, "Please use Back button", Toast.LENGTH_SHORT).show();
    }
}