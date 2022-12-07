package com.example.teachermanagement.DashBoard.Assignment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.Model.CreateClassModel;
import com.example.teachermanagement.Model.uploadAssignmentModel;
import com.example.teachermanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

public class uploadAssignment extends AppCompatActivity {
ImageView publishCalender,submitCalender;
public TextView publishDate,submitDate,AssignmentUri,classCodes;
Button uploadAssignment;
EditText assignmentTitle;
FloatingActionButton browse;
final Calendar calendar = Calendar.getInstance();
int y,m,d;
private Uri pdfUri;
FirebaseStorage firebaseStorage;
StorageReference storageReference;
FirebaseDatabase database;
DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_assignment);
        publishCalender = findViewById(R.id.publishCalender);
        submitCalender = findViewById(R.id.deadLineCalender);
        publishDate = findViewById(R.id.publishDate);
        submitDate = findViewById(R.id.deadLine);
        AssignmentUri = findViewById(R.id.AssignmentUri);
        assignmentTitle = findViewById(R.id.Assignment);
        uploadAssignment = findViewById(R.id.btnUploadAssignment);
        browse = findViewById(R.id.browse);
        firebaseStorage  = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        classCodes = findViewById(R.id.classCodes);
        String codes = getIntent().getStringExtra("code");
        classCodes.setText(codes);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Assignment Detail's");
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPDF();
            }
        });
        uploadAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPDFUrl();
            }
        });
        publishCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalender();
            }
        });
        submitCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y =calendar.get(calendar.YEAR);
                m = calendar.get(calendar.MONTH);
                d = calendar.get(calendar.DATE);
                DatePickerDialog pickerDialog = new DatePickerDialog(uploadAssignment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        submitDate.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },y,m,d);
                pickerDialog.show();
            }
        });
    }

    private void setCalender()
    {
          y =calendar.get(calendar.YEAR);
          m = calendar.get(calendar.MONTH);
          d = calendar.get(calendar.DATE);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                publishDate.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },y,m,d);
        pickerDialog.show();
    }
    private void uploadPDF()
    {
      Intent i = new Intent();
      i.setType("application/pdf");
      i.setAction(Intent.ACTION_GET_CONTENT);
      startActivityResultLaunch.launch(i);
    }
    ActivityResultLauncher<Intent>startActivityResultLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                  if(result.getResultCode()== Activity.RESULT_OK)
                  {
                   pdfUri = result.getData().getData();
                   AssignmentUri.setText(pdfUri.toString());
                  }
                }
            });
    private void downloadPDFUrl()
    {
      if (pdfUri!=null)
      {
          ProgressDialog pd = new ProgressDialog(this);
          pd.show();
          pd.setMessage("Please wait...");
          StorageReference ref = storageReference.child("Assignments/*"+ UUID.randomUUID().toString());
          try {
             ref.putFile(pdfUri)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   pd.dismiss();
                                   String PDFURI = uri.toString();
                                   pdfUrl(PDFURI);
                                   startActivity(new Intent(uploadAssignment.this,show_Assignment.class));
                                   finish();
                               }
                           });
                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(uploadAssignment.this, "something went wrong !!", Toast.LENGTH_SHORT).show();
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
          }catch (Exception e){
              e.printStackTrace();
          }
      }
    }
    private void pdfUrl(String AssignmentUri){
        String AssignmentTitle = assignmentTitle.getText().toString().trim();
        String PublishDate = publishDate.getText().toString().trim();
        String SubmitDate = submitDate.getText().toString().trim();
        String ClassCode   = classCodes.getText().toString().trim();
        uploadAssignmentModel uploadAssignment = new uploadAssignmentModel(AssignmentTitle,PublishDate,SubmitDate,AssignmentUri,ClassCode);
        reference.child(ClassCode).child(AssignmentTitle).setValue(uploadAssignment);

    }
}