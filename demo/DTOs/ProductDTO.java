package com.example.demo.DTOs;

import com.example.demo.product.Product;
import jakarta.persistence.Column;

public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imageType;
    private String imageName;
    private byte[] imageData;
    private String brand;
    private String category;
    private int quantity;

    // Constructors
    public ProductDTO() {
        // Default constructor
    }

    // Constructor to convert from Product entity
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
         this.imageType = product.getImageType();
         this.imageName = product.getImageName();
         this.imageData = product.getImageData();
        this.brand = product.getBrand();
        this.category = product.getCategory();
        this.quantity = product.getQuantity();
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

