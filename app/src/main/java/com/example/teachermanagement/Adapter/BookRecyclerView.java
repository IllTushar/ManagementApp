package com.example.teachermanagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.Model.CreateBookModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookRecyclerView extends FirebaseRecyclerAdapter<CreateBookModel,BookRecyclerView.myViewHolder> {

    public BookRecyclerView(@NonNull FirebaseRecyclerOptions<CreateBookModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull CreateBookModel model) {
        holder.Title.setText(model.getBookTitle());
        holder.Author.setText(model.getAuthor());
        holder.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_book_xml,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView Title,Author;
        FloatingActionButton fb;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
         Title = itemView.findViewById(R.id.bookName);
         Author = itemView.findViewById(R.id.author);
         fb = itemView.findViewById(R.id.btnbook);
        }
    }
}
