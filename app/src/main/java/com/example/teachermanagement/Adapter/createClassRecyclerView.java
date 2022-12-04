package com.example.teachermanagement.Adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teachermanagement.DashBoard.Assignment.show_Assignment;
import com.example.teachermanagement.Model.CreateClassModel;
import com.example.teachermanagement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class createClassRecyclerView extends FirebaseRecyclerAdapter<CreateClassModel,createClassRecyclerView.myViewHolder>
{

   public createClassRecyclerView(@NonNull FirebaseRecyclerOptions<CreateClassModel> options) {
      super(options);
   }


   @Override
   protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull CreateClassModel model) {
      holder.Name.setText(model.getClassTeacherName());
      holder.Subject.setText(model.getClassSubject());
      holder.Year.setText(model.getClassYear());
      holder.btnClass.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final AlertDialog.Builder alert  = new AlertDialog.Builder(v.getContext());
            alert.setTitle("Welcome in the class");
            final View view = View.inflate(v.getContext(), R.layout.custom_alert_dailog_box,null);
            EditText ClassCode = view.findViewById(R.id.classcode);
            Button Yes = view.findViewById(R.id.Yes);
            Button No = view.findViewById(R.id.No);

            alert.setView(view);
            AlertDialog dialog = alert.create();
            dialog.show();
            Yes.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  String classCode = ClassCode.getText().toString().trim();
                  if (classCode.isEmpty()){
                     Toast.makeText(v.getContext(), "Enter ClassCode", Toast.LENGTH_SHORT).show();
                     dialog.dismiss();
                  }
                  else if (classCode.equals(model.getClassCode())){

                     Query query = FirebaseDatabase.getInstance().getReference("Personal Class")
                             .orderByChild("classCode").equalTo(classCode);
                     query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           if (snapshot.exists()){
//                              Fragment fragment = new ShowAssignment();
//                              FragmentManager fragmentManager = ;
//                              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                              fragmentTransaction.replace(R.id.FrameLayout,fragment)
//                                      .addToBackStack(null).commit();
                              Intent i = new Intent(v.getContext(), show_Assignment.class);
                             // dialog.dismiss();
                              v.getContext().startActivity(i);

                           }
                           else {
                              Toast.makeText(v.getContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                           Toast.makeText(v.getContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                     });
                  }
                  else if (!(classCode.equals(model.getClassCode()))){
                     Toast.makeText(v.getContext(), "Enter Valid  Class Code!!", Toast.LENGTH_SHORT).show();
                  }
               }
            });
            No.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  dialog.dismiss();
               }
            });
         }
      });
   }

   @NonNull
   @Override
   public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_xml_create_class,parent,false);
      return new myViewHolder(view);
   }

   public class myViewHolder extends RecyclerView.ViewHolder{
      TextView Name,Year,Subject,classcodes;
      FloatingActionButton btnClass;
      public myViewHolder(@NonNull View itemView) {
         super(itemView);
         Name  = itemView.findViewById(R.id.teachername);
         Subject = itemView.findViewById(R.id.subjectCode);
         Year  = itemView.findViewById(R.id.classname);
         btnClass = itemView.findViewById(R.id.btnclass);
         classcodes = itemView.findViewById(R.id.classCodes);
      }
   }
}
