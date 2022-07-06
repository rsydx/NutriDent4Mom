package com.example.nutrident4mom.main;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.adapter.RecyclerFoodAdapter;
import com.example.nutrident4mom.adapter.recfragment;
import com.example.nutrident4mom.login.LoginActivity;
import com.example.nutrident4mom.model.Food;
import com.example.nutrident4mom.model.UsersFood;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{
    TextView addBreakfast, addLunch, addDinner, txtReport;
    CircularProgressBar circularProgressBar;
    ProgressBar progress_carbs, progress_protein, progress_fat;
    NestedScrollView nestedScrollView;
    ListView listView_breakfast,listView_lunch,listView_dinner;

    BottomNavigationView nav;

    ProfileFragment profileFragment;
    EducationalContentFragment educationalContentFragment;
    FragmentTransaction ft;
    ChatFragment chatFragment;
    ArrayList<String> arrayList1 = new ArrayList<String>();
    ArrayList<String> arrayList2 = new ArrayList<String>();
    ArrayList<String> arrayList3 = new ArrayList<String>();
    private ArrayList<UsersFood> usersFoodArrayList = new ArrayList<>();
    public static String uid;
    public String calorie, carbohydrate, fat, protein, foodname;
    ArrayAdapter<String> arrayAdapter1;
    ArrayAdapter<String> arrayAdapter2;
    ArrayAdapter<String> arrayAdapter3;
    public DatabaseReference databaseReference;

    TextView textViewMyCal, textViewAteCal, textViewMyCarbs, textViewAteCarbs,
            textViewMyProtein, textViewAteProtein, textViewMyFat, textViewAteFat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_nutrition);

        textViewMyCal = findViewById(R.id.my_cal);
        textViewAteCal = findViewById(R.id.ate_cal);

        textViewMyCarbs = findViewById(R.id.my_carbs);
        textViewAteCarbs = findViewById(R.id.ate_carbs);

        textViewMyProtein = findViewById(R.id.my_protein);
        textViewAteProtein = findViewById(R.id.ate_protein);

        textViewMyFat = findViewById(R.id.my_fat);
        textViewAteFat = findViewById(R.id.ate_fat);
        txtReport = findViewById(R.id.txtReport);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        circularProgressBar = findViewById(R.id.progress_circular);
        progress_protein = findViewById(R.id.progress_protein);
        progress_fat = findViewById(R.id.progress_fat);
        progress_carbs = findViewById(R.id.progress_carbs);
        addBreakfast = findViewById(R.id.add_breakfast);
        addLunch = findViewById(R.id.add_lunch);
        addDinner = findViewById(R.id.add_dinner);

        nestedScrollView = findViewById(R.id.nested_scroll);

        nav = findViewById(R.id.bottom_nav);
        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        educationalContentFragment = new EducationalContentFragment();

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, educationalContentFragment);
        ft.add(R.id.fragment_container, chatFragment);
        ft.add(R.id.fragment_container, profileFragment);
        ft.hide(educationalContentFragment);
        ft.hide(chatFragment);
        ft.hide(profileFragment);
        ft.commit();
        addBreakfast.setOnClickListener(this);
        addLunch.setOnClickListener(this);
        addDinner.setOnClickListener(this);
//        setAdapters();
        //get breakfast data
        listView_breakfast = (ListView) findViewById(R.id.listView_breakfast);
        arrayAdapter1 = new ArrayAdapter<String>(MainActivity2.this,android.R.layout.simple_list_item_1,arrayList1);
        listView_breakfast.setAdapter(arrayAdapter1);
        getBreakfastData();

        //get lunch data
        listView_lunch = (ListView) findViewById(R.id.listView_lunch);
        arrayAdapter2 = new ArrayAdapter<String>(MainActivity2.this,android.R.layout.simple_list_item_1,arrayList2);
        listView_lunch.setAdapter(arrayAdapter2);
        getLunchData();

        //get dinner data
        listView_dinner = (ListView) findViewById(R.id.listView_dinner);
        arrayAdapter3 = new ArrayAdapter<String>(MainActivity2.this,android.R.layout.simple_list_item_1,arrayList3);
        listView_dinner.setAdapter(arrayAdapter3);
        getDinnerData();
        //delete item
        setupListViewListener();
        reloadCal();


        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        ft.hide(educationalContentFragment);
//                        ft.hide(goalCalculatorFragment);
                        ft.hide(chatFragment);
                        ft.hide(profileFragment);
                        break;
                    case R.id.navigation_educational:
//                        ft.hide(goalCalculatorFragment);
                        ft.show(educationalContentFragment);
                        ft.hide(chatFragment);
                        ft.hide(profileFragment);
                        break;
                    case R.id.navigation_user:
//                        ft.hide(goalCalculatorFragment);
                        ft.hide(educationalContentFragment);
                        ft.hide(chatFragment);
                        ft.show(profileFragment);
                        break;
                    case R.id.navigation_chat:
                        ft.hide(educationalContentFragment);
//                        ft.hide(goalCalculatorFragment);
                        ft.hide(profileFragment);
                        ft.show(chatFragment);

                        break;
                    default:
                        break;
                }
                ft.commit();
                return true;
            }
        });

        txtReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, ReportActivity.class));

            }
        });

    }


private void setupListViewListener() {
    listView_breakfast.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                position, long rowId)
        {
            Log.i("MyFoodList", "Long Clicked item " + position);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity2.this);
            builder.setTitle(R.string.dialog_delete_title_2)
                    .setMessage(R.string.dialog_delete_msg_2)
                    .setPositiveButton(R.string.delete_2, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //get click position food name
                                    String getItem = arrayList1.get(position);
                                    String getFoodName = getItem;

                                    //remove food name from database
                                    deleteBreakfastData(getFoodName);

                                    // Remove item from the ArrayList
                                    arrayList1.remove(position);

                                    // Notify listView adapter to update the list
                                    arrayAdapter1.notifyDataSetChanged();

                                }
                            })
                    .setNegativeButton(R.string.cancel_2, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User cancelled the dialog
                                    // Nothing happens
                                }
                            });
            builder.create().show();
            return true;
        }
    });

    listView_lunch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                position, long rowId)
        {
            Log.i("MyFoodList", "Long Clicked item " + position);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity2.this);
            builder.setTitle(R.string.dialog_delete_title_2)
                    .setMessage(R.string.dialog_delete_msg_2)
                    .setPositiveButton(R.string.delete_2, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //get click position food name
                                    String getItem = arrayList2.get(position);
//                                    String str = getItem.substring(0,getItem.length()-7);
                                    String getFoodName = getItem;

                                    //remove food name from database
                                    deleteLunchData(getFoodName);

                                    // Remove item from the ArrayList
                                    arrayList2.remove(position);

                                    // Notify listView adapter to update the list
                                    arrayAdapter2.notifyDataSetChanged();

                                }
                            })
                    .setNegativeButton(R.string.cancel_2, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User cancelled the dialog
                                    // Nothing happens
                                }
                            });
            builder.create().show();
            return true; }
    });

    listView_dinner.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                position, long rowId)
        {
            Log.i("MyFoodList", "Long Clicked item " + position);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity2.this);
            builder.setTitle(R.string.dialog_delete_title_2)
                    .setMessage(R.string.dialog_delete_msg_2)
                    .setPositiveButton(R.string.delete_2, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //get click position food name
                                    String getItem = arrayList3.get(position);
//                                    String str = getItem.substring(0,getItem.length()-7);
                                    String getFoodName = getItem;

                                    //remove food name from database
                                    deleteDinnerData(getFoodName);

                                    // Remove item from the ArrayList
                                    arrayList3.remove(position);

                                    // Notify listView adapter to update the list
                                    arrayAdapter3.notifyDataSetChanged();

                                }
                            })
                    .setNegativeButton(R.string.cancel_2, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User cancelled the dialog
                                    // Nothing happens
                                }
                            });
            builder.create().show();
            return true; }
    });
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Food food = data.getParcelableExtra("food");
            if (requestCode == 1 && resultCode == RESULT_OK)
                addFood(food, requestCode);

            if (requestCode == 2 && resultCode == RESULT_OK)
                addFood(food, requestCode);

            if (requestCode == 3 && resultCode == RESULT_OK)
                addFood(food, requestCode);
        }
    }

//    private void setAdapters() {
//
//
//
//
//
//    }

    private void addFood(Food food, int requestCode) {
        TextView textViewMyCal = findViewById(R.id.my_cal); // from text
        TextView textViewAteCal = findViewById(R.id.ate_cal); //from analysis

        switch (requestCode) {
            case 1:
                UserFoodAdd_toDatabase(food.getDesc().toString(), Double.toString(food.getEnergy()) , Double.toString(food.getProtein()), Double.toString(food.getFat()), Double.toString(food.getCarb()), "breakfast");
                break;
            case 2:
                UserFoodAdd_toDatabase(food.getDesc().toString(), Double.toString(food.getEnergy()) , Double.toString(food.getProtein()), Double.toString(food.getFat()), Double.toString(food.getCarb()), "lunch");
                break;
            case 3:
                UserFoodAdd_toDatabase(food.getDesc().toString(), Double.toString(food.getEnergy()) , Double.toString(food.getProtein()), Double.toString(food.getFat()), Double.toString(food.getCarb()), "dinner");
                break;
        }

    }

    public void UserFoodAdd_toDatabase(String name, String energy, String protein, String fat, String carb, String category){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //get userID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //A new node is created for each food information stored
                        String newKey = databaseReference.child(uid).push().getKey();

                        UsersFood usersFood = new UsersFood( name,  energy,  protein,  fat,  carb,  category,date);

                        databaseReference.child(uid).child(newKey).setValue(usersFood)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(MainActivity2.this, "Insert food succesfully", Toast.LENGTH_SHORT).show();

                                    }
                    });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void getBreakfastData(){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        //get userID
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(uid)
                .orderByChild("category")
                .equalTo("breakfast")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot data: snapshot.getChildren()){
                                if(data.child("date").getValue(String.class).equals(date)) {
                                    String name_quantity = data.child("foodname").getValue(String.class);
                                    System.out.println(name_quantity);
                                    arrayList1.add(name_quantity);
                                    arrayAdapter1.notifyDataSetChanged();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    public void deleteBreakfastData(final String foodName){
        //get userID

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(uid)
                .orderByChild("category")
                .equalTo("breakfast")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot data: snapshot.getChildren()){
                                if (foodName.equals(data.child("foodname").getValue(String.class))) {
                                    data.getRef().removeValue();
//                                    System.out.println(data.getRef());

                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void getLunchData(){
        //get userID
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(uid)
                .orderByChild("category")
                .equalTo("lunch")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot data: snapshot.getChildren()){
                                if(data.child("date").getValue(String.class).equals(date)) {
                                    UsersFood lunch = data.getValue(UsersFood.class);
                                    String name_quantity = lunch.foodname;
                                    arrayList2.add(name_quantity);
                                    arrayAdapter2.notifyDataSetChanged();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    public void deleteLunchData(final String foodName){
        //get userID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child(uid)
                .orderByChild("category")
                .equalTo("lunch")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot data: snapshot.getChildren()){
                                if (foodName.equals(data.child("foodname").getValue(String.class))) {
                                    data.getRef().removeValue();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    public void getDinnerData(){
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //get userID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(uid)
                .orderByChild("category")
                .equalTo("dinner")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot data: snapshot.getChildren()){
                                if(data.child("date").getValue(String.class).equals(date)) {
                                    UsersFood dinner = data.getValue(UsersFood.class);
                                    String name_quantity = dinner.foodname;
                                    arrayList3.add(name_quantity);
                                    arrayAdapter3.notifyDataSetChanged();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    public void deleteDinnerData(final String foodName){
        //get userID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child(uid)
                .orderByChild("category")
                .equalTo("dinner")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot data: snapshot.getChildren()){
                                if (foodName.equals(data.child("foodname").getValue(String.class))) {
                                    data.getRef().removeValue();

                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }



    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.add_breakfast:
                intent = new Intent(MainActivity2.this, FoodSearch.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.add_lunch:
                intent = new Intent(MainActivity2.this, FoodSearch.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.add_dinner:
                intent = new Intent(MainActivity2.this, FoodSearch.class);
                startActivityForResult(intent, 3);
                break;
        }
    }


    private void reloadCal() {

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String TDEE, protein,fat,carbs;
                        TDEE = snapshot.child("TDEE").getValue().toString();
                        protein = snapshot.child("protein").getValue().toString();
                        carbs = snapshot.child("carbs").getValue().toString();
                        fat = snapshot.child("fat").getValue().toString();
                        textViewMyCal.setText(TDEE);
                        textViewMyProtein.setText(protein);
                        textViewMyFat.setText(fat);
                        textViewMyCarbs.setText(carbs);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for(DataSnapshot d: snapshot.getChildren()){

                                                            if(d.exists()){
                                                                if(!d.getKey().equals("TDEE")&&!d.getKey().equals("carbs")&&!d.getKey().equals("email")&&!d.getKey().equals("fat")&&!d.getKey()
                                                                        .equals("isAdmin")&&!d.getKey().equals("isDiabetic")&&!d.getKey().equals("protein")&&!d.getKey().equals("userId")){
                                                                    if(d.child("date").getValue(String.class).equals(date)) {

                                                                        UsersFood customFood = new UsersFood();
                                                                        calorie = d.child("energy").getValue().toString();
                                                                        carbohydrate = d.child("carb").getValue().toString();
                                                                        fat = d.child("fat").getValue().toString();
                                                                        protein = d.child("protein").getValue().toString();

                                                                        customFood.setEnergy(calorie);
                                                                        customFood.setCarb(carbohydrate);
                                                                        customFood.setFat(fat);
                                                                        customFood.setProtein(protein);
//                                                                    customFood.setCategory(category);
//                                                                    System.out.println("===alINFO====="+customFood.getFoodname()+"+"+customFood.getEnergy()+"+"+customFood.getCarb()+"+"+customFood.getFat()+"+"+customFood.getProtein()+"+"+customFood.getCategory());

                                                                        usersFoodArrayList.add(customFood);
                                                                    }
                                                                }
                                                            }

                                                        }
                                                        double totCalorie = 0;
                                                        double allCalorieCount = 0;
                                                        double totProtein=0,allProteinCount = 0;
                                                        double totCarbs = 0, allCarbsCount =0;
                                                        double totFat = 0, allFatCount =0;

                                                        int typesCount = 0;

                                                        //calculate calorie of all food

                                                        for(int j = 0; j < usersFoodArrayList.size(); j++){
                                                            totCalorie = Double.parseDouble(usersFoodArrayList.get(j).getEnergy());
                                                            System.out.println("calories = "+totCalorie);

                                                            totProtein = Double.parseDouble(usersFoodArrayList.get(j).getProtein());
                                                            System.out.println("protein = "+totProtein);

                                                            totCarbs = Double.parseDouble(usersFoodArrayList.get(j).getCarb());
                                                            System.out.println("carb = "+totCarbs);

                                                            totFat = Double.parseDouble(usersFoodArrayList.get(j).getFat());
                                                            System.out.println("fat = "+totFat);
                                                            allCalorieCount = allCalorieCount + totCalorie;
                                                            allProteinCount = allProteinCount + totProtein;
                                                            allCarbsCount = allCarbsCount + totCarbs;
                                                            allFatCount = allFatCount + totFat;

                                                        }




                                                        //setFoodIntake diversity


                                                        //setNutrient
                                                        //calorie

//                                                        DecimalFormat df = new DecimalFormat("0.0");
                                                        //Protein

                                                        //Carbs

                                                        //Fat
                                                        //calories
                                                        int allAteCal = (int)(allCalorieCount);
                                                        textViewAteCal.setText(""+ allAteCal);
                                                        int myCal = Integer.parseInt(textViewMyCal.getText().toString());
                                                        float progress = ((float) allAteCal / (float) myCal) * 100;
                                                        circularProgressBar.setProgressWithAnimation(progress, 1000l); // =1s
                                                        if(allAteCal > myCal){
                                                            Toast.makeText(MainActivity2.this, "You have exceeded your daily calories goal", Toast.LENGTH_SHORT).show();
                                                        }



                                                        //protein
                                                        int myProtein = Integer.parseInt(textViewMyProtein.getText().toString());
                                                        int allAteProtein = (int)(allProteinCount);
                                                        textViewAteProtein.setText("" + allAteProtein);
                                                        float progressProtein = ((float) allAteProtein / (float) myProtein) * 100;
                                                        ObjectAnimator.ofInt(progress_protein, "progress", (int) progressProtein).setDuration(1000).start();
                                                        if(allAteProtein > myProtein){
                                                            Toast.makeText(MainActivity2.this, "You have exceeded your daily protein goal", Toast.LENGTH_SHORT).show();
                                                        }

                                                        //fat
                                                        int myFat = Integer.parseInt(textViewMyFat.getText().toString());
                                                        int allAteFat = (int)allFatCount;
                                                        textViewAteFat.setText("" + allAteFat);
                                                        float progressFat = ((float) allAteFat / (float) myFat) * 100;
                                                        ObjectAnimator.ofInt(progress_fat, "progress", (int) progressFat).setDuration(1000).start();
                                                        if(allAteFat > myFat){
                                                            Toast.makeText(MainActivity2.this, "You have exceeded your daily fat goal", Toast.LENGTH_SHORT).show();
                                                        }

                                                        //carbs
                                                        int myCarbs = Integer.parseInt(textViewMyCarbs.getText().toString());
                                                        int allAteCarbs = (int)allCarbsCount;
                                                        textViewAteCarbs.setText("" + allAteCarbs);
                                                        float progressCarbs = ((float) allAteCarbs / (float) myCarbs) * 100;
                                                        ObjectAnimator.ofInt(progress_carbs, "progress", (int) progressCarbs).setDuration(1000).start();
                                                        if(allAteCarbs > myCarbs){
                                                            Toast.makeText(MainActivity2.this, "You have exceeded your daily carbs goal", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                }
                );


    }


}
