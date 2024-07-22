package com.example.demo.DTOs;

import com.example.demo.enums.Roles;
import com.example.demo.users.admin.Admin;

import javax.management.relation.Role;
import java.util.List;

public class AdminDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String login;
    private String token;
    private String address;
    private List<ProductDTO> products;
    private final Roles role = Roles.ADMIN;


    public AdminDTO() {
        // Default constructor
    }

    // Constructor to convert from Admin entity
    public AdminDTO(Admin admin)
    {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phone = admin.getPhone();
        this.address = admin.getAddress();
        this.login = admin.getLogin();
    }

    public Roles getRole() {
        return role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
