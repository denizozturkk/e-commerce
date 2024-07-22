package com.example.demo.users.admin;


import com.example.demo.entities.adminProduct.AdminProduct;
import com.example.demo.entities.clientProduct.ClientProduct;
import com.example.demo.enums.Roles;
import com.example.demo.product.Product;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
public class Admin
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String login;
    private String password;
    private String phone;
    private String address;
    private final Roles role = Roles.ADMIN;


    /*
    @ManyToMany
    @JoinTable(
            name = "admin_product",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productListA = new ArrayList<>();
     */

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdminProduct> productListA = new ArrayList<>();

    public Admin(int id, String email, String name, String password, String phone, String address)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public Admin()
    {

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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    /*
    public List<Product> getProductListA() {
        return productListA;
    }

    public void setProductListA(List<Product> productListA) {
        this.productListA = productListA;
    }

     */

    public List<AdminProduct> getProductListA() {
        return productListA;
    }

    public void setProductListA(List<AdminProduct> productListA) {
        this.productListA = productListA;
    }

}
