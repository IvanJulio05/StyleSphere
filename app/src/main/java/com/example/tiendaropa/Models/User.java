package com.example.tiendaropa.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String url_img;//foto de perfil
    private String name;
    private String surname;
    private String email;
    private String address;
    private List<String> shoppingCar=null;
    private List<String> favoriteProduct=null;
    private List<String> purchasedProducts=null;//productos comprados

    public User(String url_img,String name, String surname, String email, String address, List<String> shoppingCar, List<String> favoriteProduct, List<String> purchasedProducts) {
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

    public List<String> getShoppingCar() {
        return shoppingCar;
    }

    public void setShoppingCar(List<String> shoppingCar) {
        this.shoppingCar = shoppingCar;
    }

    public boolean setShoppingCar(String id){

        boolean noRepetida = true;
        for(String x:this.shoppingCar){
            if(x.equals(id)){
                noRepetida = false;
                break;
            }
        }

        if(noRepetida){
            this.shoppingCar.add(id);
        }

        return noRepetida;
    }

    public List<String> getFavoriteProduct() {
        return favoriteProduct;
    }

    public void setFavoriteProduct(List<String> favoriteProduct) {
        this.favoriteProduct = favoriteProduct;
    }
    public void setFavoriteProduct(String favoriteProduct) {
        this.favoriteProduct.add(favoriteProduct);
    }

    public List<String> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<String> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public void borrarUnFavorito(String x){
        favoriteProduct.remove(x);
    }

    public boolean existe(String x){

        boolean exi = false;

        for(String i:this.favoriteProduct){
            if(i.equals(x)){
                exi = true;
                break;
            }
        }
        return exi;
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
