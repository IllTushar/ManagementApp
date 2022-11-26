package com.example.teachermanagement.Adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.DashBoard.ClassSchedule.Reschedule;
import com.example.teachermanagement.Model.ClassScheduleModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClassScheduleRecyclerView extends FirebaseRecyclerAdapter<ClassScheduleModel,ClassScheduleRecyclerView.myViewHolder> {

    public ClassScheduleRecyclerView(@NonNull FirebaseRecyclerOptions<ClassScheduleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ClassScheduleModel model) {
            holder.title.setText(model.getYear());
            holder.subject.setText(model.getSubject());
            holder.date.setText("Date: "+model.getDate());
            holder.time.setText("Timing: "+model.getTime());
            holder.name.setText(model.getName());
            holder.fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)  {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new Reschedule(model.getName(),model.getSubject()
                    ,model.getYear(),model.getDate(),model.getTime())).addToBackStack(null).commit();
                }
            });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_schedule_single_row_xml,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
    TextView title,subject,name,time,date;
    FloatingActionButton fb;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titles);
            subject = itemView.findViewById(R.id.subject);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.dates);
            fb = itemView.findViewById(R.id.click);

        }
    }
}
