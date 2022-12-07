package com.example.teachermanagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.Model.CreateClassModel;
import com.example.teachermanagement.Model.uploadAssignmentModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Objects;

public class AssignmentRecyclerView extends FirebaseRecyclerAdapter<uploadAssignmentModel,AssignmentRecyclerView.myViewHolder>
{

    public AssignmentRecyclerView(@NonNull FirebaseRecyclerOptions<uploadAssignmentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull uploadAssignmentModel model)
    {
        holder.Title.setText(model.getAssignmentTitle());
        holder.publishDate.setText(model.getPublishDate());
        holder.lastDate.setText(model.getDeadLine());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_asssignment_xml,parent,false);
        return new myViewHolder(v);
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
    TextView Title,publishDate,lastDate;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        Title = itemView.findViewById(R.id.assignmentTitle);
        publishDate = itemView.findViewById(R.id.publishDates);
        lastDate = itemView.findViewById(R.id.lastDate);
    }
}
}
