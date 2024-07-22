package com.example.demo.DTOs;

import com.example.demo.enums.Roles;
import com.example.demo.users.client.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String login;
    private String token;
    private String address;
    private final Roles role = Roles.CLIENT;

    private List<ProductDTO> productList;

    public ClientDTO() {
        // Default constructor
    }

    // Constructor to convert from Client entity
    public ClientDTO(Client client)
    {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.address = client.getAddress();
    }

    public Roles getRole() {
        return role;
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

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }
}

