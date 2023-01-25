package com.example.teachermanagement.DashBoard.Notice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachermanagement.Fragment.Notice;
import com.example.teachermanagement.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class ViewNotice extends Fragment {
    String NoticeTitle,NoticeDate,Noticeurl;
    TextView noticeTitle;
    FirebaseDatabase database;
    DatabaseReference reference;
    PDFView webView;
    FloatingActionButton close;
    public ViewNotice(){}

    public ViewNotice(String NoticeTitle,String NoitceDate,String Noticeurl){
     this.NoticeTitle = NoticeTitle;
     this.NoticeDate = NoitceDate;
     this.Noticeurl = Noticeurl;
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
        View root= inflater.inflate(R.layout.fragment_view_notice, container, false);
        noticeTitle = root.findViewById(R.id.notice_pdf);
        noticeTitle.setText(NoticeTitle);
       webView = root.findViewById(R.id.pdfView);
       close  = root.findViewById(R.id.delete_notice);

        ProgressDialog pd = new ProgressDialog(getContext());
        //webView.getSettings().setJavaScriptEnabled(true);
        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Notice's");
       pd.show();
        pd.setMessage("Loading..");
         new RetrivePdfStream().execute(Noticeurl);
        pd.dismiss();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(NoticeTitle).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Fragment fragment = new Notice();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.FrameLayout,fragment)
                                        .addToBackStack(null).commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Falied !!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return root;
    }
    public class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {

                // adding url
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // if url connection response code is 200 means ok the execute
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            // if error return null
            catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        // Here load the pdf and dismiss the dialog box
        protected void onPostExecute(InputStream inputStream) {
            webView.fromStream(inputStream).load();
           // pd.dismiss();
        }
    }
}