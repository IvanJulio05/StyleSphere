package com.example.tiendaropa.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.Models.User;
import com.example.tiendaropa.Models.UserCallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UserService {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    boolean noExiste;
    private boolean correcto;
    private ProgressDialog progressDialog;
    private User user = new User();

    public void findUserByEmail(String email,UserCallBack ucb){



        noExiste=false;
        db.collection("users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                if(documentSnapshot.exists()){

                    user.setName(documentSnapshot.getString("name"));
                    user.setEmail(email);
                    user.setSurname(documentSnapshot.getString("surname"));
                    user.setAddress(documentSnapshot.getString("address"));
                    user.setFavoriteProduct((List<Product>) documentSnapshot.get("favoriteProduct"));
                    user.setPurchasedProducts((List<Product>) documentSnapshot.get("purchasedProduct"));
                    user.setShoppingCar((List<Product>) documentSnapshot.get("shoppingCar"));
                    user.setUrl_img(documentSnapshot.getString("url_img"));
                    ucb.recibirUsuario(user);

                }
                else{
                    ucb.recibirUsuario(user);
                }
            }
        });


    }

    public void updateUser(User user){

        Map<String,Object> update = new HashMap<>();

        update.put("name",user.getName());

        update.put("surname",user.getSurname());

        update.put("address",user.getAddress());

        update.put("favoriteProduct",user.getFavoriteProduct());

        update.put("purchasedProduct",user.getPurchasedProducts());

        update.put("url_img",user.getUrl_img());

        update.put("shoppingCar",user.getShoppingCar());


        db.collection("users").document(user.getEmail()).update(update);

    }

    public boolean InsertUser(User user){

        //mapa de usuario
        Map<String,Object> userM = new HashMap();


        userM.put("url_img",user.getUrl_img());
        userM.put("name",user.getName());
        userM.put("surname",user.getSurname());
        userM.put("email",user.getEmail());
        userM.put("address",user.getAddress());
        userM.put("shoppingCar",user.getShoppingCar());
        userM.put("favoriteProduct",user.getFavoriteProduct());
        userM.put("purchasedProduct",user.getPurchasedProducts());
        db.collection("users").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(!documentSnapshot.exists()){
                    db.collection("users").document(user.getEmail()).set(userM);
                    correcto= true;
                }
                else{
                    correcto= false;
                }
            }
        });

        return correcto;
    }

    public void uploadPhoto(Uri uri, String identificador, String folder, Context e){
        progressDialog= new ProgressDialog(e);
        progressDialog.setMessage("Actualizando foto");
        progressDialog.show();

        StorageReference storageRef = storage.getReference().child(folder).child(identificador+".jpa");

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
                        db.collection("users").document(identificador).update(map);
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
