//package com.example.nutrident4mom.main;
//
//import static android.content.Context.MODE_PRIVATE;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.widget.NestedScrollView;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.animation.ObjectAnimator;
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.example.nutrident4mom.R;
//import com.example.nutrident4mom.adapter.RecyclerFoodAdapter;
//import com.example.nutrident4mom.adapter.recfragment;
//import com.example.nutrident4mom.model.Food;
//import com.example.nutrident4mom.model.UsersFood;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
//import com.mikhaellopez.circularprogressbar.CircularProgressBar;
//
//import java.util.ArrayList;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AddNutritionFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AddNutritionFragment extends Fragment implements View.OnClickListener {
//    ArrayList<Food> foodsBreakfast = new ArrayList<>();
//    ArrayList<Food> foodsLunches = new ArrayList<>();
//    ArrayList<Food> foodsDinner = new ArrayList<>();
//    public DatabaseReference databaseReference;
//    RecyclerFoodAdapter breakfastAdapter;
//    RecyclerFoodAdapter lunchAdapter;
//    RecyclerFoodAdapter dinnerAdapter;
//    RecyclerView recyclerViewBreakfast;
//    RecyclerView recyclerViewLunch;
//    RecyclerView recyclerViewDinner;
//    CircularProgressBar circularProgressBar;
//    ProgressBar progress_carbs, progress_protein, progress_fat;
//    NestedScrollView nestedScrollView;
//
//
//    TextView textViewMyCal, textViewAteCal, textViewResult, textViewMyCarbs, textViewAteCarbs,
//            textViewMyProtein, textViewAteProtein, textViewMyFat, textViewAteFat, addBreakfast,addLunch, addDinner;
//    // Inflate the layout for this fragment
//
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
//
//
//
//    public AddNutritionFragment() {
//
//    }
//    public static AddNutritionFragment newInstance(String param1, String param2) {
//        AddNutritionFragment fragment = new AddNutritionFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view=inflater.inflate(R.layout.fragment_add_nutrition, container, false);
//
//        textViewMyCal = view.findViewById(R.id.my_cal);
//        textViewAteCal = view.findViewById(R.id.ate_cal);
//
//        textViewMyCarbs = view.findViewById(R.id.my_carbs);
//        textViewAteCarbs = view.findViewById(R.id.ate_carbs);
//
//        textViewMyProtein = view.findViewById(R.id.my_protein);
//        textViewAteProtein = view.findViewById(R.id.ate_protein);
//
//        textViewMyFat = view.findViewById(R.id.my_fat);
//        textViewAteFat = view.findViewById(R.id.ate_fat);
//        //KIV
//        reloadCal();
//
//        circularProgressBar = view.findViewById(R.id.progress_circular);
//        progress_protein = view.findViewById(R.id.progress_protein);
//        progress_fat = view.findViewById(R.id.progress_fat);
//        progress_carbs = (view.findViewById(R.id.progress_carbs));
//        recyclerViewBreakfast = view.findViewById(R.id.recyclerView_Breakfast);
//        recyclerViewLunch = view.findViewById(R.id.recyclerView_Lunch);
//        recyclerViewDinner = view.findViewById(R.id.recyclerView_Dinner);
//        addBreakfast = view.findViewById(R.id.add_breakfast);
//        addLunch = view.findViewById(R.id.add_lunch);
//        addDinner = view.findViewById(R.id.add_dinner);
//
//        nestedScrollView = view.findViewById(R.id.nested_scroll);
//
//
//        addBreakfast.setOnClickListener((View.OnClickListener) this);
//        addLunch.setOnClickListener((View.OnClickListener) this);
//        addDinner.setOnClickListener((View.OnClickListener) this);
//
//
//        setAdapters();
//
//        breakfastAdapter.setOnItemClickListener(new RecyclerFoodAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(getActivity(), EditPickedFood.class);
//                intent.putExtra("food", foodsBreakfast.get(position));
//                intent.putExtra("edit", true);
//                intent.putExtra("position", position);
//                startActivityForResult(intent, 4);
//            }
//        });
//        breakfastAdapter.setOnLongItemClickListener(new RecyclerFoodAdapter.OnLongItemClickListener() {
//            @Override
//            public void onLongItemClick(int position) {
//                alertDeleteFood(position, 1);
//            }
//        });
//
//
//        lunchAdapter.setOnItemClickListener(new RecyclerFoodAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(getActivity(), EditPickedFood.class);
//                intent.putExtra("food", foodsLunches.get(position));
//                intent.putExtra("edit", true);
//                intent.putExtra("position", position);
//                startActivityForResult(intent, 5);
//            }
//        });
//        lunchAdapter.setOnLongItemClickListener(new RecyclerFoodAdapter.OnLongItemClickListener() {
//            @Override
//            public void onLongItemClick(int position) {
//                alertDeleteFood(position, 2);
//
//            }
//        });
//
//
//        dinnerAdapter.setOnItemClickListener(new RecyclerFoodAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(getActivity(), EditPickedFood.class);
//                intent.putExtra("food", foodsDinner.get(position));
//                intent.putExtra("edit", true);
//                intent.putExtra("position", position);
//                startActivityForResult(intent, 6);
//            }
//        });
//        dinnerAdapter.setOnLongItemClickListener(new RecyclerFoodAdapter.OnLongItemClickListener() {
//            @Override
//            public void onLongItemClick(int position) {
//                alertDeleteFood(position, 3);
//            }
//        });
//
//
//
//        return view;
//
//    }
//
//    private void editFood(Food food, int position, int requestCode) {
//
//        int newFoodCal = (int) food.getEnergy();
//        int newFoodProtein = (int) food.getProtein();
//        int newFoodFat = (int) food.getFat();
//        int newFoodCarbs = (int) food.getCarb();
//
//        int myCal = Integer.parseInt(textViewMyCal.getText().toString());
//        int myProtein = Integer.parseInt(textViewMyProtein.getText().toString());
//        int myFat = Integer.parseInt(textViewMyFat.getText().toString());
//        int myCarbs = Integer.parseInt(textViewMyCarbs.getText().toString());
//
//        int ateCal = Integer.parseInt(textViewAteCal.getText().toString());
//        int ateProtein = Integer.parseInt(textViewAteProtein.getText().toString());
//        int ateFat = Integer.parseInt(textViewAteFat.getText().toString());
//        int ateCarbs = Integer.parseInt(textViewAteCarbs.getText().toString());
//
//        int currentFoodCal=0,currentFoodProtein=0,currentFoodFat=0,currentFoodCarbs=0;
//
//        switch (requestCode) {
//            case 4:
//                currentFoodCal = (int) foodsBreakfast.get(position).getEnergy();
//                currentFoodProtein = (int) foodsBreakfast.get(position).getProtein();
//                currentFoodFat = (int) foodsBreakfast.get(position).getFat();
//                currentFoodCarbs = (int) foodsBreakfast.get(position).getCarb();
//
//                breakfastAdapter.notifyItemChanged(position);
//                break;
//            case 5:
//                currentFoodCal = (int) foodsLunches.get(position).getEnergy();
//                currentFoodProtein = (int) foodsLunches.get(position).getProtein();
//                currentFoodFat = (int) foodsLunches.get(position).getFat();
//                currentFoodCarbs = (int) foodsLunches.get(position).getCarb();
//
//                lunchAdapter.notifyItemChanged(position);
//                break;
//            case 6:
//                currentFoodCal = (int) foodsDinner.get(position).getEnergy();
//                currentFoodProtein = (int) foodsDinner.get(position).getProtein();
//                currentFoodFat = (int) foodsDinner.get(position).getFat();
//                currentFoodCarbs = (int) foodsDinner.get(position).getCarb();
//
//                dinnerAdapter.notifyItemChanged(position);
//                break;
//
//        }
//
//
//        ateCal = ateCal - currentFoodCal + newFoodCal;
//        ateProtein = ateProtein - currentFoodProtein + newFoodProtein;
//        ateFat = ateFat - currentFoodFat + newFoodFat;
//        ateCarbs = ateCarbs - currentFoodCarbs + newFoodCarbs;
//
//
//        int result = myCal - ateCal;
//        textViewAteCal.setText("" + ateCal);
//        textViewResult.setText("" + result);
//
//        float progress = ((float) ateCal / (float) myCal) * 100;
//        if (progress >= 100)
//            circularProgressBar.setProgressBarColor(Color.RED);
//        else
//            circularProgressBar.setProgressBarColor(Color.WHITE);
//
//        circularProgressBar.setProgressWithAnimation(progress, 1000l); // =1s
//
//        //protein
//        textViewAteProtein.setText("" + ateProtein);
//        float progressProtein = ((float) ateProtein / (float) myProtein) * 100;
//        ObjectAnimator.ofInt(progress_protein, "progress", (int) progressProtein).setDuration(1000).start();
//
//        //Fat
//        textViewAteFat.setText("" + ateFat);
//        float progressFat = ((float) ateFat / (float) myFat) * 100;
//        ObjectAnimator.ofInt(progress_fat, "progress", (int) progressFat).setDuration(1000).start();
//
//
//        //Carbs
//        textViewAteCarbs.setText("" + ateCarbs);
//        float progressCarbs = ((float) ateCarbs / (float) myCarbs) * 100;
//        ObjectAnimator.ofInt(progress_carbs, "progress", (int) progressCarbs).setDuration(1000).start();
//
//
//
//
//
//    }
//    private void alertDeleteFood(final int position, final int requestCode) {
//        new AlertDialog.Builder(getActivity())
//                .setTitle("Delete entry")
//                .setMessage("Are you sure you want to delete this entry?")
//
//                // Specifying a listener allows you to take an action before dismissing the dialog.
//                // The dialog is automatically dismissed when a dialog button is clicked.
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (requestCode) {
//                            case 1:
//                                deleteFood(foodsBreakfast.get(position));
//                                breakfastAdapter.notifyItemRemoved(position);
//                                break;
//                            case 2:
//                                deleteFood(foodsLunches.get(position));
//                                lunchAdapter.notifyItemRemoved(position);
//                                break;
//                            case 3:
//                                deleteFood(foodsDinner.get(position));
//                                dinnerAdapter.notifyItemRemoved(position);
//                                break;
//                        }
//
//                    }
//                })
//
//                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (data != null) {
//            Food food = data.getParcelableExtra("food");
//            Log.i("AddNutritionFragment:",food.getDesc());
//            if (requestCode == 1 && resultCode == Activity.RESULT_OK)
//                addFood(food, requestCode);
//
//            if (requestCode == 2 && resultCode == Activity.RESULT_OK)
//                addFood(food, requestCode);
//
//            if (requestCode == 3 && resultCode == Activity.RESULT_OK)
//                addFood(food, requestCode);
//
//            if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
//                int pos = data.getExtras().getInt("position");
//                editFood(food, pos, requestCode);
//            }
//            if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
//                int pos = data.getExtras().getInt("position");
//                editFood(food, pos, requestCode);
//            }
//            if (requestCode == 6 && resultCode == Activity.RESULT_OK) {
//                int pos = data.getExtras().getInt("position");
//                editFood(food, pos, requestCode);
//            }
//        }
//        AppCompatActivity activity=(AppCompatActivity)getContext();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new AddNutritionFragment()).addToBackStack(null).commit();
//    }
//
//    private void setAdapters() {
//
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
//        linearLayoutManager1.setItemPrefetchEnabled(true);
//        breakfastAdapter = new RecyclerFoodAdapter(foodsBreakfast);
//        recyclerViewBreakfast.setAdapter(breakfastAdapter);
//        recyclerViewBreakfast.setLayoutManager(linearLayoutManager1);
//
//        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
//        linearLayoutManager2.setItemPrefetchEnabled(true);
//        lunchAdapter = new RecyclerFoodAdapter(foodsLunches);
//        recyclerViewLunch.setAdapter(lunchAdapter);
//        recyclerViewLunch.setLayoutManager(linearLayoutManager2);
//
//        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
//        linearLayoutManager3.setItemPrefetchEnabled(true);
//        dinnerAdapter = new RecyclerFoodAdapter(foodsDinner);
//        recyclerViewDinner.setAdapter(dinnerAdapter);
//        recyclerViewDinner.setLayoutManager(linearLayoutManager3);
//
//
//    }
//
//    private void addFood(Food food, int requestCode) {
//
//
//        int currentAteCal = Integer.parseInt(textViewAteCal.getText().toString());
//        int newAteCal = (int) food.getEnergy();
//        int allAteCal = currentAteCal + newAteCal;
//        String allAteCal2 = String.valueOf(allAteCal);
//        textViewAteCal.setText(allAteCal2);
//
//
//        int myCal = Integer.parseInt(textViewMyCal.getText().toString());
//        String result = String.valueOf(myCal - allAteCal);
//        textViewResult.setText(result);
//
//        float progress = ((float) allAteCal / (float) myCal) * 100;
//        if (progress >= 100)
//            circularProgressBar.setProgressBarColor(Color.RED);
//        circularProgressBar.setProgressWithAnimation(progress, 1000l); // =1s
//
//
//        //protein
//        int myProtein = Integer.parseInt(textViewMyProtein.getText().toString());
//        int currentAteProtein = Integer.parseInt(textViewAteProtein.getText().toString());
//        int newAteProtein = (int) food.getProtein();
//        int allAteProtein = currentAteProtein + newAteProtein;
//        textViewAteProtein.setText("" + allAteProtein);
//        float progressProtein = ((float) allAteProtein / (float) myProtein) * 100;
//        ObjectAnimator.ofInt(progress_protein, "progress", (int) progressProtein).setDuration(1000).start();
//
//        //fat
//        int myFat = Integer.parseInt(textViewMyFat.getText().toString());
//        int currentAteFat = Integer.parseInt(textViewAteFat.getText().toString());
//        int newAteFat = (int) food.getFat();
//        int allAteFat = currentAteFat + newAteFat;
//        textViewAteFat.setText("" + allAteFat);
//        float progressFat = ((float) allAteFat / (float) myFat) * 100;
//        ObjectAnimator.ofInt(progress_fat, "progress", (int) progressFat).setDuration(1000).start();
//
//        //carbs
//        int myCarbs = Integer.parseInt(textViewMyCarbs.getText().toString());
//        int currentAteCarbs = Integer.parseInt(textViewAteCarbs.getText().toString());
//        int newAteCarbs = (int) food.getCarb();
//        int allAteCarbs = currentAteCarbs + newAteCarbs;
//        textViewAteCarbs.setText("" + allAteCarbs);
//        float progressCarbs = ((float) allAteCarbs / (float) myCarbs) * 100;
//        ObjectAnimator.ofInt(progress_carbs, "progress", (int) progressCarbs).setDuration(1000).start();
//
//
//        switch (requestCode) {
//            case 1:
//                foodsBreakfast.add(0, food);
//                breakfastAdapter.notifyItemInserted(0);
//                break;
//            case 2:
//                foodsLunches.add(0, food);
//                lunchAdapter.notifyItemInserted(0);
//
//                break;
//            case 3:
//                foodsDinner.add(0, food);
//                dinnerAdapter.notifyItemInserted(0);
//                int pos = recyclerViewDinner.getTop() + 240;
//                nestedScrollView.smoothScrollTo(0, pos);
//
//        }
//
//
//    }
//
//
//    private void deleteFood(Food food) {
//        int foodCal = (int) food.getEnergy();
//        int myCal = Integer.parseInt(textViewMyCal.getText().toString());
//        int ateCal = Integer.parseInt(textViewAteCal.getText().toString());
//
//        int newAteCal = ateCal - foodCal;
//
//        textViewAteCal.setText("" + newAteCal);
//
//        float progress = ((float) newAteCal / (float) myCal) * 100;
//        if (progress >= 100)
//            circularProgressBar.setProgressBarColor(Color.RED);
//        else
//            circularProgressBar.setProgressBarColor(Color.WHITE);
//
//        circularProgressBar.setProgressWithAnimation(progress, 1000l); // =1s
//
//
//        //protein
//        int myProtein = Integer.parseInt(textViewMyProtein.getText().toString());
//        int currentAteProtein = Integer.parseInt(textViewAteProtein.getText().toString());
//        int newAteProtein = currentAteProtein - (int) food.getProtein();
//        textViewAteProtein.setText("" + newAteProtein);
//        float progressProtein = ((float) newAteProtein / (float) myProtein) * 100;
//        ObjectAnimator.ofInt(progress_protein, "progress", (int) progressProtein).setDuration(1000).start();
//
//        //fat
//        int myFat = Integer.parseInt(textViewMyFat.getText().toString());
//        int currentAteFat = Integer.parseInt(textViewAteFat.getText().toString());
//        int newAteFat = currentAteFat - (int) food.getFat();
//        textViewAteFat.setText("" + newAteFat);
//        float progressFat = ((float) newAteFat / (float) myFat) * 100;
//        ObjectAnimator.ofInt(progress_fat, "progress", (int) progressFat).setDuration(1000).start();
//
//        //carbs
//        int myCarbs = Integer.parseInt(textViewMyCarbs.getText().toString());
//        int currentAteCarbs = Integer.parseInt(textViewAteCarbs.getText().toString());
//        int newAteCarbs = currentAteCarbs - (int) food.getCarb();
//        textViewAteCarbs.setText("" + newAteCarbs);
//        float progressCarbs = ((float) newAteCarbs / (float) myCarbs) * 100;
//        ObjectAnimator.ofInt(progress_carbs, "progress", (int) progressCarbs).setDuration(1000).start();
//
//    }
//
//    public void onClick(View view) {
//        Intent intent;
//        switch (view.getId()) {
//            case R.id.add_breakfast:
//                intent = new Intent(getActivity(), FoodSearch.class);
//                AddNutritionFragment.this.startActivityForResult(intent, 1);
//                break;
//            case R.id.add_lunch:
//                intent = new Intent(getActivity(), FoodSearch.class);
//                startActivityForResult(intent, 2);
//                break;
//            case R.id.add_dinner:
//                intent = new Intent(getActivity(), FoodSearch.class);
//                startActivityForResult(intent, 3);
//                break;
//        }
//    }
//
//
//    public void updateCal(String cal, String protein, String fat, String carbs) {
//
//        textViewMyCal.setText(cal);
//        textViewMyProtein.setText(protein);
//        textViewMyFat.setText(fat);
//        textViewMyCarbs.setText(carbs);
//
//        int myCal = Integer.parseInt(textViewMyCal.getText().toString());
//        int ateCal = Integer.parseInt(textViewAteCal.getText().toString());
//        int result = myCal - ateCal;
//        textViewResult.setText("" + result);
//
//        float progress = ((float) ateCal / (float) myCal) * 100;
//        if (progress >= 100)
//            circularProgressBar.setProgressBarColor(Color.RED);
//        else
//            circularProgressBar.setProgressBarColor(Color.WHITE);
//
//        circularProgressBar.setProgressWithAnimation(progress, 1000l); // =1s
//        SharedPreferences pref = this.getActivity().getSharedPreferences("result", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("TDEE", cal);
//        editor.putString("protein", "" + protein);
//        editor.putString("fat", "" + fat);
//        editor.putString("carbs", "" + carbs);
//        editor.apply();
//
//
//    }
//    private void reloadCal() {
//        String protein = getActivity().getIntent().getStringExtra("protein");
//        String fat = getActivity().getIntent().getStringExtra("fat");
//        String carbs = getActivity().getIntent().getStringExtra("carbs");
//
//        textViewMyProtein.setText(protein);
//        textViewMyFat.setText(fat);
//        textViewMyCarbs.setText(carbs);
//    }
//
//
//
//
//}