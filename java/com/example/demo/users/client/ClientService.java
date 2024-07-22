package com.example.demo.users.client;

import com.example.demo.DTOs.AdminDTO;
import com.example.demo.DTOs.ClientDTO;
import com.example.demo.DTOs.CredentialsDto;
import com.example.demo.DTOs.SignUpDto;
import com.example.demo.entities.adminProduct.AdminProduct;
import com.example.demo.entities.adminProduct.AdminProductDao;
import com.example.demo.entities.clientProduct.ClientProduct;
import com.example.demo.entities.clientProduct.ClientProductDao;
import com.example.demo.entities.clientProduct.ClientProductId;
import com.example.demo.mappers.AdminMapper;
import com.example.demo.product.Product;
import com.example.demo.product.ProductDao;
import com.example.demo.product.ProductService;
import com.example.demo.users.admin.Admin;
import com.example.demo.users.admin.AdminDao;
import com.example.demo.users.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService
{
    @Autowired
    ClientDao clientDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ClientProductDao clientProductDao;
    @Autowired
    private AdminProductDao adminProductDao;
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Client> getClients()
    {
       return clientDao.findAll();
    }
    public void addClient(int id, String name, String email, String password, String phone, String address)
    {
        Client client = new Client(id, name, email, password, phone ,address);
        clientDao.save(client); // This will insert the data into the database
    }
    public void registerClient(Client client)
    {
        if (!client.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        else
        {
            if (!clientDao.existsByEmail(client.getEmail())) {
                throw new IllegalArgumentException("Admin with email " + client.getEmail() + " already exists.");
            }
            else
                clientDao.save(client);
        }
    }

    public void deleteClientById(int clientId)
    {
        if (clientDao.existsById(clientId)) {
            clientDao.deleteById(clientId);
        } else {
            throw new IllegalArgumentException("Client with ID " + clientId + " does not exist.");
        }
    }
    // Optional: Method to delete a client by email or another attribute
    public void deleteClientByEmail(String email)
    {
        Client client = clientDao.findByEmail(email);
        if (client != null) {
            clientDao.delete(client);
        } else {
            throw new IllegalArgumentException("Client with email " + email + " does not exist.");
        }
    }


    public void purchaseProduct(int clientId, int adminId, int productId, int numOfProduct) {

        Client client = clientDao.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Admin admin = adminDao.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        boolean adminProductExist = adminProductDao.findByAdminAndProduct(admin, product).isPresent();
        if(adminProductExist)
        {
            AdminProduct adminProduct = adminProductDao.findByAdminAndProduct(admin, product).get();
            if(adminProduct.getQuantity() >= numOfProduct)
            {
                product.setQuantity(product.getQuantity() - numOfProduct);

                if(product.getQuantity() == 0)
                    product.setExist(false);

                productDao.save(product);
                adminProduct.setQuantity(adminProduct.getQuantity() - numOfProduct);
                adminProductDao.save(adminProduct);

                if(clientProductDao.findByClientAndProduct(client, product).isPresent())
                {
                    ClientProduct clientProduct = clientProductDao.findByClientAndProduct(client, product).get();
                    clientProduct.setQuantity(clientProduct.getQuantity() + numOfProduct);
                    clientProductDao.save(clientProduct);
                }
                else
                {
                    ClientProductId clientProductId = new ClientProductId();
                    clientProductId.setClientId(clientId);
                    clientProductId.setProductId(productId);

                    ClientProduct newClientProduct = new ClientProduct();
                    newClientProduct.setId(clientProductId);
                    newClientProduct.setClient(client);
                    newClientProduct.setProduct(product);
                    newClientProduct.setQuantity(0);

                    newClientProduct.setQuantity(newClientProduct.getQuantity() + numOfProduct);
                    clientProductDao.save(newClientProduct);

                }
            }
            else
            {
                throw new IllegalArgumentException("Not enough products in the stock of that admin. Check other sellers!!");
            }
        }
        else
        {
            throw new IllegalArgumentException("This admin does not have any product you want to purchase.");
        }

    }

    public Client getById(int id) {
        return clientDao.getById(id);
    }

    public ResponseEntity<Product> viewProduct(int productId)
    {
        if(productDao.existsById(productId))
        {
            return ResponseEntity.ok(productDao.findById(productId).get());
        }
        else
            return ResponseEntity.notFound().build();
    }





    @Transactional
    public ClientDTO register(SignUpDto signUpDto) {

        if (clientDao.findByLogin(signUpDto.getLogin()).isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        else if (!signUpDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

            throw new IllegalArgumentException("Invalid email format.");
        }
        else
        {
            Client client = adminMapper.signUpToClient(signUpDto);

            client.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

            Client savedClient = clientDao.save(client);

            return adminMapper.toClientDTO(savedClient);
        }
    }

    public ClientDTO login(CredentialsDto credentialsDto) {
        Client client = clientDao.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown CL", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialsDto.getPassword(), client.getPassword())) {
            return adminMapper.toClientDTO(client);
        }

        throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    public ClientDTO findByLogin(String login) {
        Client client = clientDao.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown CL", HttpStatus.NOT_FOUND));
        return adminMapper.toClientDTO(client);
    }

}

