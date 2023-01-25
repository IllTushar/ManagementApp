package com.example.teachermanagement.Student.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachermanagement.Adapter.noticeRecyclerView;
import com.example.teachermanagement.Model.CreateNoticeModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class StudentNotice extends Fragment {
    RecyclerView recyclerView;
    noticeRecyclerView adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_notice, container, false);
        recyclerView = view.findViewById(R.id.st_notice_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {

            FirebaseRecyclerOptions<CreateNoticeModel> options = new FirebaseRecyclerOptions.Builder<CreateNoticeModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Notice's")
                            ,CreateNoticeModel.class )
                    .build();
            adapter = new noticeRecyclerView(options);
            Log.i("##Tag",adapter.toString());
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e){
            Log.d("##Notice",e.getMessage());
        }
        return view;
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