//package com.example.nutrident4mom.main;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.nutrident4mom.R;
//import com.example.nutrident4mom.adapter.recfragment;
//import com.example.nutrident4mom.main.AddNutritionFragment;
//import com.example.nutrident4mom.databinding.ActivityMainBinding;
//import com.example.nutrident4mom.model.Food;
//
//
//public class MainActivity extends AppCompatActivity {
//    ActivityMainBinding binding;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recfragment()).commit();
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    replaceFragment(new recfragment());
//                    break;
//                case R.id.navigation_educational:
//                    replaceFragment(new AddNutritionFragment());
//                    break;
//                case R.id.navigation_user:
//                    replaceFragment(new SettingFragment());
//                    break;
//            }
//
//            return true;
//        });
//
//
//    }
//
//    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.wrapper,fragment);
//        fragmentTransaction.commit();
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//
//}
