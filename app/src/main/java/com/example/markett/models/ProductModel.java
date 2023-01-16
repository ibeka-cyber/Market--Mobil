package com.example.markett.models;

public class ProductModel {
    String name,description,img_url,price,discount,type,rating;

    public ProductModel(String name, String description, String img_url, String price, String discount, String type, String rating) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.rating = rating;
    }

    public ProductModel() {
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String prise) {
        this.price = prise;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
