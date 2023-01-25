package com.example.teachermanagement.DashBoard.Quizz;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teachermanagement.Model.QuizzModel;
import com.example.teachermanagement.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreatQuestions extends Fragment {

EditText question,answer,classCode;
Button uploadQuestion;
FirebaseDatabase database;
DatabaseReference reference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_creat_questions, container, false);
        question = root.findViewById(R.id.addQuestion);
        answer = root.findViewById(R.id.addAnswer);
        classCode = root.findViewById(R.id.quizzClassCode);
        uploadQuestion = root.findViewById(R.id.btnUploadQuestion);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Quizzes");
        uploadQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(getContext());
                pd.show();
                pd.setMessage("please wait...");
                String Question = question.getText().toString().trim();
                String Answer = answer.getText().toString().trim();
                String ClassCode = classCode.getText().toString().trim();
                if (Question.isEmpty()){
                    Toast.makeText(getContext(), "Enter Question", Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
                else if(ClassCode.isEmpty()){
                    Toast.makeText(getContext(), "Enter Class Code", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else if(Answer.isEmpty()){
                    Toast.makeText(getContext(), "Enter Answer", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else{
                    QuizzModel quizzModel = new QuizzModel(Question,ClassCode,Answer);
                    reference.child(Question).setValue(quizzModel);
                    pd.dismiss();
                    Fragment fragment = new ViewQuestions();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.FrameLayout,fragment)
                            .addToBackStack(null).commit();
                }

            }
        });
        return root;
    }
}