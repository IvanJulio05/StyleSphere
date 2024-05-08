package com.example.tiendaropa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tiendaropa.Models.User;
import com.example.tiendaropa.Models.UserCallBack;
import com.example.tiendaropa.Services.UserService;

public class InformacionPersonal extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private EditText direccion;
    private String email;
    private ImageView flechaAtras;
    private UserService userService = new UserService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_personal);
        nombre=(EditText)findViewById(R.id.editNombre);
        apellido=(EditText)findViewById(R.id.editApellido);
        direccion=(EditText)findViewById(R.id.editDireccion);
        flechaAtras = (ImageView) findViewById(R.id.flecha_regresar);
        flechaAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cargarDatos();
    }



    public void cargarDatos(){
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        userService.findUserByEmail(email, new UserCallBack() {
            @Override
            public void recibirUsuario(User user) {
                nombre.setText(user.getName());
                apellido.setText(user.getSurname());
                direccion.setText(user.getAddress());
            }
        });

    }

    public void guardarCambios(View view){
        String name = nombre.getText().toString();
        String surname = apellido.getText().toString();
        String address = direccion.getText().toString();

        userService.findUserByEmail(email, new UserCallBack() {
            @Override
            public void recibirUsuario(User user) {
                if(user !=null){
                    //actualizando informacion
                    user.setName(name);
                    user.setSurname(surname);
                    user.setAddress(address);
                    userService.updateUser(user);
                    showAlert("Datos guardados correctamente","Guardar Datos");
                }
                else{
                    showAlert("Error al guardar los datos","Error");
                }




            }
        });


    }

    private void showAlert(String mensaje,String title){
        new AlertDialog.Builder(this).setTitle(title).
                setMessage(mensaje).setPositiveButton(android.R.string.yes, null).show();
    }
}