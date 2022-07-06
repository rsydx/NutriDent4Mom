package com.example.nutrident4mom.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;
import com.example.nutrident4mom.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class UpdateGoalActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteGoal;

    EditText textInputWeight;
    Button calc;
    View layoutResult;
    ScrollView scrollView;


    int TDEE, protein, carbs, fat;
    TextView calText, proText, carbsText, fatText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goal);

        autoCompleteGoal = findViewById(R.id.dropdown_goal);
        textInputWeight = findViewById(R.id.weight);
        calc = findViewById(R.id.calc_button);
        layoutResult = findViewById(R.id.layout_result);

        scrollView = findViewById(R.id.scroll_parent);

        calText = findViewById(R.id.tdee);
        fatText = findViewById(R.id.fat);
        carbsText = findViewById(R.id.carbs);
        proText = findViewById(R.id.protein);


        String[] goals = new String[]{
                "First Trimester (week 1 to the end of week 12)",
                "Second Trimester (week 13 to the end of week 26)",
                "Third Trimester (week 27 to the end of the pregnancy)"
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.activity_level_dropdown_item, goals);
        autoCompleteGoal.setAdapter(arrayAdapter);
        autoCompleteGoal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            autoCompleteGoal.showDropDown();
                        }
                    }, 200);
                }
            }
        });
        autoCompleteGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteGoal.showDropDown();
            }
        });


        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });

    }

    public void calculate() {
        if (validateWeight() & validateGoal()) {

            int weight = Integer.parseInt(textInputWeight.getText().toString());
            //Toast.makeText(this, "" + calculate_BMR(age, height, weight), Toast.LENGTH_SHORT).show();

            String goal = autoCompleteGoal.getText().toString();

            int BMR = 1690;
            int proteinCalculator = (int) (1000/weight);
            switch (goal) {
                case "First Trimester (week 1 to the end of week 12)":

                    protein = (int)(proteinCalculator + 0.5);
                    carbs = (int) (0.1*BMR);
                    fat = 65;
                    break;
                case "Second Trimester (week 13 to the end of week 26)":
                    BMR = 1890;
                    protein = (int)(proteinCalculator + 8);
                    carbs = (int) (0.1*BMR);
                    fat = 71;
                    break;
                case "Third Trimester (week 27 to the end of the pregnancy)":
                    BMR = 2080;
                    protein = (int) (proteinCalculator + 25);
                    carbs = (int) (0.1*BMR);
                    fat = 78;
                    break;
            }


            calText.setText(BMR + "Kcal");
            fatText.setText(fat + "g");
            carbsText.setText(carbs + "g");
            proText.setText(protein + "g");

            TDEE = BMR;

            layoutResult.setVisibility(View.VISIBLE);
            calc.setVisibility(View.GONE);

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }


    }


    private boolean validateWeight() {
        if (textInputWeight.getText().toString().length() == 0) {
            textInputWeight.setError("Field can't be empty!");
            return false;
        } else if (Integer.parseInt(textInputWeight.getText().toString()) < 30) {
            textInputWeight.setError("Too Low Weight!");
            return false;
        } else if (Integer.parseInt(textInputWeight.getText().toString()) > 300) {
            textInputWeight.setError("Too High Weight!");
            return false;
        } else {
            textInputWeight.setError(null);
            return true;
        }
    }

    private boolean validateGoal() {
        if (autoCompleteGoal.getText().toString().length() == 0) {
            autoCompleteGoal.setError("Please Pick Your Pregnancy Trimester");
            return false;
        } else {
            autoCompleteGoal.setError(null);
            return true;
        }
    }

    public void use(View view) {
        Intent intent = new Intent(UpdateGoalActivity.this, MainActivity2.class);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        String userId = firebaseUser.getUid();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("TDEE",TDEE);
        hashMap.put("carbs",carbs);
        hashMap.put("protein",protein);
        hashMap.put("fat",fat);
        reference.child(userId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateGoalActivity.this, "Your goal have been updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UpdateGoalActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        startActivity(intent);
//        finish();


    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

//    public void initUsersFood(String userId, String email){
//        Intent intent = new Intent(this, MainActivity2.class);
//        DatabaseReference referenceUserFood =  FirebaseDatabase.getInstance().getReference("UserMeal");
//        referenceUserFood.child(userId).setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                startActivity(intent);
//                finish();
//            }
//        });
//
//    }

}
