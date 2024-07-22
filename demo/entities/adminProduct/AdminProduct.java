package com.example.demo.entities.adminProduct;

import com.example.demo.product.Product;
import com.example.demo.users.admin.Admin;
import com.example.demo.users.client.Client;
import jakarta.persistence.*;

@Entity
@Table(name = "admin_product")
public class AdminProduct
{
    @EmbeddedId
    private AdminProductId id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("adminId")
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    public AdminProduct(){}

    public AdminProduct(AdminProductId id, Admin admin, Product product, int quantity) {
        this.id = id;
        this.admin = admin;
        this.product = product;
        this.quantity = quantity;
    }

    public AdminProductId getId() {
        return id;
    }

    public void setId(AdminProductId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
