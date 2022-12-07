package com.example.teachermanagement.DashBoard.Assignment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teachermanagement.Model.CreateClassModel;
import com.example.teachermanagement.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewClass extends Fragment {
EditText classTeacherName,classSubject,classCode,className;
static FirebaseDatabase  firebaseDatabase = FirebaseDatabase.getInstance();;
static DatabaseReference  reference = firebaseDatabase.getReference("Personal Class");
Button createClass,shareClassCode;
  public NewClass() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_new_class, container, false);
         classTeacherName = v.findViewById(R.id.classTeacherName);
         classSubject = v.findViewById(R.id.classSubject);
         classCode = v.findViewById(R.id.classCode);
         className = v.findViewById(R.id.className);
         createClass = v.findViewById(R.id.btnCreate);



         createClass.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String TeacherName = classTeacherName.getText().toString().trim();
                 String ClassSubject = classSubject.getText().toString().trim();
                 String ClassCode = classCode.getText().toString().trim();
                 String ClassName = className.getText().toString().trim();
                 ProgressDialog pd = new ProgressDialog(getContext());
                 pd.show();
                 pd.setMessage("Please wait...");
                 if (TeacherName.isEmpty())
                 {
                     Toast.makeText(getContext(), "Enter Class Teacher Name !!", Toast.LENGTH_SHORT).show();
                     pd.dismiss();
                 } else if (ClassSubject.isEmpty())
                 {
                     Toast.makeText(getContext(), "Enter Subject Name !!", Toast.LENGTH_SHORT).show();
                     pd.dismiss();
                 } else if (ClassCode.isEmpty())
                 {
                     Toast.makeText(getContext(), "Enter Class Code !!", Toast.LENGTH_SHORT).show();
                     pd.dismiss();
                 } else if (ClassName.isEmpty())
                 {
                     Toast.makeText(getContext(), "Enter Year !!", Toast.LENGTH_SHORT).show();
                     pd.dismiss();
                 } else
                 {
                     CreateClassModel createClassModel = new CreateClassModel(TeacherName, ClassSubject, ClassName, ClassCode);
                     reference.child(ClassCode).setValue(createClassModel);
                     Toast.makeText(getContext(), "Create Class Successfully !!", Toast.LENGTH_SHORT).show();
                     Fragment fragment = new CreateClass();
                     FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.FrameLayout,fragment).addToBackStack(null).commit();
                     pd.dismiss();
                 }
             }
         });
        return v;
    }
}