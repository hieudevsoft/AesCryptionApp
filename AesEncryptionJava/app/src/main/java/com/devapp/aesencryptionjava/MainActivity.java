package com.devapp.aesencryptionjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView imgMenu;
    private TextView tvDanger;
    private TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        imgMenu.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        NavigationView navView = findViewById(R.id.navigationView);
        navView.setItemIconTintList(null);
        tvDanger = navView.getHeaderView(0).findViewById(R.id.tvDanger);
        tvDanger.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blynk_animation));
        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(navView,navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            titleText.setText(destination.getLabel());
        });
    }
    private void mapping(){
        drawerLayout = findViewById(R.id.drawerLayout);
        imgMenu = findViewById(R.id.imgMenu);
        titleText = findViewById(R.id.titleText);
    }
}