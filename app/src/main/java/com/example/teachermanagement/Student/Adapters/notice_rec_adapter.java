package com.example.teachermanagement.Student.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.Model.CreateNoticeModel;
import com.example.teachermanagement.R;
import com.example.teachermanagement.Student.StNotice.viewNotice;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class notice_rec_adapter extends FirebaseRecyclerAdapter<CreateNoticeModel, notice_rec_adapter.myViewHolders> {


    public notice_rec_adapter(@NonNull FirebaseRecyclerOptions<CreateNoticeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolders holder, int position, @NonNull CreateNoticeModel model) {
        holder.title.setText(model.getTitle());
        holder.date.setText(model.getDate());
        //  holder.link.setText(model.getNoticeUri());
        holder.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new viewNotice(model.getTitle(),model.getDate(),model.getNoticeUri()))
                        .addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_row_xml,parent,false);
        return new myViewHolders(view);
    }

    public class myViewHolders extends RecyclerView.ViewHolder{
        TextView title,date,link;
        FloatingActionButton fb;
        public myViewHolders(@NonNull View itemView) {
            super(itemView);
            title  = itemView.findViewById(R.id.noticeTitle);
            date = itemView.findViewById(R.id.noticeDate);
            // link = itemView.findViewById(R.id.noticeLink);
            fb = itemView.findViewById(R.id.btnViewNotice);

        }


    }
}
