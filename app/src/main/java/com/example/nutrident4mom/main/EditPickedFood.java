package com.example.nutrident4mom.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.data.DatabaseAccess;
import com.example.nutrident4mom.model.Food;


public class EditPickedFood extends AppCompatActivity {
    DatabaseAccess databaseAccess;

    Food food, result;
    TextView name, calories_value, protein_value, fat_value, carbs_value, sugar_value;
    EditText foodQuantity;
    int food_quantity, food_cal;
    String currentFoodQuantity, currentFoodPortion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked_food);

        Button button = findViewById(R.id.button);
        button.setText("Update");

        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        name = findViewById(R.id.food_name);
        calories_value = findViewById(R.id.calories_value);
        protein_value = findViewById(R.id.protein_value);
        fat_value = findViewById(R.id.fat_value);
        carbs_value = findViewById(R.id.carbs_value);
        sugar_value = findViewById(R.id.sugar_value);

        foodQuantity = findViewById(R.id.food_quantity);


        food = getIntent().getParcelableExtra("food");
        result = new Food();
        result.setId(food.getId());
        result.setDesc(food.getDesc());

        currentFoodQuantity = food.getPortion();

        foodQuantity.setText(currentFoodQuantity);


        name.setText(food.getDesc());
        calc();

        foodQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calc();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void calc() {


        if (foodQuantity.length() == 0)
            food_quantity = 0;
        else
            food_quantity = Integer.parseInt(foodQuantity.getText().toString());


        int current_food_quantity = Integer.parseInt(currentFoodQuantity);

        double energy, protein, fat, carbs, sugar;
        energy = food.getEnergy();
        protein = food.getProtein();
        fat = food.getFat();
        carbs = food.getCarb();
        sugar = food.getSugar();


        energy = (energy / current_food_quantity)* food_quantity;
        protein = (protein / current_food_quantity) * food_quantity;
        fat = (fat / current_food_quantity) * food_quantity;
        carbs = (carbs / current_food_quantity) * food_quantity;
        sugar = (sugar / current_food_quantity) * food_quantity;

        calories_value.setText("" + String.format("%.2f", energy) + "Kcal");
        protein_value.setText("" + String.format("%.2f", protein) + "g");
        fat_value.setText("" + String.format("%.2f", fat) + "g");
        carbs_value.setText("" + String.format("%.2f", carbs) + "g");
        sugar_value.setText("" + String.format("%.2f", sugar) + "g");

        result.setEnergy(energy);
        result.setProtein(protein);
        result.setFat(fat);
        result.setCarb(carbs);
        result.setSugar(sugar);

    }

    /*
    public void calc2() {
        if (foodQuantity.length() == 0)
            food_quantity = 0;
        else
            food_quantity = Integer.parseInt(foodQuantity.getText().toString());


        float quantity = serving2.getQuantity();
        if (quantity > 1) {
            food_cal = (int) (food_quantity * food.getCal() * quantity) / food.getWeight();

        } else if (quantity > 0 && quantity <= 1) {
            food_cal = (int) (food_quantity * food.getCal() * quantity * food.getWeight()) / food.getWeight();
        } else if (quantity == 0) {
            food_cal = (int) (food_quantity * food.getCal()) / food.getWeight();
        }

        cal.setText("" + food_cal);

    }
*/
    public void insert(View view) {
        String portion = foodQuantity.getText().toString();
        result.setPortion(portion);

        Intent intent = new Intent();
        intent.putExtra("food", result);

        if (getIntent().getBooleanExtra("edit", false) != false) {
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_OK, intent);
        }
        finish();

    }



}