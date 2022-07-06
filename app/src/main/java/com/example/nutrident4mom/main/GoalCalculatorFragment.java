//package com.example.nutrident4mom.main;
//
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.RadioGroup;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.example.nutrident4mom.R;
//import com.google.android.material.textfield.TextInputLayout;
//
//
//public class GoalCalculatorFragment extends Fragment {
//
//
//
//    AutoCompleteTextView autoCompleteGoal;
//    TextInputLayout textInputGoal;
//    Button calc;
//    Button calcNew;
//    Button use;
//    View layoutResult;
//
//    ScrollView scrollView;
//
//    OnDataPass dataPasser;
//
//
//    int TDEE, protein, carbs, fat;
//    TextView calText, proText, carbsText, fatText;
//
//    public GoalCalculatorFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        dataPasser = (OnDataPass) context;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_goal_calculator, container, false);
//
//
//        autoCompleteGoal = v.findViewById(R.id.dropdown_goal);
//
//        textInputGoal = v.findViewById(R.id.goal);
//        calc = v.findViewById(R.id.calc_button);
//        calcNew = v.findViewById(R.id.calc_new);
//        use = v.findViewById(R.id.use);
//        layoutResult = v.findViewById(R.id.layout_result);
//        scrollView = v.findViewById(R.id.scroll_parent);
//
//        calText = v.findViewById(R.id.tdee);
//        fatText = v.findViewById(R.id.fat);
//        carbsText = v.findViewById(R.id.carbs);
//        proText = v.findViewById(R.id.protein);
//
//
//        String[] goals = new String[]{
//                "First Trimester (week 1 to the end of week 12)",
//                "Second Trimester (week 13 to the end of week 26)",
//                "Third Trimester (week 27 to the end of the pregnancy)"
//        };
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.activity_level_dropdown_item, goals);
//        autoCompleteGoal.setAdapter(arrayAdapter);
//        autoCompleteGoal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//                if (b) {
//                    closeKeyboard();
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            autoCompleteGoal.showDropDown();
//                        }
//                    }, 200);
//                }
//            }
//        });
//        autoCompleteGoal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                autoCompleteGoal.showDropDown();
//            }
//        });
//
//
//        calc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                calculate();
//            }
//        });
//
//        calcNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                newCalc();
//            }
//        });
//
//        use.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                use();
//            }
//        });
//
//        return v;
//    }
//
//
//    public void calculate() {
//        if (validateGoal()) {
//
//
//            int BMR = 1;
//
//            String goal = autoCompleteGoal.getText().toString();
//
//            switch (goal) {
//                case "First Trimester (week 1 to the end of week 12)":
//                    BMR = 1;
//                    protein = 1;
//                    carbs = 1;
//                    fat = 1;
//                    break;
//                case "Second Trimester (week 13 to the end of week 26)":
//                    BMR = 2;
//                    protein = 2;
//                    carbs = 2;
//                    fat = 2;
//                    break;
//                case "Third Trimester (week 27 to the end of the pregnancy)":
//                    BMR = 3;
//                    protein = 3;
//                    carbs = 3;
//                    fat = 3;
//                    break;
//            }
//
//            calText.setText(BMR + "Kcal");
//            fatText.setText(fat + "g");
//            carbsText.setText(carbs + "g");
//            proText.setText(protein + "g");
//
//            TDEE = BMR;
//
//            layoutResult.setVisibility(View.VISIBLE);
//            calc.setVisibility(View.GONE);
////                    scrollView.smoothScrollTo(0, scrollView.getBottom());
//            //scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            scrollView.post(new Runnable() {
//                @Override
//                public void run() {
//                    scrollView.fullScroll(View.FOCUS_DOWN);
//                }
//            });
//        }
//
//
//    }
//
//
//
//    private boolean validateGoal() {
//        if (autoCompleteGoal.getText().toString().length() == 0) {
//            textInputGoal.setError("Please Pick Your Goal");
//            return false;
//        } else {
//            textInputGoal.setError(null);
//            return true;
//        }
//    }
//
//
//    public void use() {
//        //dataPasser.onDataPass("" + TDEE, "" + protein, "" + fat, "" + carbs);
//        //getActivity().getSupportFragmentManager().beginTransaction().hide(this).commit();
//        /*
//        Intent intent = new Intent(this,MainActivity.class);
//        intent.putExtra("TDEE",""+TDEE);
//
//
//        editor.putString("TDEE",""+TDEE);
//        editor.apply();
//
//
//        startActivity(intent);
//        finish();
//         */
//    }
//
//    public void newCalc() {
//        calc.setVisibility(View.VISIBLE);
//        layoutResult.setVisibility(View.GONE);
//        autoCompleteGoal.setText("");
//
//    }
//
//    private void closeKeyboard() {
//        View view = getActivity().getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
//
//    public interface OnDataPass {
//        public void onDataPass(String cal, String protein, String fat, String carbs);
//    }
//}
