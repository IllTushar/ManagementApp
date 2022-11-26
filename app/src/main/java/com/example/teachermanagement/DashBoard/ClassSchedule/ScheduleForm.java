package com.example.teachermanagement.DashBoard.ClassSchedule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ScheduleForm extends Fragment {
    TextView dates,time;
    ImageView calenders,clock;
    EditText Name,Class,Subject;
    Button Schedule;
    FloatingActionButton fb;
    int y,m,d;
    int hr,min;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_schedule_form, container, false);
        dates =v. findViewById(R.id.Dates);
        time =v. findViewById(R.id.time);
        clock = v.findViewById(R.id.clock);
        Name =v. findViewById(R.id.teacherName);
        Class = v.findViewById(R.id.Class);
        Subject = v.findViewById(R.id.Subject);
        Schedule = v.findViewById(R.id.btnSchedule);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Class Schedule's");
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
        calenders = v.findViewById(R.id.calender);
        final Calendar calendar = Calendar.getInstance();
        calenders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y = calendar.get(calendar.YEAR);
                m = calendar.get(calendar.MONTH);
                d = calendar.get(calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                ProgressDialog pd  = new ProgressDialog(getContext());
                pd.show();
                pd.setMessage("Please wait..");
                String name = Name.getText().toString().trim();
                String classes = Class.getText().toString().trim();
                String subject = Subject.getText().toString().trim();
                String Date = dates.getText().toString().trim();
                String Time = time.getText().toString().trim();
                if (name.isEmpty()){
                    Toast.makeText(getContext(), "Enter Your Name", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    return;
                }
                else if(classes.isEmpty()){
                    Toast.makeText(getContext(), "Enter Year !!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    return;
                }
                else if(subject.isEmpty()){
                    Toast.makeText(getContext(), "Enter Subject !!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    return;
                }
                else {
                    ClassScheduleModel classScheduleModel = new ClassScheduleModel(name,subject,classes,Date,Time);
                    reference.child(subject).setValue(classScheduleModel);
                    Fragment fragment = new Schedule();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.FrameLayout,fragment);
                    fragmentTransaction.addToBackStack(null).commit();
                    pd.dismiss();
                    Name.setText("");
                    Class.setText("");
                    Subject.setText("");
                    dates.setText("");
                    time.setText("");

                }

            }
        });
        return v;
    }
    private void setTime() {
        final  Calendar calendar = Calendar.getInstance();
        hr = calendar.get(calendar.HOUR);
        min = calendar.get(calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
}