package com.example.tiendaropa.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.tiendaropa.Models.GeneradorIdCallBack;
import com.example.tiendaropa.Models.IdProductCallBack;
import com.example.tiendaropa.Models.ListProductCallBack;
import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.Models.ProductCallBack;
import com.example.tiendaropa.Models.TypeProduct;
import com.example.tiendaropa.Models.User;
import com.example.tiendaropa.Models.UserCallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    boolean noExiste;
    private boolean correcto;
    private ProgressDialog progressDialog;

    private Product product = new Product();

    public void findProductById(String id, ProductCallBack pcb){

        noExiste=false;
        db.collection("products").document(id+"").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){

                    product.setId(id);
                    product.setUrl_img(documentSnapshot.getString("url_img"));
                    product.setName(documentSnapshot.getString("name"));
                    product.setDescription(documentSnapshot.getString("description"));
                    product.setSize(documentSnapshot.getString("size"));
                    product.setPrice((Integer)documentSnapshot.get("price"));
                    product.setBrand(documentSnapshot.getString("brand"));
                    product.setTypeProduct(TypeProduct.valueOf(documentSnapshot.getString("typeProduct")));
                    product.setStock((Integer) documentSnapshot.get("stock"));
                    product.setFreeShipping((boolean) documentSnapshot.get("freeShipping"));
                    pcb.recibirProducto(product);

                }
                else{
                    pcb.recibirProducto(product);
                }
            }
        });


    }

    public void findAllProduct(ListProductCallBack lp){
        noExiste=false;
        db.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Product> productList = new ArrayList<>();

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if(documentSnapshot.exists()){
                        Product product = new Product();
                        product.setId(documentSnapshot.getString("id"));
                        product.setUrl_img(documentSnapshot.getString("url_img"));
                        product.setName(documentSnapshot.getString("name"));
                        product.setDescription(documentSnapshot.getString("description"));
                        product.setSize(documentSnapshot.getString("size"));
                        product.setPrice(Integer.parseInt(documentSnapshot.getString("price")) );
                        product.setBrand(documentSnapshot.getString("brand"));
                        product.setTypeProduct(TypeProduct.valueOf(documentSnapshot.getString("typeProduct")));
                        product.setStock(Integer.parseInt(documentSnapshot.getString("stock")) );
                        product.setFreeShipping((boolean) documentSnapshot.get("freeShipping"));
                        productList.add(product);


                    }
                }
                lp.recibirProductos(productList);

            }
        });


    }

    public void findAllProduct(ListProductCallBack lp,Context context){
        progressDialog= new ProgressDialog(context);
        progressDialog.setMessage("Cargando");
        progressDialog.show();

        noExiste=false;
        db.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Product> productList = new ArrayList<>();

                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if(documentSnapshot.exists()){
                        Product product = new Product();
                        product.setId(documentSnapshot.getString("id"));
                        product.setUrl_img(documentSnapshot.getString("url_img"));
                        product.setName(documentSnapshot.getString("name"));
                        product.setDescription(documentSnapshot.getString("description"));
                        product.setSize(documentSnapshot.getString("size"));
                        product.setPrice(Integer.parseInt(documentSnapshot.getString("price")) );
                        product.setBrand(documentSnapshot.getString("brand"));
                        product.setTypeProduct(TypeProduct.valueOf(documentSnapshot.getString("typeProduct")));
                        product.setStock(Integer.parseInt(documentSnapshot.getString("stock")) );
                        product.setFreeShipping((boolean) documentSnapshot.get("freeShipping"));
                        productList.add(product);


                    }
                }
                progressDialog.dismiss();
                lp.recibirProductos(productList);

            }
        });


    }

    public void updateProduct(Product product){

        Map<String,Object> update = new HashMap<>();

        update.put("url_img",product.getUrl_img());

        update.put("name",product.getName());

        update.put("description",product.getDescription());

        update.put("size",product.getSize());

        update.put("price",product.getPrice());

        update.put("brand",product.getBrand());

        update.put("typeProduct",product.getTypeProduct().toString());

        update.put("stock",product.getStock());

        update.put("freeShipping",product.isFreeShipping());

        db.collection("products").document(product.getId()).update(update);

    }


    private void generadorId(GeneradorIdCallBack generador){


        db.collection("ids").document("idProduct").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){

                    String idCompleta;
                    long idNumero;
                    long id;
                    id= documentSnapshot.getLong("id");
                    Map<String,Object> map = new HashMap<>();
                    idNumero=documentSnapshot.getLong("idNumero");

                    //formando Id
                    char letra= (char) id;
                    System.out.println("valor de letra char: "+letra);
                    idCompleta= ""+idNumero+letra;


                    //cambiando id
                    if(id==122){
                        id=97;
                    }
                    else{
                        System.out.println("antes: "+id);
                        id=id+1;
                        System.out.println("despues: "+id);
                    }

                    idNumero=idNumero+1;



                    map.put("id",id);

                    map.put("idNumero",idNumero);

                    System.out.println("valor de idNumero: "+map.get("idNumero"));
                    System.out.println("valor de idLetra: "+map.get("id"));

                    db.collection("ids").document("idProduct").update(map);
                    generador.recibirId(idCompleta);
                }
                else{

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                generador.recibirId(null);
            }
        });
    }

    public void InsertProduct(Product product, IdProductCallBack idProductCallBack){

        generadorId(new GeneradorIdCallBack() {
            @Override
            public void recibirId(String id) {
                Map<String,Object> update = new HashMap();

                update.put("id",id);

                update.put("url_img",product.getUrl_img());

                update.put("name",product.getName());

                update.put("description",product.getDescription());

                update.put("size",product.getSize());

                update.put("price",product.getPrice()+"");

                update.put("brand",product.getBrand());

                update.put("typeProduct",product.getTypeProduct().toString());

                update.put("stock",product.getStock()+"");

                update.put("freeShipping",product.isFreeShipping());
                db.collection("products").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(!documentSnapshot.exists()){
                            db.collection("products").document(id).set(update);
                            idProductCallBack.recibirId(id);
                            correcto= true;
                        }
                        else{
                            idProductCallBack.recibirId(null);
                            correcto= false;
                        }
                    }
                });




            }
        });


    }

    public void uploadPhoto(Uri uri, String identificador, Context e){
        progressDialog= new ProgressDialog(e);
        progressDialog.setMessage("Subiendo Imagen");
        progressDialog.show();

        StorageReference storageRef = storage.getReference().child("photoProducts").child(identificador);

        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String dowload_uri = uri.toString();
                        Map<String,Object> map = new HashMap<>();
                        map.put("url_img",dowload_uri);
                        db.collection("products").document(identificador).update(map);
                        progressDialog.dismiss();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace(System.out);
                progressDialog.dismiss();
            }
        });



    }

}


