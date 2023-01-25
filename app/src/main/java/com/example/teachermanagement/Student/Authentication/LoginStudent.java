package com.example.teachermanagement.Student.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.R;
import com.example.teachermanagement.Student.StudentDashboard.StudentDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginStudent extends AppCompatActivity {
    TextView registration;
    FirebaseAuth mAuth;
    Button Login;
    EditText Email,Password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        registration = findViewById(R.id.stregistration);
        mAuth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.stlogin);
        Email = findViewById(R.id.stemail);
        Password = findViewById(R.id.stpassword);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                validation(email,password);
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginStudent.this, RegistrationStudent.class));
                finish();
            }
        });
    }
    private void validation(String email, String password) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.show();
        pd.setMessage("Please wait...");
        if (email.isEmpty()){
            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            return;
        }
        else if (password.isEmpty()){
            Toast.makeText(this, "Enter valid Password", Toast.LENGTH_SHORT).show();
            pd.dismiss();
            return;
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String email = Email.getText().toString().trim();
                                Intent i = new Intent(LoginStudent.this, StudentDashboard.class);
                                i.putExtra("email",email);
                                i.putExtra("password",Password.getText().toString());
                                startActivity(i);
                                pd.dismiss();
                                Email.setText("");
                                Password.setText("");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginStudent.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}