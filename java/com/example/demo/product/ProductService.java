package com.example.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService
{
    @Autowired
    ProductDao productDao;

    public List<Product> getProducts()
    {
        return productDao.findAll();
    }
    public void addProduct(Product product)
    {
        productDao.save(product); // This will insert the data into the database
    }

    public void deleteProductById(int productId)
    {
        if (productDao.existsById(productId)) {
            productDao.deleteById(productId);
        } else {
            throw new IllegalArgumentException("Client with ID " + productId + " does not exist.");
        }
    }
//    public List<Product> searchProduct(String keyword)
//    {
//        List<Product> products = productDao.
//                findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,keyword);
//        return products;
//    }
public List<Product> searchProduct(String keyword)
{
    return productDao.searchProduct(keyword);
}
    public Product getProductImagetById(int productId)
    {
        return productDao.findById(productId).get();
    }
    public Product getProductById(int productId)
    {
        return productDao.findById(productId).get();
    }
}
