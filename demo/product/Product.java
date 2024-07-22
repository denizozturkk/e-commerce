package com.example.demo.product;

import com.example.demo.entities.adminProduct.AdminProduct;
import com.example.demo.entities.clientProduct.ClientProduct;
import com.example.demo.users.admin.Admin;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private String name;
    private double price;
    private String description;
    private String imageType;
    private String imageName;
    private boolean exist;

    @Column(nullable = true)
    private byte [] imageData;

    private String brand;
    private String category;

    @Column(nullable = false)
    private Integer quantity = 0;

/*
    @JsonIgnore
    @ManyToMany(mappedBy = "productList")
    private List<Client> clientList = new ArrayList<>();

 */
@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
private List<ClientProduct> clientProducts = new ArrayList<>();

   /* @JsonIgnore
    @ManyToMany(mappedBy = "productListA")
    private List<Admin> adminList = new ArrayList<>();

    */

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdminProduct> adminProducts = new ArrayList<>();


    public Product(int id, String name, double price, String description)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public Product(){}

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
/*
    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

 */

    public List<ClientProduct> getClientProducts() {
        return clientProducts;
    }

    public void setClientProducts(List<ClientProduct> clientProducts) {
        this.clientProducts = clientProducts;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /*public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }
     */

    public List<AdminProduct> getAdminProducts() {
        return adminProducts;
    }

    public void setAdminProducts(List<AdminProduct> adminProducts) {
        this.adminProducts = adminProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(price, product.price) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(imageType, product.imageType) && Objects.equals(imageName, product.imageName) && Objects.deepEquals(imageData, product.imageData) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category) && Objects.equals(quantity, product.quantity) && Objects.equals(clientProducts, product.clientProducts) && Objects.equals(adminProducts, product.adminProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description, imageType, imageName, Arrays.hashCode(imageData), brand, category, quantity, clientProducts, adminProducts);
    }
}
