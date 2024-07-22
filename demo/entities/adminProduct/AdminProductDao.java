package com.example.demo.entities.adminProduct;

import com.example.demo.product.Product;
import com.example.demo.users.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminProductDao extends JpaRepository<AdminProduct, AdminProductId>
{
    Optional<AdminProduct> findByAdminAndProduct(@Param("admin") Admin admin, @Param("product") Product product);
}
