package com.example.teachermanagement.DashBoard.Quizz;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachermanagement.Adapter.QuizzRecyclerAdapter;
import com.example.teachermanagement.Model.QuizzModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ViewQuestions extends Fragment {

FloatingActionButton fb;
RecyclerView recyclerView;
QuizzRecyclerAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=  inflater.inflate(R.layout.fragment_view_questions, container, false);
        fb = root.findViewById(R.id.create_quizzes);
        recyclerView = root.findViewById(R.id.rec_view_quizz);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            FirebaseRecyclerOptions<QuizzModel>options = new FirebaseRecyclerOptions.Builder<QuizzModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Quizzes"),QuizzModel.class)
                    .build();
            adapter = new QuizzRecyclerAdapter(options);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new CreatQuestions();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FrameLayout,fragment).addToBackStack(null)
                        .commit();
            }
        });
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        try{

            adapter.startListening();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        try {

            adapter.stopListening();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}