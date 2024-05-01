package com.example.tiendaropa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.inputEmailLogin);
        password= (EditText) findViewById(R.id.inputPasswordLogin);
        mAuth = FirebaseAuth.getInstance();
        session();
    }

    public void session(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email",null);
        System.out.println("------ mira el email: "+email);
        if(email != null){
            System.out.println("---------- si entrooo ---------------");
            showHome(email);
        }
    }

    public void CrearCuenta(View view){
        Intent cuenta = new Intent(this,CrearCuenta.class);
        startActivity(cuenta);
    }

    public void olvidarContraseña(View view){

    }

    public void login(View view){
        mAuth.signInWithEmailAndPassword(email.getText().toString(),
                password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    showHome(email.getText().toString());

                } else{
                    showAlert();
                }
            }
        });
    }
    private void showAlert(){
        new AlertDialog.Builder(this).setTitle("Error").
                setMessage("se ha producido un error autentificando el usuario").setPositiveButton(android.R.string.yes, null).show();
    }

    private void showHome(String email){
        Intent homeIntent = new Intent(MainActivity.this,Home.class);
        homeIntent.putExtra("email",email);
        startActivity(homeIntent);
    }

}