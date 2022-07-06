package com.example.nutrident4mom.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.adapter.RecyclerFoodAdapter;
import com.example.nutrident4mom.data.DatabaseAccess;
import com.example.nutrident4mom.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodSearch extends AppCompatActivity {
    DatabaseAccess databaseAccess;
    ProgressBar progressBar;
    private SearchView searchView;
    EditText editText;
    TextView searchResult;
    List<Food> foods;
    RecyclerFoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        searchView = findViewById(R.id.searchview);
        searchResult = findViewById(R.id.search_result);

        progressBar = findViewById(R.id.progress_bar);
        //editText = findViewById(R.id.editText);

        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();


        foods = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerFoodAdapter(foods);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setItemPrefetchEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new RecyclerFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(FoodSearch.this, PickedFood.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                Food food = foods.get(position);
                intent.putExtra("food", food);
                startActivityForResult(intent, 1);
            }
        });

        if(getIntent().getStringExtra("foodName") != null){
            //editText.setText(getIntent().getStringExtra("foodName"));
            //search2();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String newText) {
                    search2(newText);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search2(newText);
                    return false;
                }
            });
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                search2(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search2(newText);
                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Food food = data.getParcelableExtra("food");
            Log.i("FoodSearch:",food.getDesc());
            setResult(RESULT_OK, data);
            finish();
        }
    }


    public void search2(String newText) {
        //search = false;
        progressBar.setVisibility(View.VISIBLE);
        searchResult.setVisibility(View.VISIBLE);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Food> newFoods = databaseAccess.foods(newText);
                foods.clear();
                foods.addAll(newFoods);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Do Ui Thread work here
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
}