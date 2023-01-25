package com.example.teachermanagement.Student.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teachermanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class StudentProfile extends Fragment {

    CircleImageView profile;
    TextView name,email,password;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_profile, container, false);
        profile = view.findViewById(R.id.profileimg);
        name  = view.findViewById(R.id.profilename);
        email = view.findViewById(R.id.profileemail);
        password = view.findViewById(R.id.profilepassword);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Student's Details");
        String Password = getActivity().getIntent().getStringExtra("password");
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.show();
        pd.setMessage("Please wait...");
        databaseReference.child(Password).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Email = String.valueOf(snapshot.child("email").getValue());
                String Name  = String.valueOf(snapshot.child("name").getValue());
                String Password = String.valueOf(snapshot.child("password").getValue());
                String Profile = String.valueOf(snapshot.child("imageuri").getValue());

                name.setText(Name);
                email.setText(Email);
                password.setText(Password);
                Glide.with(getContext()).load(Profile).fitCenter().into(profile);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    return view;
    }
}