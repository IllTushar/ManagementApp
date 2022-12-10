package com.example.teachermanagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.DashBoard.Notice.CreateNotice;
import com.example.teachermanagement.DashBoard.Notice.ViewNotice;
import com.example.teachermanagement.Model.CreateNoticeModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class noticeRecyclerView extends FirebaseRecyclerAdapter<CreateNoticeModel,noticeRecyclerView.myViewHolder> {
    public noticeRecyclerView(@NonNull FirebaseRecyclerOptions<CreateNoticeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull CreateNoticeModel model) {
        holder.title.setText(model.getTitle());
        holder.date.setText(model.getDate());
      //  holder.link.setText(model.getNoticeUri());
        holder.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new ViewNotice(model.getTitle(),model.getDate(),model.getNoticeUri()))
                        .addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_row_xml,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
      TextView title,date,link;
      FloatingActionButton fb;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title  = itemView.findViewById(R.id.noticeTitle);
            date = itemView.findViewById(R.id.noticeDate);
           // link = itemView.findViewById(R.id.noticeLink);
            fb = itemView.findViewById(R.id.btnViewNotice);

        }
    }
}
