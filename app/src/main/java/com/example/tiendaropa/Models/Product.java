package com.example.tiendaropa.Models;

public class Product {

    private String id;
    private String url_img;
    private String name;
    private String description;
    private String size;
    private int price;
    private String brand;//marca
    private TypeProduct typeProduct;
    private int stock;
    private boolean freeShipping;//envio gratis

    public Product(String id,int stock,String url_img,String name,String description,String size,int price, String brand, TypeProduct typeProduct,boolean freeShipping){
        this.url_img= url_img;
        this.name = name;
        this.description = description;
        this.size = size;
        this.price = price;
        this.brand = brand;
        this.typeProduct = typeProduct;
        this.stock = stock;
        this.id= id;
        this.freeShipping = freeShipping;
    }
    public Product(){

    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "url_img='" + url_img + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", typeProduct=" + typeProduct +
                '}';
    }
}
