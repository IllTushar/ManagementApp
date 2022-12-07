package com.example.teachermanagement.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachermanagement.Adapter.noticeRecyclerView;
import com.example.teachermanagement.DashBoard.Notice.CreateNotice;
import com.example.teachermanagement.Model.CreateNoticeModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class Notice extends Fragment {

FloatingActionButton fb;
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
        View root = inflater.inflate(R.layout.fragment_notice, container, false);
        fb = root.findViewById(R.id.create_notice);
        recyclerView = root.findViewById(R.id.notice_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {

            FirebaseRecyclerOptions<CreateNoticeModel>options = new FirebaseRecyclerOptions.Builder<CreateNoticeModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Notice's")
                            ,CreateNoticeModel.class )
                    .build();
            adapter = new noticeRecyclerView(options);
            Log.i("##Tag",adapter.toString());
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Log.d("##Notice",e.getMessage());
        }
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CreateNotice();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FrameLayout,fragment)
                        .addToBackStack(null)
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