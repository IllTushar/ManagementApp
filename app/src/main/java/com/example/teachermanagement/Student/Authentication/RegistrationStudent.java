package com.example.teachermanagement.Student.Authentication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.Authentication.Login;
import com.example.teachermanagement.Authentication.Registration;
import com.example.teachermanagement.Model.AuthenticationModel;
import com.example.teachermanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationStudent extends AppCompatActivity {
    Button Registration;
    EditText Name,Password,Email;
    CircleImageView Image;
    TextView Login;
    private Uri imageUri;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_student);
        Name = findViewById(R.id.stName);
        Email = findViewById(R.id.stEmail);
        Password = findViewById(R.id.stPassword);
        Image = findViewById(R.id.stprofileImage);
        Login = findViewById(R.id.stLogin);
        Registration = findViewById(R.id.stRegistration);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Student's Details");
        storage = FirebaseStorage.getInstance();
        storageReference  =storage.getReference();
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if (name.isEmpty()){
                    Toast.makeText(RegistrationStudent.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(email.isEmpty()){
                    Toast.makeText(RegistrationStudent.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(password.isEmpty()){
                    Toast.makeText(RegistrationStudent.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    mAuth.createUserWithEmailAndPassword(Email.getText().toString().trim(),Password.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        imageUpload();
                                    }
                                }
                            });

                }


            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationStudent.this, LoginStudent.class));
                finish();
            }
        });
    }

    private void imageUpload()
    {
        if (imageUri!=null)
        {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Please wait..");
            pd.show();
            StorageReference ref  = storageReference.child("Student's Images/*"+ UUID.randomUUID().toString());
            try
            {
                ref.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageuri =uri.toString();
                                        validation(imageuri);
                                        Toast.makeText(RegistrationStudent.this, "Upload Successfull !!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegistrationStudent.this, LoginStudent.class);
                                        i.putExtra("password",Password.getText().toString());
                                        startActivity(i);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegistrationStudent.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                                        .getTotalByteCount());
                                pd.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void validation(String imageUri)
    {
        String name = Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        AuthenticationModel authenticationModel = new AuthenticationModel(name,email,password,imageUri);
        reference.child(password).setValue(authenticationModel);
        Name.setText("");
        Email.setText("");
        Password.setText("");
        Image.setImageResource(R.drawable.profile);



    }
    private void chooseImage()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        resultLauncher.launch(i);
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK){
                        imageUri = result.getData().getData();
                        Image.setImageURI(imageUri);
                    }
                }
            });
}