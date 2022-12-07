package com.example.teachermanagement.DashBoard.Assignment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teachermanagement.Adapter.createClassRecyclerView;
import com.example.teachermanagement.Model.CreateClassModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class CreateClass extends Fragment {
FloatingActionButton fb,search;
RecyclerView rec_view;
createClassRecyclerView adapter;
EditText searchField;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_create_class, container, false);
        fb = v.findViewById(R.id.btnClass);
        rec_view = v.findViewById(R.id.class_rec_view);
        searchField = v.findViewById(R.id.createSearchOption);
        search = v.findViewById(R.id.btnCreateSearch);
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.show();
        pd.setMessage("Please wait..");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SearchFiled = searchField.getText().toString().trim();
                if (SearchFiled.isEmpty())
                {
                    Toast.makeText(getContext(), "Enter Subject Code", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else
                {
                    setQuery(SearchFiled);
                }
            }
        });
        rec_view.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            FirebaseRecyclerOptions<CreateClassModel>options = new FirebaseRecyclerOptions.Builder<CreateClassModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("Personal Class"),CreateClassModel.class)
                    .build();
            adapter = new createClassRecyclerView(options);
             rec_view.setAdapter(adapter);
             pd.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        fb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new NewClass();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FrameLayout,fragment).addToBackStack(null).commit();
            }
        });

        return v;
    }

    private void setQuery(String searchFiled)
    {
        FirebaseRecyclerOptions<CreateClassModel>options = new FirebaseRecyclerOptions.Builder<CreateClassModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Personal Class")
                .orderByChild("classCode").startAt(searchFiled),CreateClassModel.class )
                .build();
        adapter = new createClassRecyclerView(options);
        adapter.startListening();
        rec_view.setAdapter(adapter);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        try
        {
            adapter.startListening();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onStop()
    {
        super.onStop();
        try
        {
            adapter.stopListening();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}