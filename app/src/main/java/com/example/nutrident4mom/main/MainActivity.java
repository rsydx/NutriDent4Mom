package com.example.nutrident4mom.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.nutrident4mom.R;
//import com.example.nutrident4mom.addMeal.ManuallyInputActivity;
import com.example.nutrident4mom.login.User;
//import com.example.nutrident4mom.news.NewsAdapter;
//import com.example.nutrident4mom.news.NewsBean;
//import com.example.nutrident4mom.news.NewsUtils;
//import com.example.nutrident4mom.userSetting.UserActivity;


public class MainActivity extends AppCompatActivity{

    public static String uid;

    public TextView textView_bmi,textView_weight,textView_name;
    public ImageView imageView_userImage;
    public String weight,gender,bmi,username;//get the height and weight from the database

    public DatabaseReference databaseReference;
    public FirebaseStorage storage;
    public StorageReference storageReference;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

//        //Get all news data packaged with list
//        ArrayList<NewsBean> allNews = NewsUtils.getAllNews(mContext);
//        ListView lv_news = (ListView) findViewById(R.id.lv_news);
//        //Create an adapter to listview
//        NewsAdapter newsAdapter = new NewsAdapter(mContext, allNews);
//        lv_news.setAdapter(newsAdapter);
//        //Set the click event of the listview item
//        lv_news.setOnItemClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        textView_name=(TextView) findViewById(R.id.main_display_user_name);
        textView_weight = (TextView) findViewById(R.id.main_display_user_weight);
        textView_bmi = (TextView) findViewById(R.id.main_display_user_BMI);

        getUsername_fromDatabse();
        getWeight_BMI_fromDatabase();


        //define button
        final Button mtReportBtn=findViewById(R.id.btn_main_to_report);
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

//        mtReportBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
//                if (intent != null) {
//                    MainActivity.this.startActivity(intent);
//                }
//            }
//        });
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_add:
                        userChoice();
                        break;
                    case R.id.navigation_user:
//                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                        break;
                }
                return true;
            }
        });
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position,
//                            long id) {
//
//        NewsBean bean = (NewsBean) parent.getItemAtPosition(position);
//
//        String url = bean.news_url;
//
//        //open browser
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        startActivity(intent);
//
//    }

    public void userChoice(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(getDrawable(R.drawable.icon_alert));
        builder.setTitle("Record Method");
        final String []items=new String[]{"Manual"};
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            //'which' refers to the subscript of the item selected by the user
            //'dialog' triggers this method
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
//                    onManualClick();
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

//    public void onManualClick(){
//        Intent intent = new Intent(MainActivity.this, ManuallyInputActivity.class);
//        if (intent != null) {
//            startActivity(intent);
//        }
//    }



    public void getUsername_fromDatabse(){
        //get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Get the username of the current user from the database
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user!=null){

                            for(DataSnapshot d: snapshot.getChildren()){

                                String userInfo_Key = d.getKey();
                                if(userInfo_Key.equals("username")) {
                                    username = d.getValue().toString();
                                    textView_name.setText(username);
                                }
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"displayUsername ERROR!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void getWeight_BMI_fromDatabase(){
        //get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Get the weight and height of the current user from the database
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user!=null){

                            for(DataSnapshot d: snapshot.getChildren()){

                                String userInfo_Key = d.getKey();
                                if(!userInfo_Key.equals("userID") && !userInfo_Key.equals("username") && !userInfo_Key.equals("email") && !userInfo_Key.equals("password")&& !userInfo_Key.equals("confirm_password")&& !userInfo_Key.equals("security")) {

                                    databaseReference.child("Users").child(uid)
                                            .child(userInfo_Key).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot d: dataSnapshot.getChildren()) {

                                                String d_Key = d.getKey();
                                                if(d_Key.equals("weight")){
                                                    weight = d.getValue().toString();

                                                    Double d_weight = Double.parseDouble(weight);
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    String str_weight = df.format(d_weight);
                                                    textView_weight.setText(str_weight);

                                                }

                                                if(d_Key.equals("bmi")){
                                                    bmi = d.getValue().toString();

                                                    Double d_bmi = Double.parseDouble(bmi);
                                                    DecimalFormat df = new DecimalFormat(("0.00"));
                                                    String str_bmi = df.format(d_bmi);
                                                    textView_bmi.setText(str_bmi);

                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"displayHeight_BMI ERROR!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
