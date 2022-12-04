package com.example.teachermanagement.DashBoard.ClassSchedule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.teachermanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;


public class Reschedule extends Fragment {
String name,subject,year,time,date;
int hr,min,d,m,y;
TextView Time,Name,Subject,Year,Date;
Button reShedule,Delete;
ImageView Clander,Clock;
FirebaseDatabase database;
DatabaseReference reference;
public Reschedule() {
        // Required empty public constructor
    }

    public Reschedule(String name,String subject,String year,String date,String time){
        this.name =name;
        this.subject = subject;
        this.year =year;
        this.date =date;
        this.time =time;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_reschedule, container, false);
         Time = v.findViewById(R.id.retime);
         Date = v.findViewById(R.id.reDates);
         Name = v.findViewById(R.id.reName);
         Subject = v.findViewById(R.id.reSubject);
         Year = v.findViewById(R.id.reClass);
         reShedule  =v.findViewById(R.id.btnSchedule);
         Delete = v.findViewById(R.id.btnDelete);
         Clander = v.findViewById(R.id.recalender);
         Clock = v.findViewById(R.id.reclock);
         Name.setText(name);
         Subject.setText(subject);
         Date.setText(date);
         Year.setText(year);
         Time.setText(time);
         database = FirebaseDatabase.getInstance();
         reference = database.getReference("Class Schedule's");



         Clock.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 setTime();
             }
         });
        final Calendar calendar = Calendar.getInstance();
         Clander.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 y = calendar.get(calendar.YEAR);
                 m = calendar.get(calendar.MONTH);
                 d = calendar.get(calendar.DATE);
                 DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         Date.setText(dayOfMonth+"/"+month+"/"+year);
                     }
                 },y,m,d);
                 datePickerDialog.show();

             }
         });

         reShedule.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ProgressDialog pd = new ProgressDialog(getContext());
                 pd.show();
                 pd.setMessage("Please wait...");
                 subject = Subject.getText().toString();
                 date = Date.getText().toString();
                 time = Time.getText().toString();
                 HashMap map = new HashMap();
                 map.put("date",date);
                 map.put("time",time);
                 reference.child(subject).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                     @Override
                     public void onComplete(@NonNull Task task) {
                      if (task.isSuccessful()){
                          Toast.makeText(getContext(), "Re-Scheduled !!", Toast.LENGTH_SHORT).show();
                          pd.dismiss();
                      }
                      else{
                          Toast.makeText(getContext(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
                          pd.dismiss();
                      }
                     }
                 });
             }
         });

         Delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 subject = Subject.getText().toString();
                 reference.child(subject).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()){
                            Fragment fragment = new Schedule();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction =fragmentManager.beginTransaction();
                            transaction.replace(R.id.FrameLayout,fragment)
                                    .addToBackStack(null).commit();
                         }
                         else{
                             Toast.makeText(getContext(), "Something went wrong !!", Toast.LENGTH_SHORT).show();

                         }
                     }
                 });
             }
         });

        return v;
    }

    private void setTime() {
        final Calendar calendar = Calendar.getInstance();
        hr = calendar.get(calendar.HOUR);
        min = calendar.get(calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendars = Calendar.getInstance();
                calendars.set(Calendar.HOUR,hourOfDay);
                calendars.set(Calendar.MINUTE,minute);
                Time.setText(hourOfDay+":"+minute);
            }
        },hr,min,true);
        timePickerDialog.show();
    }
}