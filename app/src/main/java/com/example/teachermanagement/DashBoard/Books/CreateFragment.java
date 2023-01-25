package com.example.teachermanagement.DashBoard.Books;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.Fragment.Notice;
import com.example.teachermanagement.Model.CreateBookModel;
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

import java.util.UUID;


public class CreateFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference reference;
    FloatingActionButton browse_book;
    Button upload_book;
    EditText bookTitle,author;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    TextView link;
    Uri pdfUri;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_create, container, false);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Book's");
        browse_book = root.findViewById(R.id.browse_book);
        upload_book = root.findViewById(R.id.upload_book);
        link = root.findViewById(R.id.upload_link);
        author = root.findViewById(R.id.author);
        bookTitle = root.findViewById(R.id.bookName);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        browse_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseBook();
            }
        });

        upload_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBook();
            }
        });
        return root;
    }

    private void uploadBook() {
        if (pdfUri!=null){
            ProgressDialog pd =  new ProgressDialog(getContext());
            pd.show();
            pd.setMessage("please wait....");
            StorageReference ref  = storageReference.child("Books/*"+ UUID.randomUUID().toString());
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

    private void pdf(String pdfUri) {
        String Title = bookTitle.getText().toString();
        String Author = author.getText().toString();
        CreateBookModel model= new CreateBookModel(Title,pdfUri,Author);
        reference.child(Title).setValue(model);
    }

    private void browseBook() {
        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityLaunch.launch(i);
    }
    ActivityResultLauncher<Intent> startActivityLaunch = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        pdfUri = result.getData().getData();
                        link.setText(pdfUri.toString());
                    }
                }
            });
}