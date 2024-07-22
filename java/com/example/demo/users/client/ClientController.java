package com.example.demo.users.client;

import com.example.demo.DTOs.ClientDTO;
import com.example.demo.DTOs.ProductDTO;
import com.example.demo.entities.clientProduct.ClientProduct;
import com.example.demo.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/client")
public class ClientController
{
     private final ClientService  clientService;

    public ClientController(ClientService clientService)
    {
        this.clientService = clientService;
    }

    @GetMapping("/getClients")
    public List<ClientDTO> getClients()
    {
        List<ClientDTO> clients = new ArrayList<>();
        for (int i = 0; i < clientService.getClients().size(); i++)
        {
            Client client = clientService.getClients().get(i);
            ClientDTO clientDTO = new ClientDTO(client);
            clientDTO.setProductList(null);
            clients.add(clientDTO);
        }
        return clients;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCLient(@RequestBody Client client)
    {
        try {
            clientService.registerClient(client);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping  ("/purchaseProduct/{clientId}/admin/{adminId}/product/{productId}/numOfProduct/{numOfProduct}")
    public void purchaseProduct(@PathVariable int clientId, @PathVariable int adminId, @PathVariable int productId, @PathVariable int numOfProduct)
    {
        clientService.purchaseProduct(clientId, adminId, productId, numOfProduct);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable int id)
    {
        Client client = clientService.getById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        // find its list and get the products and set its list!!
        List<ClientProduct> productList = client.getClientProducts();
        int size = productList.size();
        List<ProductDTO> products = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            ProductDTO productDTO = new ProductDTO(productList.get(i).getProduct());
            products.add(productDTO);
        }
        ClientDTO clientDTO = new ClientDTO(client);
        clientDTO.setProductList(products);
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping
    public ResponseEntity<Product> viewProduct(@PathVariable int productId)
    {
        return clientService.viewProduct(productId);
    }
}

