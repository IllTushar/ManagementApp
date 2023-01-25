package com.example.teachermanagement.Student.StNotice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teachermanagement.DashBoard.Notice.ViewNotice;
import com.example.teachermanagement.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class viewNotice extends Fragment {

    String NoticeTitle,NoticeDate,Noticeurl;
    TextView noticeTitle;
    FirebaseDatabase database;
    DatabaseReference reference;
    PDFView webView;
    public viewNotice(){}
    public viewNotice(String NoticeTitle,String NoitceDate,String Noticeurl){
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
        View root= inflater.inflate(R.layout.fragment_view_notice2, container, false);
        noticeTitle = root.findViewById(R.id.stnotice_pdf);
        noticeTitle.setText(NoticeTitle);
        webView = root.findViewById(R.id.pdfView);

        ProgressDialog pd = new ProgressDialog(getContext());
        //webView.getSettings().setJavaScriptEnabled(true);
        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Notice's");
        pd.show();
        pd.setMessage("Loading..");
        new viewNotice.RetrivePdfStream().execute(Noticeurl);
        pd.dismiss();

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