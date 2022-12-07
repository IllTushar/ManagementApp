package com.example.teachermanagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.DashBoard.Notice.CreateNotice;
import com.example.teachermanagement.Model.CreateNoticeModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class noticeRecyclerView extends FirebaseRecyclerAdapter<CreateNoticeModel,noticeRecyclerView.myViewHolder> {
    public noticeRecyclerView(@NonNull FirebaseRecyclerOptions<CreateNoticeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull CreateNoticeModel model) {
        holder.title.setText(model.getTitle());
        holder.date.setText(model.getDate());
      //  holder.link.setText(model.getNoticeUri());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_row_xml,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
      TextView title,date,link;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title  = itemView.findViewById(R.id.noticeTitle);
            date = itemView.findViewById(R.id.noticeDate);
           // link = itemView.findViewById(R.id.noticeLink);
        }
    }
}
