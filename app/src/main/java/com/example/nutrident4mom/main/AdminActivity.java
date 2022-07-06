package com.example.nutrident4mom.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.adapter.recfragment;
import com.example.nutrident4mom.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BottomNavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        nav = findViewById(R.id.bottomNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new EducationalContentFragment()).commit();
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.navigation_chat:
//                    replaceFragment(new ChatFragment());
//                    break;
//                case R.id.navigation_educational:
//                    replaceFragment(new recfragment());
//                    break;
//                case R.id.navigation_user:
//                    replaceFragment(new SettingFragment());
//                    break;
//            }
//
//            return true;
//        });

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_chat:
                        replaceFragment(new ChatFragment());
                        break;
                    case R.id.navigation_educational:
                        replaceFragment(new EducationalContentFragment());
                        break;
                    case R.id.navigation_user:
                        replaceFragment(new SettingFragment());
                        break;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.wrapper,fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}