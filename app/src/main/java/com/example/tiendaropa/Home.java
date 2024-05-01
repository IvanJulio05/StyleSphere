package com.example.tiendaropa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {


    PerfilFragment  perfilFragment= new PerfilFragment();
    CarritoFragment carritoFragment = new CarritoFragment();
    InicioFragment inicioFragment = new InicioFragment();
    FavoritoFragment favoritoFragment = new FavoritoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectListener);
        Intent intent = getIntent();

        //Guardado de datos
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit= prefs.edit();
        prefsEdit.putString("email",intent.getStringExtra("email"));
        prefsEdit.apply();
        //NOTA: falta implementar el cerrar session
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.perfilFragment:
                    loadFragment(perfilFragment);
                    return true;
                case R.id.carritoFragment:
                    loadFragment(carritoFragment);
                    return true;
                case R.id.InicioFragment:
                    loadFragment(inicioFragment);
                    return true;
                case R.id.favoriteFragment:
                    loadFragment(favoritoFragment);
                    return true;
            }

            return false;
        }
    };

    public void loadFragment(Fragment f){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,f);
        transaction.commit();
    }



}