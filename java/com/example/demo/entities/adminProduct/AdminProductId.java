package com.example.demo.entities.adminProduct;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AdminProductId implements Serializable
{
    private int adminId;
    private int productId;

    public AdminProductId(){}

    public AdminProductId(int productId, int adminId)
    {
        this.productId = productId;
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminProductId that = (AdminProductId) o;
        return adminId == that.adminId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, productId);
    }
}
