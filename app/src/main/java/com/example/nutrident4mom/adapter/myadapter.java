package com.example.nutrident4mom.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nutrident4mom.R;
import com.example.nutrident4mom.login.LoginActivity;
import com.example.nutrident4mom.main.AdminActivity;
import com.example.nutrident4mom.main.ContentActivity;
import com.example.nutrident4mom.main.GoalCalculator;
import com.example.nutrident4mom.model.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myadapter extends FirebaseRecyclerAdapter<Model,myadapter.myviewholder>
{


    public myadapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, final int position, @NonNull final Model model) {
        holder.nametext.setText(model.getName());
        holder.coursetext.setText(model.getCourse());
        //holder.emailtext.setText(model.getEmail());
        Glide.with(holder.img1.getContext()).load(model.getPurl()).into(holder.img1);


        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ContentActivity.class);
                intent.putExtra("nameholder",model.getName());
                intent.putExtra("courseholder",model.getCourse());
                intent.putExtra("emailholder",model.getEmail());
                intent.putExtra("imagegholder",model.getPurl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);


//                AppCompatActivity activity=(AppCompatActivity)view.getContext();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new descfragment(model.getName(),model.getCourse(),model.getEmail(),model.getPurl())).addToBackStack(null).commit();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nametext.getContext());
                builder.setTitle("Confirm delete content?");
                builder.setMessage("Deleted content can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("students").child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.nametext.getContext(), "Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        ImageButton btnDelete;
        TextView nametext,coursetext,emailtext;
        FirebaseUser firebaseUser;
        DatabaseReference reference;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            img1=itemView.findViewById(R.id.img1);
            nametext=itemView.findViewById(R.id.nametext);
            coursetext=itemView.findViewById(R.id.coursetext);
            //emailtext=itemView.findViewById(R.id.emailtext);
            if (firebaseUser != null) {
                String userId = firebaseUser.getUid();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child("isAdmin").getValue(Boolean.class) == true){
                            btnDelete.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }

}