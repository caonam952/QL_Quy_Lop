package com.example.quan_ly_quy_lop;

import android.os.Bundle;

import com.example.quan_ly_quy_lop.models.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.quan_ly_quy_lop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(binding.navView, navController);

        db = new DatabaseHelper(this);

//        db.insertMember("SV01", "Kiều Hoàng Phúc");
//        db.insertMember("SV02", "Phạm Hoàng Kim");
//        db.insertMember("SV03", "Cao Hải Nam");
//
//        db.insertSessionIncome("I1", "Test 1", 10000, "23-06-2018", false);
//        db.insertSessionIncome("I2", "Test 2", 10000, "20-06-2017", false);
//        db.insertSessionIncome("I3", "Test 3", 10000, "29-06-2021", false);
//        db.insertSessionIncome("I4", "Test 4", 10000, "29-06-2019", false);
//        db.insertSessionIncome("I5", "Test 4", 10000, "29-06-2020", false);
//
//        db.insertExpense("E1", "Test1", 10000, "14-10-2021 21:22:23");
//        db.insertExpense("E2", "Test2", 20000, "10-1-2021 20:22:23");
//        db.insertExpense("E3", "Test3", 30000, "15-11-2021 23:22:23");
    }
}