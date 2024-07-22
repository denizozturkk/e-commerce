package com.example.demo.product;

import com.example.demo.DTOs.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController
{
    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping("/getProducts")
    public List<ProductDTO> getProducts()
    {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(int i = 0; i < productService.getProducts().size(); i++)
        {
            Product product = productService.getProducts().get(i);
            productDTOS.add(new ProductDTO(product));
        }
        return productDTOS;
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product)
    {
        productService.addProduct(product);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProductById(@PathVariable("id") int id)
    {
        productService.deleteProductById(id);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam String keyword)
    {
        List<Product> products = productService.searchProduct(keyword);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products)
        {
            productDTOS.add(new ProductDTO(product));
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable int productId)
    {
        Product product = productService.getProductById(productId);
        byte[] imageFile = product.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }
    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int productId)
    {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        ProductDTO productDTO = new ProductDTO(product);
        return ResponseEntity.ok(productDTO);
    }
}


