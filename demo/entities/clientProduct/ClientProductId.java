package com.example.demo.entities.clientProduct;


import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClientProductId implements Serializable {

    private int clientId;
    private int productId;

    public ClientProductId() {}

    public ClientProductId(int clientId, int productId) {
        this.clientId = clientId;
        this.productId = productId;
    }

    // Getters, setters, equals, and hashCode methods
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientProductId that = (ClientProductId) o;
        return clientId == that.clientId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, productId);
    }
}

