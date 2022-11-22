package com.example.teachermanagement.Authentication;

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

import com.example.teachermanagement.DashBoard.Dashboard;
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

public class Registration extends AppCompatActivity {
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
        setContentView(R.layout.activity_registration);
        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Image = findViewById(R.id.profileImage);
        Login = findViewById(R.id.Login);
        Registration = findViewById(R.id.Registration);


        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Teacher's Details");
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
                    Toast.makeText(Registration.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(email.isEmpty()){
                    Toast.makeText(Registration.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(password.isEmpty()){
                    Toast.makeText(Registration.this, "Enter Password", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(Registration.this,Login.class));
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
         StorageReference ref  = storageReference.child("Teacher's Images/*"+ UUID.randomUUID().toString());
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
                                   Toast.makeText(Registration.this, "Upload Successfull !!", Toast.LENGTH_SHORT).show();
                                   Intent i = new Intent(Registration.this, Dashboard.class);
                                   startActivity(i);
                               }
                           });
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Registration.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();
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
           reference.child(name).setValue(authenticationModel);
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
    ActivityResultLauncher<Intent>resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
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