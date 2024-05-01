package com.example.tiendaropa.Persistence;

import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.Models.User;

import java.util.List;

public interface Repository {

    //CRUD

    //read

    //buscar usuario por correo
    User getUserByEmail();

    //buscar solo la informacion del usuario.
    User getOnlyUserInf();

    //retornar todos los usuario
    List<User> getAllUser();

    //retornar productos por busqueda
    List<Product> getAllProductBySearch(String search);

    //retornar un producto
    Product getProductById(String id);

    //create

    //guardar usuario
    void saveUser(User user);

    //guardar producto
    void saveProduct(Product product);

    //delete

    void deleteAllUser();

    void deleteUserByEmail();

    void deleteAllProducto();

    void deleteProductById();

}
