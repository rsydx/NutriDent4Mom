package com.example.nutrident4mom.main;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.login.LoginActivity;
import com.example.nutrident4mom.login.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private FirebaseAuth mFirebaseAuth;
    Button logoutBtn,updateBtn;
    TextView userSetting_user_name,txtCalorie,txtCarbs,txtFat,txtProtein;
    public static String uid;
    public String username;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        logoutBtn = view.findViewById(R.id.logoutBtn);
        updateBtn = view.findViewById(R.id.updateBtn);
        userSetting_user_name = view.findViewById(R.id.userSetting_user_name);
        txtCalorie = view.findViewById(R.id.txtCalorie);
        txtCarbs = view.findViewById(R.id.txtCarbs);
        txtFat = view.findViewById(R.id.txtFat);
        txtProtein = view.findViewById(R.id.txtProtein);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), UpdateGoalActivity.class);
                startActivity(intent);
            }
        });


//
//        selectTimeBreakfastBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showTimePicker(1);
//            }
//        });
//        selectTimeLunchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showTimePicker(2);
//            }
//        });
//        selectTimeDinnerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showTimePicker(3);
//            }
//        });
//
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                signOutUser();
            }
        });
        getEmail_fromDatabase();
        return view;
    }

//    private void showTimePicker(int i) {
//
//        picker = new MaterialTimePicker.Builder()
//                .setTimeFormat(TimeFormat.CLOCK_12H)
//                .setHour(12)
//                .setMinute(0)
//                .setTitleText("Select Alarm Time")
//                .build();
//
//        picker.show(getActivity().getSupportFragmentManager(),"foxandroid");
//
//        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(i == 1){
//                    if (picker.getHour() > 12){
//
//                        txtBreakfast.setText(
//                                String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM"
//                        );
//
//                    }else {
//
//                        txtBreakfast.setText(picker.getHour()+" : " + picker.getMinute() + " AM");
//
//                    }
//
//                    calendar = Calendar.getInstance();
//                    calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
//                    calendar.set(Calendar.MINUTE,picker.getMinute());
//                    calendar.set(Calendar.SECOND,0);
//                    calendar.set(Calendar.MILLISECOND,0);
//                    System.out.println(picker.getMinute());
//                    setAlarm();
//                }
//                else if(i == 2){
//                    if (picker.getHour() > 12){
//
//                        txtLunch.setText(
//                                String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM"
//                        );
//
//                    }else {
//
//                        txtLunch.setText(picker.getHour()+" : " + picker.getMinute() + " AM");
//
//                    }
//
//                    calendar = Calendar.getInstance();
//                    calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
//                    calendar.set(Calendar.MINUTE,picker.getMinute());
//                    calendar.set(Calendar.SECOND,0);
//                    calendar.set(Calendar.MILLISECOND,0);
//
//                    setAlarm();
//                }
//                else{
//                    if (picker.getHour() > 12){
//
//                        txtDinner.setText(
//                                String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM"
//                        );
//
//                    }else {
//
//                        txtDinner.setText(picker.getHour()+" : " + picker.getMinute() + " AM");
//
//                    }
//
//                    calendar = Calendar.getInstance();
//                    calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
//                    calendar.set(Calendar.MINUTE,picker.getMinute());
//                    calendar.set(Calendar.SECOND,0);
//                    calendar.set(Calendar.MILLISECOND,0);
//                    System.out.println(calendar.getTimeZone());
//                    setAlarm();
//                }
//
//            }
//        });
//
//
//    }
//    private void cancelAlarm() {
//
//        Intent intent = new Intent(new Intent(getActivity(), AlarmReceiver.class));
//
//        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
//
//        if (alarmManager == null){
//
//            alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
//
//        }
//
//        alarmManager.cancel(pendingIntent);
//        Toast.makeText(getActivity(), "Reminder Cancelled", Toast.LENGTH_SHORT).show();
//    }

//    private void setAlarm() {
//
//        alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(new Intent(getActivity(), AlarmReceiver.class));
//
//        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
//
////        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
////                AlarmManager.INTERVAL_DAY,pendingIntent);
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
//
//        Toast.makeText(getActivity(), "Reminder set Successfully", Toast.LENGTH_SHORT).show();
//
//
//
//    }


    private void signOutUser(){
        Intent mainActivity = new Intent(getActivity(), LoginActivity.class);
        startActivity(mainActivity);
        getActivity().finish();
    }
    public void getEmail_fromDatabase(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //Get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Get the username of the current user from the database
        databaseReference.child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user!=null){

                            for(DataSnapshot d: snapshot.getChildren()){

                                String userInfo_Key = d.getKey();
                                if(userInfo_Key.equals("email")) {
                                    username = d.getValue().toString();
                                    userSetting_user_name.setText(username);
                                }
                                else if(userInfo_Key.equals("TDEE")) {
                                    txtCalorie.setText(d.getValue().toString());
                                }
                                else if(userInfo_Key.equals("carbs")) {
                                    txtCarbs.setText(d.getValue().toString());
                                }
                                else if(userInfo_Key.equals("fat")) {
                                    txtFat.setText(d.getValue().toString());
                                }
                                else if(userInfo_Key.equals("protein")) {
                                    txtProtein.setText(d.getValue().toString());
                                }
                            }
                        }else{
                            Toast.makeText(getActivity(),"display username ERROR!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}