package com.example.tiendaropa.Persistence;

import com.example.tiendaropa.Models.Product;
import com.example.tiendaropa.Models.User;

import java.util.List;

public class FireBaseRepository implements Repository{
    @Override
    public User getUserByEmail() {
        return null;
    }

    @Override
    public User getOnlyUserInf() {
        return null;
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public List<Product> getAllProductBySearch(String search) {
        return null;
    }

    @Override
    public Product getProductById(String id) {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void saveProduct(Product product) {

    }

    @Override
    public void deleteAllUser() {

    }

    @Override
    public void deleteUserByEmail() {

    }

    @Override
    public void deleteAllProducto() {

    }

    @Override
    public void deleteProductById() {

    }
}
