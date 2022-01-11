package com.example.nutrident4mom.loginFirstTimeUserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.login.User;


public class InfoActivity_1 extends AppCompatActivity {
    public static String uid;

    private EditText Height;
    public String height, trimester;//String values for database storage
    private RadioGroup rgTrimester;
    private RadioButton rbFirst,rbSecond, rbThird;


    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_1);

        //define buttons
        //Get the gender by listening for RadioGroup clicks
        final Button btnNext=findViewById(R.id.btnNext);
        final RadioGroup rgTrimester = findViewById(R.id.rgTrimester);
        //Get the height
        Height = (EditText)findViewById(R.id.input_user_height);

        //get user info_1


        databaseReference = FirebaseDatabase.getInstance().getReference();


        rgTrimester.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                switch(checkedID){
                    case R.id.rbFirst: trimester = "First";break;
                    case R.id.rbSecond: trimester = "Second";break;
                    case R.id.rbThird: trimester = "Third";break;
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                userInfoAdd();
            }
        });
    }


    public void userInfoAdd(){

        height = Height.getText().toString();

        //if User input is not null
        if(height.isEmpty()){
            Height.setError("Height is required");
            Height.requestFocus();
            return;
        }
        if(trimester.isEmpty()){
            return;
        }
        userInfoAdd_toDatabase();
    }

    public void userInfoAdd_toDatabase(){

        //Get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user!=null){
                            //Write Gender Height to database

                            String newKey = databaseReference.child("Users").child(uid).push().getKey();

                            UserInfo userInfo = new UserInfo(trimester,height);

                            databaseReference.child("Users").child(uid).child(newKey).setValue(userInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(InfoActivity_1.this,"gender & height have saved in database successfully!",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(InfoActivity_1.this, InfoActivity_2.class);
                                            InfoActivity_1.this.startActivity(intent);
                                        }
                                    });

                        }else{
                            Toast.makeText(InfoActivity_1.this,"ERROR!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}

