package com.example.demo.entities.clientProduct;

import com.example.demo.product.Product;
import com.example.demo.users.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientProductDao extends JpaRepository<ClientProduct, ClientProductId>
{
    Optional<ClientProduct> findByClientAndProduct(Client client, Product product);
}
