package com.example.tiendaropa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class CrearCuenta extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText password2;
    private EditText name;
    private FirebaseAuth mAuth;

    private final int GOOGLE_SING_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crear_cuenta);
        email= (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.inputPassword);
        password2 = (EditText) findViewById(R.id.inputPassword2);
        name = (EditText) findViewById(R.id.inputName);
        mAuth = FirebaseAuth.getInstance();


    }

    public void CrearCuenta(View view){

        String p1=password.getText().toString();
        String p2=password2.getText().toString();
        final int MINIMUM_LENGTH= 8;
        boolean correcto = true;
        boolean verifyPassword = true;

        //veficando la informacion
        if(email.getText().toString().equals("") || password.getText().toString().equals("") || password2.getText().toString().equals("") || name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
            correcto =false;
        }

        //veficando que las contraseñas sean iguales
        if(!p1.equals(p2)){
            Toast.makeText(getApplicationContext(),"La contraseña no coinciden",Toast.LENGTH_SHORT).show();
            verifyPassword= false;
        }

        //verificando la longitud de las contraseñas
        if(verifyPassword && p1.length() < MINIMUM_LENGTH){
            Toast.makeText(getApplicationContext(),"La contraseñas es muy corta",Toast.LENGTH_SHORT).show();
            correcto = false;
        }

        //guardando usuario
        if(correcto && verifyPassword){
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),
                    password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent homeIntent = new Intent(CrearCuenta.this,Home.class);
                        homeIntent.putExtra("email",email.getText().toString());
                        homeIntent.putExtra("name",name.getText().toString());
                        startActivity(homeIntent);
                    } else{
                        showAlert();
                    }
                }
            });
        }


    }

    public void btnGogoogle(View view){

        //configuracion

        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
        googleClient.signOut();

        startActivityForResult(googleClient.getSignInIntent(),GOOGLE_SING_IN);

    }

    private void showAlert(){
        new AlertDialog.Builder(this).setTitle("Error").
                setMessage("se ha producido un error autentificando el usuario").setPositiveButton(android.R.string.yes, null).show();
    }
    public void regresar(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SING_IN){

            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                GoogleSignInAccount accout = task.getResult();


                if(accout!=null){

                    AuthCredential credential = GoogleAuthProvider.getCredential(accout.getIdToken(),null);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent homeIntent = new Intent(CrearCuenta.this,Home.class);
                                homeIntent.putExtra("email",accout.getEmail());
                                homeIntent.putExtra("name",name.toString());
                                startActivity(homeIntent);
                            } else{
                                showAlert();
                            }
                        }
                    });
                }
            }catch (Exception e){
                showAlert();
            }


        }
    }
}