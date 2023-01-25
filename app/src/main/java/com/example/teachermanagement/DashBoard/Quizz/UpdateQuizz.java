package com.example.teachermanagement.DashBoard.Quizz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateQuizz extends Fragment {
EditText Question,Answer;
TextView quizzClass;
Button update;
   String question ,answer,classCode;
    public UpdateQuizz() {
        // Required empty public constructor
    }

   public UpdateQuizz(String question,String answer,String classCode){
        this.question = question;
        this.answer = answer;
        this.classCode = classCode;
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
        View root= inflater.inflate(R.layout.fragment_update_quizz, container, false);
        Question = root.findViewById(R.id.updateQuestion);
        Answer  = root.findViewById(R.id.updateAnswer);
        quizzClass = root.findViewById(R.id.quizzClass);
        Question.setText(question);
        Answer.setText(answer);
        quizzClass.setText(classCode);
        update = root.findViewById(R.id.btnUpdateQuestion);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(getContext());
                pd.show();
                pd.setMessage("please wait..");
                question = Question.getText().toString().trim();
                answer = Answer.getText().toString().trim();
                HashMap map = new HashMap();
                map.put("question",question);
                map.put("answer",answer);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Quizzes");
                reference.child(question).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                     if (task.isSuccessful()){
                         pd.dismiss();
                         Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                         Fragment fragment = new ViewQuestions();
                         FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.FrameLayout,fragment)
                                 .addToBackStack(null)
                                 .commit();
                     }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        return root;
    }
}