package com.example.teachermanagement.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.DashBoard.Quizz.UpdateQuizz;
import com.example.teachermanagement.Model.QuizzModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizzRecyclerAdapter extends FirebaseRecyclerAdapter<QuizzModel,QuizzRecyclerAdapter.myViewHolder> {

    public QuizzRecyclerAdapter(@NonNull FirebaseRecyclerOptions<QuizzModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull QuizzModel model) {
        holder.title.setText(model.getQuestion());
        holder.year.setText(model.getClassCode());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quizzes");
                reference.child(model.getQuestion()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(v.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Something Wents Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,new UpdateQuizz(model.getQuestion(),model.getAnswer(),model.getClassCode()))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quizz_single_row_xml,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView title,year;
        Button update,delete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.questionTitle);
            year  = itemView.findViewById(R.id.year);
            update = itemView.findViewById(R.id.btnUpdateQuizz);
            delete = itemView.findViewById(R.id.btnDeleteQuizz);
        }
    }
}
