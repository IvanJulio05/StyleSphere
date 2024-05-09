package com.example.tiendaropa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.Models.User;
import com.example.tiendaropa.Models.UserCallBack;
import com.example.tiendaropa.Services.UserService;

import java.text.DecimalFormat;

public class VentanaProducto extends AppCompatActivity {

    TextView stock,nombreProducto,precio,envioGratis,marca,talla,tipo,description,nombreVendedor;
    ImageView imgProducto,favorito;
    Button agregar;
    UserService userService = new UserService();
    boolean relleno = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_producto);

        agregar= (Button) findViewById(R.id.btCarrito);
        favorito= (ImageView) findViewById(R.id.btFavorito);
        stock = (TextView) findViewById(R.id.stockProductVent);
        nombreProducto = (TextView) findViewById(R.id.nombreProductoVentana);
        precio = (TextView) findViewById(R.id.precioProductoVentana);
        envioGratis = (TextView) findViewById(R.id.EnvioGratisVentana);
        marca = (TextView) findViewById(R.id.caracMarcarProduct);
        talla = (TextView) findViewById(R.id.caracTallaProduct);
        tipo = (TextView) findViewById(R.id.caracTypeProduct);
        imgProducto= (ImageView) findViewById(R.id.imgProduct);
        description = (TextView) findViewById(R.id.caracDescripProduct);
        nombreVendedor =(TextView)findViewById(R.id.nombreVendedor);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email",null);
        //mi producto
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Product product;
        if(bundle!=null){

            product=(Product) bundle.get("product");

            //verificando si el producto esta en favoritos
            userService.findFavoriteByEmail(email, new UserCallBack() {
                @Override
                public void recibirUsuario(User u) {
                    if(u.existe(product.getId())){
                        favorito.setImageResource(R.drawable.corazon_lleno);
                        relleno=false;

                    }
                }
            });


            //asignando informacion

            stock.setText("Nuevo | "+product.getStock()+" disponibles");
            nombreProducto.setText(product.getName());

            DecimalFormat formatter = new DecimalFormat("#,###.00");

            precio.setText("$ "+formatter.format(product.getPrice()));
            marca.setText("Marca: "+product.getBrand());
            talla.setText("Talla: "+product.getSize());
            tipo.setText("Categoria: "+product.getTypeProduct().toString());
            description.setText(product.getDescription());
            if(product.isFreeShipping()){
                envioGratis.setText("Envio Gratis");
            }
            else{
                envioGratis.setTextColor(ContextCompat.getColor(this,R.color.black));
                envioGratis.setText("Entrega acorde con el vendedor");
            }
            cargarImg(product.getUrl_img());



            userService.findUserByEmail(product.getEmailUser(), new UserCallBack() {
                @Override
                public void recibirUsuario(User u) {
                    nombreVendedor.setText("Vendido por "+u.getName());


                }
            });

            agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    userService.findUserByEmail(email, new UserCallBack() {
                        @Override
                        public void recibirUsuario(User u) {
                            boolean noRepetido = u.setShoppingCar(product.getId());
                            if(noRepetido){
                                userService.updateUser(u);
                                Toast.makeText(VentanaProducto.this, "Agregado al carrito", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(VentanaProducto.this, "Ya esta agregado", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });

            favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userService.findUserByEmail(email, new UserCallBack() {
                        @Override
                        public void recibirUsuario(User u) {
                            if(relleno){
                                u.setFavoriteProduct(product.getId());
                                userService.updateUser(u);
                                favorito.setImageResource(R.drawable.corazon_lleno);
                                Toast.makeText(VentanaProducto.this, "Se agrego a favorito", Toast.LENGTH_SHORT).show();
                                relleno = false;
                            }
                            else{
                                u.borrarUnFavorito(product.getId());
                                userService.updateUser(u);
                                favorito.setImageResource(R.drawable.corazon);
                                Toast.makeText(VentanaProducto.this, "Se quito de favorito", Toast.LENGTH_SHORT).show();
                                relleno= true;
                            }
                        }
                    });
                }
            });

        }

    }

    public void cargarImg(String url){
        Glide.with(this).load(url).into(imgProducto);
    }




}