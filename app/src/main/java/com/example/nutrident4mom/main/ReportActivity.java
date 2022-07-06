package com.example.nutrident4mom.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.nutrident4mom.R;
import com.example.nutrident4mom.login.User;
import com.example.nutrident4mom.model.Food;
import com.example.nutrident4mom.model.UsersFood;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    public static String uid;
    public String weight, bmi, height;
    public String foodname, quantity, category;
    public String calorie, carbohydrate, fat, protein;
    AnyChartView anyChartView;
    private ArrayList<UsersFood> usersFoodArrayList = new ArrayList<>();




    DatabaseReference databaseReference;

    //bmi bar
    ImageView bubble_lean, bubble_normal, bubble_overweight, bubble_obese;
    TextView text_lean, text_normal, text_overweight, text_obese;
    ImageView bar_lean, bar_normal, bar_overweight, bar_obese;

    LinearLayout toMain;
    Button switchToCalorieView;
    Button switchToNutrientView;
    LinearLayout CalorieView;
    LinearLayout NutrientView;
    ImageView LabelCalorie;
    ImageView LabelNutrient;
    TextView percentBreakfast, percentLunch, percentDinner;
    TextView diversityNumber, energyStatus;

    TextView calorieTotal, calorieGoal, calorieStatus;
    TextView proteinTotal, proteinGoal, proteinStatus;
    TextView carbsTotal, carbsGoal, carbsStatus;
    TextView fatTotal, fatGoal, fatStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        anyChartView = findViewById(R.id.any_chart_view);

        toMain = (LinearLayout) findViewById(R.id.report_ll_quit);
        switchToCalorieView = (Button) findViewById(R.id.btn_report_calorie);
        switchToNutrientView = (Button) findViewById(R.id.btn_report_nutrient);
        CalorieView = (LinearLayout) findViewById(R.id.ll_report_calorie);
        NutrientView = (LinearLayout) findViewById(R.id.ll_report_nutrient);
        LabelCalorie = (ImageView) findViewById(R.id.label_report_calorie);
        LabelNutrient = (ImageView) findViewById(R.id.label_report_nutrient);

        percentBreakfast = (TextView) findViewById(R.id.report_display_percent_breakfast);
        percentLunch = (TextView) findViewById(R.id.report_display_percent_lunch);
        percentDinner = (TextView) findViewById(R.id.report_display_percent_dinner);
        energyStatus = (TextView) findViewById(R.id.report_display_energy_status);


        calorieTotal = (TextView) findViewById(R.id.report_display_nutrient_calorie_total);


        proteinTotal = (TextView) findViewById(R.id.report_display_nutrient_protein_total);


        carbsTotal = (TextView) findViewById(R.id.report_display_nutrient_carbohydrate_total);

        fatTotal = (TextView) findViewById(R.id.report_display_nutrient_fat_total);



        //get Calorie and Nutrient info from database
        initCalorie_initNutrient();


        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, MainActivity2.class);
                if (intent != null) {
                    ReportActivity.this.startActivity(intent);
                }
            }
        });

        switchToCalorieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NutrientView.setVisibility(View.GONE);
                LabelNutrient.setVisibility(View.INVISIBLE);
                LabelCalorie.setVisibility(View.VISIBLE);
                CalorieView.setVisibility(View.VISIBLE);
            }
        });

        switchToNutrientView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalorieView.setVisibility(View.GONE);
                LabelCalorie.setVisibility(View.INVISIBLE);
                LabelNutrient.setVisibility(View.VISIBLE);
                NutrientView.setVisibility(View.VISIBLE);

            }
        });

    }


    public void initCalorie_initNutrient() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        //[steps]
        //1. select * food information from UsersDB
        //2. select * from FoodDB where foodname = foodname from UsersDB
        //UsersFood.setUser3foodCount(0);


        //1. select * food information from UsersDB
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d: snapshot.getChildren()){
                            
                            if(d.exists()){
                                if(!d.getKey().equals("TDEE")&&!d.getKey().equals("carbs")&&!d.getKey().equals("email")&&!d.getKey().equals("fat")&&!d.getKey().equals("isAdmin")&&!d.getKey().equals("isDiabetic")&&!d.getKey().equals("protein")&&!d.getKey().equals("userId")){
                                    if(d.child("date").getValue(String.class).equals(date)) {

                                        UsersFood customFood = new UsersFood();
                                        foodname = d.child("foodname").getValue().toString();
                                        calorie = d.child("energy").getValue().toString();
                                        carbohydrate = d.child("carb").getValue().toString();
                                        fat = d.child("fat").getValue().toString();
                                        protein = d.child("protein").getValue().toString();
                                        category = d.child("category").getValue().toString();

                                        customFood.setFoodname(foodname);
                                        customFood.setEnergy(calorie);
                                        customFood.setCarb(carbohydrate);
                                        customFood.setFat(fat);
                                        customFood.setProtein(protein);
                                        customFood.setCategory(category);
                                        System.out.println("===alINFO=====" + customFood.getFoodname() + "+" + customFood.getEnergy() + "+" + customFood.getCarb() + "+" + customFood.getFat() + "+" + customFood.getProtein() + "+" + customFood.getCategory());


                                        customFood.incrementFoodCount();
                                        System.out.println("==============foodcount======" + customFood.getFoodCount());
                                        usersFoodArrayList.add(customFood);
                                        System.out.println("==============allFoodArrayList======" + usersFoodArrayList.size());
                                    }
                                }
                            }

                        }
                        System.out.println(usersFoodArrayList.size());
                        getAnalysis(usersFoodArrayList);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
                );

    }
    private void getAnalysis(ArrayList<UsersFood> usersFoodArrayList1) {
        double totCalorie = 0;
        double totCalorie_b=0;
        double totCalorie_l=0;
        double totCalorie_d=0;

        double allCalorieCount = 0;
        double allCalorieCount_b = 0;
        double allCalorieCount_l = 0;
        double allCalorieCount_d = 0;


        double totProtein=0,allProteinCount = 0;
        double totCarbs = 0, allCarbsCount =0;
        double totFat = 0, allFatCount =0;



        int typesCount = 0;

        //calculate calorie of all food

//        System.out.println("first food = "+usersFoodArrayList1.get(0).getFoodname());
//        System.out.println("second food = "+usersFoodArrayList1.get(1).getFoodname());
        for(int j = 0; j < usersFoodArrayList1.size(); j++){
            totCalorie = Double.parseDouble(usersFoodArrayList1.get(j).getEnergy());
            System.out.println("calories = "+totCalorie);

            totProtein = Double.parseDouble(usersFoodArrayList1.get(j).getProtein());
            System.out.println("protein = "+totProtein);

            totCarbs = Double.parseDouble(usersFoodArrayList1.get(j).getCarb());
            System.out.println("carb = "+totCarbs);

            totFat = Double.parseDouble(usersFoodArrayList1.get(j).getFat());
            System.out.println("fat = "+totFat);
            allCalorieCount = allCalorieCount + totCalorie;
            allProteinCount = allProteinCount + totProtein;
            allCarbsCount = allCarbsCount + totCarbs;
            allFatCount = allFatCount + totFat;

        }

        System.out.println("=====allCalorieCount=="+allCalorieCount);



        //setFoodIntake diversity
        System.out.println("=========typesCount=="+typesCount);
        System.out.println("=========allCalorieCount=="+allCalorieCount);

        energyStatus.setText(Double.toString(allCalorieCount));

        //setNutrient
        //calorie
        calorieTotal.setText(Double.toString(allCalorieCount));

        DecimalFormat df = new DecimalFormat("0.0");
        //Protein
        proteinTotal.setText(df.format(allProteinCount));

        //Carbs
        carbsTotal.setText(df.format(allCarbsCount));

        //Fat
        fatTotal.setText(df.format(allFatCount));

        //calculate calorie of breakfast
        for(int i = 0; i < usersFoodArrayList1.size(); i++) {
            String breakfast_category = usersFoodArrayList1.get(i).getCategory();

            if (breakfast_category.equals("breakfast")) {
                totCalorie_b = Double.parseDouble(usersFoodArrayList1.get(i).getEnergy());
                allCalorieCount_b = allCalorieCount_b + totCalorie_b;
                System.out.println("=====BREAKFASTCalorieCount==" + allCalorieCount_b);
            }
        }
        //calculate calorie of lunch
        for(int i = 0; i < usersFoodArrayList1.size(); i++) {
            String lunch_category = usersFoodArrayList1.get(i).getCategory();

            if (lunch_category.equals("lunch")) {
                totCalorie_l = Double.parseDouble(usersFoodArrayList1.get(i).getEnergy());
                allCalorieCount_l = allCalorieCount_l + totCalorie_l;
                System.out.println("=====LUNCHCalorieCount==" + allCalorieCount_l);
            }
        }
        //calculate calorie of dinner
        for(int i = 0; i < usersFoodArrayList1.size(); i++) {
            String dinner_category = usersFoodArrayList1.get(i).getCategory();

            if (dinner_category.equals("dinner")) {
                totCalorie_d = Double.parseDouble(usersFoodArrayList1.get(i).getEnergy());
                allCalorieCount_d = allCalorieCount_d + totCalorie_d;
                System.out.println("=====DINNERCalorieCount==" + allCalorieCount_d);
            }
        }

        //Calculate the proportion and status of breakfast,lunch,dinner,snacks
        df = new DecimalFormat("0.0");
        double d_breakfastPercent = allCalorieCount_b/allCalorieCount;
        double d_lunchPercent = allCalorieCount_l/allCalorieCount;
        double d_dinnerPercent = allCalorieCount_d/allCalorieCount;

        String[] mealTime = {"breakfast","lunch", "dinner"};
        double[] value = {allCalorieCount_b,allCalorieCount_l,allCalorieCount_d};


        d_breakfastPercent = d_breakfastPercent * 100;
        d_lunchPercent = d_lunchPercent * 100;
        d_dinnerPercent = d_dinnerPercent * 100;

        String str_breakfastPercent = df.format(d_breakfastPercent);//format返回String
        String str_lunchPercent = df.format(d_lunchPercent);
        String str_dinnerPercent = df.format(d_dinnerPercent);

        //breakfast：lunch：dinner ：other = 2.75  :3.5: 2.75：1
        if(d_breakfastPercent >0 || d_breakfastPercent ==0){
            System.out.println("=======d_breakfastPercent=="+d_breakfastPercent);
            percentBreakfast.setText("("+str_breakfastPercent+"%)");
        }
        //lunch
        if(d_lunchPercent >0 || d_lunchPercent ==0){
            System.out.println("=======d_lunchPercent=="+d_lunchPercent);

            percentLunch.setText("("+str_lunchPercent+"%)");
        }
        //dinner
        if(d_dinnerPercent >0 || d_dinnerPercent ==0){
            System.out.println("=======d_dinnerPercent=="+d_dinnerPercent);
            percentDinner.setText("("+str_dinnerPercent+"%)");
        }

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for(int i=0; i< mealTime.length;i++){
            System.out.println(mealTime[i]);
            System.out.println(value[i]);
            dataEntries.add(new ValueDataEntry(mealTime[i],value[i]));
        }
        pie.data(dataEntries);
        pie.title("Calories percentage");
        anyChartView.setChart(pie);
    }

}