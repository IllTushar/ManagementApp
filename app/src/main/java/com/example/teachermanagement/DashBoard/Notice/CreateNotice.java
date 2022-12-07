package com.example.teachermanagement.DashBoard.Notice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.Fragment.Notice;
import com.example.teachermanagement.Model.CreateNoticeModel;
import com.example.teachermanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;


public class CreateNotice extends Fragment {
FirebaseDatabase database;
DatabaseReference reference;
FloatingActionButton browse_notice;
Button upload_notice;
final Calendar calendar = Calendar.getInstance();
TextView link,date;
Uri pdfUri;
ImageView dateCalender;
EditText notice_title;
FirebaseStorage firebaseStorage;
StorageReference storageReference;
int d,m,y;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_create_notice, container, false);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Notice's");
        browse_notice = root.findViewById(R.id.browse_notice);
        upload_notice = root.findViewById(R.id.upload_notice);
        link = root.findViewById(R.id.upload_link);
        date  = root.findViewById(R.id.notice_date);
        notice_title = root.findViewById(R.id.notice_title);
        dateCalender = root.findViewById(R.id.notice_calender);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        // set_date...
        dateCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        //Choose notice..
        browse_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseNotice();
            }
        });


        // upload_notice..
        upload_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadUri();
            }
        });
        return root;
    }

    private void downloadUri()
    {
         if (pdfUri!=null){
             ProgressDialog pd =  new ProgressDialog(getContext());
             pd.show();
             pd.setMessage("please wait....");
             StorageReference ref  = storageReference.child("Notice/*"+ UUID.randomUUID().toString());
             try {
                 ref.putFile(pdfUri)
                         .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                     @Override
                                     public void onSuccess(Uri uri) {
                                         String pdfUri = uri.toString();
                                         pdf(pdfUri);
                                         Fragment fragment = new Notice();
                                         FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                         fragmentTransaction.replace(R.id.FrameLayout,fragment)
                                                 .addToBackStack(null)
                                                 .commit();
                                         pd.dismiss();
                                     }
                                 });
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(getContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
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

    private void pdf(String noticeUri)
    {
      String Title  =notice_title.getText().toString();
      String Date = date.getText().toString();
        CreateNoticeModel createNoticeModel = new CreateNoticeModel(Title,Date,noticeUri);
        reference.child(Title).setValue(createNoticeModel);
    }

    private void browseNotice() {

        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityLaunch.launch(i);
    }
    ActivityResultLauncher<Intent>startActivityLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                pdfUri = result.getData().getData();
                link.setText(pdfUri.toString());
            }
        }
    });
    private void setDate()
    {
        d = calendar.get(calendar.DATE);
        m  = calendar.get(calendar.MONTH);
        y = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                  date.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },y,m,d);
       datePickerDialog.show();
    }
}