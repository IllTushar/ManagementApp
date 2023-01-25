package com.example.teachermanagement.DashBoard.Books;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.teachermanagement.Adapter.BookRecyclerView;
import com.example.teachermanagement.Adapter.ClassScheduleRecyclerView;
import com.example.teachermanagement.Model.ClassScheduleModel;
import com.example.teachermanagement.Model.CreateBookModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class Books extends Fragment {
FloatingActionButton addBook,search;
RecyclerView recyclerView;
BookRecyclerView adapter;
EditText Search;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  root=  inflater.inflate(R.layout.fragment_books, container, false);
        recyclerView = root.findViewById(R.id.rec_view_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Search = root.findViewById(R.id.bookSearchOption);
        search = root.findViewById(R.id.bookCreate);

        try {

            FirebaseRecyclerOptions<CreateBookModel> options =
                    new FirebaseRecyclerOptions.Builder<CreateBookModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference("Book's"), CreateBookModel.class)
                            .build();
            adapter = new BookRecyclerView(options);
            recyclerView.setAdapter(adapter);


        }catch (Exception e){
            Log.d("##Checking",e.getMessage());
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SearchBook = Search.getText().toString().trim();
                SeachQuery(SearchBook);
            }
        });

        return  root;
    }

    private void SeachQuery(String searchBook) {
        FirebaseRecyclerOptions<CreateBookModel> options =
                new FirebaseRecyclerOptions.Builder<CreateBookModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Book's").orderByChild("title")
                                .startAt(searchBook), CreateBookModel.class)
                        .build();
        adapter = new BookRecyclerView(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        try{

            adapter.startListening();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        try {

            adapter.stopListening();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}