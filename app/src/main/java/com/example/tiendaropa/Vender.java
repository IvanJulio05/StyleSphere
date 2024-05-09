package com.example.tiendaropa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendaropa.Models.IdProductCallBack;
import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.Models.TypeProduct;
import com.example.tiendaropa.Services.ProductService;

import java.util.ArrayList;
import java.util.List;

public class Vender extends AppCompatActivity {

    private String selectedOption;
    private EditText description;
    private EditText productName;
    private EditText stock;
    private EditText size;
    private EditText price;
    private CheckBox envioGratis;
    private EditText brand;
    private String idProduct;
    private ProductService productService = new ProductService();
    private boolean addFoto = true;
    private static final int COD_SEL_IMAGE= 300;
    private Uri image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);

        //informaicon de productos
        productName = (EditText) findViewById(R.id.nombreProducto);
        description= (EditText) findViewById(R.id.descripcionProducto);
        stock = (EditText) findViewById(R.id.stockProducto);
        size = (EditText) findViewById(R.id.tallaProducto);
        price = (EditText) findViewById(R.id.precioProducto);
        envioGratis = (CheckBox) findViewById(R.id.envioGratis);
        brand = (EditText) findViewById(R.id.marcaProducto);


        Spinner spinner = findViewById(R.id.spinner);

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,opcionesTexto);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opciones,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = parent.getItemAtPosition(position).toString();

                Toast.makeText(Vender.this, "Opción seleccionada: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void guardar(View view){
        if(addFoto){
            Product product = new Product();
            SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
            String email = prefs.getString("email",null);
            product.setEmailUser(email);
            product.setName(productName.getText().toString());
            product.setPrice(Integer.parseInt(price.getText().toString()));
            product.setStock(Integer.parseInt(stock.getText().toString()));
            product.setDescription(description.getText().toString());
            product.setSize(size.getText().toString());
            product.setFreeShipping(envioGratis.isChecked());
            product.setBrand(brand.getText().toString());


            switch (selectedOption){
                case "Accesorios": product.setTypeProduct(TypeProduct.ACCESSORY); break;
                case "Pantalones":product.setTypeProduct(TypeProduct.PANTS); break;
                case "Camisas":product.setTypeProduct(TypeProduct.SHIRT);break;
                case "Faldas":product.setTypeProduct(TypeProduct.SKIRT); break;
                case "Camisetas":product.setTypeProduct(TypeProduct.TSHIRT); break;
                case "Cinturones":product.setTypeProduct(TypeProduct.BELT); break;
                case "Gorras":product.setTypeProduct(TypeProduct.CAP); break;
                case "Joyas":product.setTypeProduct(TypeProduct.JEWEL); break;
                case "Zapatos":product.setTypeProduct(TypeProduct.SHOES); break;

            }



            productService.InsertProduct(product, new IdProductCallBack() {
                @Override
                public void recibirId(String id) {
                    idProduct = id;
                }
            });
            Toast.makeText(Vender.this, "Producto Guardado", Toast.LENGTH_SHORT).show();
            addFoto= false;
        }
        else{
            Toast.makeText(Vender.this, "Producto repetido", Toast.LENGTH_SHORT).show();
        }

    }

    public void agregarFoto(View view){

        if(!addFoto){
            if(idProduct==null){
                Toast.makeText(Vender.this, "Ocurrio un error, lo sentimos", Toast.LENGTH_SHORT).show();
            }
            else{
                uploaPhoto();
            }
        }
        else{
            Toast.makeText(Vender.this, "Primero Envia el producto", Toast.LENGTH_SHORT).show();
        }


    }

    public void uploaPhoto(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,COD_SEL_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == COD_SEL_IMAGE && resultCode == RESULT_OK && data!=null && data.getData() != null){
            image_url = data.getData();

            productService.uploadPhoto(image_url,idProduct,this);
        }
    }

}