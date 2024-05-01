package com.example.tiendaropa.Models;

import java.util.List;

public class SellerUser extends User{

    private List<Product> soldProducts=null;

    public SellerUser(String url_img,String name, String surname, String email, String address, List<Product> shoppingCar, List<Product> favoriteProduct, List<Product> purchasedProducts, List<Product> soldProducts) {
        super(url_img,name, surname, email, address, shoppingCar, favoriteProduct, purchasedProducts);
        this.soldProducts = soldProducts;
    }

    public SellerUser(String url_img,String name, String surname, String email, String address, List<Product> soldProducts) {
        super(url_img,name, surname, email, address);
        this.soldProducts = soldProducts;
    }

    public SellerUser(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }

    @Override
    public String toString() {
        return "SellerUser{" +
                "soldProducts=" + soldProducts +
                '}';
    }
}
