package com.example.demo.entities.clientProduct;

import com.example.demo.product.Product;
import com.example.demo.users.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "client_product")
public class ClientProduct {

    @EmbeddedId
    private ClientProductId id;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id")
     private Client client;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    public ClientProduct() {}

    public ClientProduct(Client client, Product product, int quantity) {
        this.id = new ClientProductId(client.getId(), product.getId());
        this.client = client;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public ClientProductId getId() {
        return id;
    }

    public void setId(ClientProductId id) {
        this.id = id;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

