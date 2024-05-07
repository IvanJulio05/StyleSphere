package com.example.tiendaropa.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String url_img;//foto de perfil
    private String name;
    private String surname;
    private String email;
    private String address;
    private List<Product> shoppingCar=null;
    private List<Product> favoriteProduct=null;
    private List<Product> purchasedProducts=null;//productos comprados

    public User(String url_img,String name, String surname, String email, String address, List<Product> shoppingCar, List<Product> favoriteProduct, List<Product> purchasedProducts) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.shoppingCar = shoppingCar;
        this.favoriteProduct = favoriteProduct;
        this.purchasedProducts = purchasedProducts;
        this.url_img = url_img;
    }

    public User(String url_img,String name, String surname, String email, String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.url_img = url_img;
    }

    public User(String name, String surname, String email, String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
    }

    public User(){
       shoppingCar=new ArrayList<>();
       favoriteProduct=new ArrayList<>();
       purchasedProducts=new ArrayList<>();
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(List<Product> shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public List<Product> getFavoriteProduct() {
        return favoriteProduct;
    }

    public void setFavoriteProduct(List<Product> favoriteProduct) {
        this.favoriteProduct = favoriteProduct;
    }

    public List<Product> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<Product> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", shoppingCar=" + shoppingCar +
                ", favoriteProduct=" + favoriteProduct +
                ", purchasedProducts=" + purchasedProducts +
                '}';
    }
}
