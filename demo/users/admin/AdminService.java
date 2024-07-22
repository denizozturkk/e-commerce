package com.example.demo.users.admin;

import com.example.demo.DTOs.AdminDTO;
import com.example.demo.DTOs.CredentialsDto;
import com.example.demo.DTOs.SignUpDto;
import com.example.demo.entities.adminProduct.AdminProduct;
import com.example.demo.entities.adminProduct.AdminProductDao;
import com.example.demo.entities.adminProduct.AdminProductId;
import com.example.demo.entities.clientProduct.ClientProduct;
import com.example.demo.entities.clientProduct.ClientProductDao;
import com.example.demo.entities.clientProduct.ClientProductId;
import com.example.demo.mappers.AdminMapper;
import com.example.demo.product.Product;
import com.example.demo.product.ProductDao;
import com.example.demo.security.config.UserAuthenticationProvider;
import com.example.demo.users.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService
{
    @Autowired
    AdminDao adminDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    private AdminProductDao adminProductDao;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserAuthenticationProvider userAuthProvider;

    public List<Admin> getAdmins()
    {
        return adminDao.findAll();
    }
    public void registerAdmin(Admin admin)
    {
        if (!admin.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        else
        {
            if (!adminDao.findByEmail(admin.getEmail()).isEmpty()) {
                throw new IllegalArgumentException("Admin with email " + admin.getEmail() + " already exists.");
            }
            else
                adminDao.save(admin);
        }
    }

    public void deleteAdminById(int adminId)
    {
        if (adminDao.existsById(adminId)) {
            adminDao.deleteById(adminId);
        } else {
            throw new IllegalArgumentException("Client with ID " + adminId + " does not exist.");
        }
    }
    // Optional: Method to delete a client by email or another attribute
    public void deleteAdminByEmail(String email)
    {
        boolean adminExist = adminDao.findByEmail(email).isPresent();
        if (adminExist) {
            Admin admin = adminDao.findByEmail(email).get();
            adminDao.delete(admin);
        } else {
            throw new IllegalArgumentException("Client with email " + email + " does not exist.");
        }

    }

    @Transactional
    public void addProduct(Product product, int adminId, int numOfProduct, MultipartFile image) throws IOException {

        if (adminDao.existsById(adminId))
        {
            boolean productExists = productDao.existsById(product.getId());

            //ADMİN DAO DA FINDBYID() FUNC YAZMADIĞIM İÇİN OPRTIONAL DÖNDÜRÜYOR HATA OLABİLİR
            Admin admin = adminDao.findById(adminId).get();

            if(productExists)
            {
                product = productDao.getProductById(product.getId());
                product.setImageName(image.getOriginalFilename());
                product.setImageType(image.getContentType());
                product.setImageData(image.getBytes());
            }
            else
            {
                productDao.save(product);
                product = productDao.getProductById(product.getId());
            }
            product.setQuantity(product.getQuantity() + numOfProduct);
            productDao.save(product);

            Optional<AdminProduct> optionalAdminProduct = adminProductDao.findByAdminAndProduct(admin, product);

            AdminProduct adminProduct;
            if(optionalAdminProduct.isPresent())
            {
                adminProduct = optionalAdminProduct.get();
                adminProduct.setQuantity(adminProduct.getQuantity() + numOfProduct);
            }
            else
            {
                AdminProductId adminProductId = new AdminProductId(product.getId(), adminId);
                adminProduct = new AdminProduct(adminProductId, admin, product, numOfProduct);
                System.out.println("AdminProduct to be saved: " + adminProduct);
            }

            adminProductDao.save(adminProduct);

        }
        else
            throw new IllegalArgumentException("Client with ID " + adminId + " does not exist.");
    }

    public Admin getAdminById(int adminId)
    {
        return adminDao.getById(adminId);
    }

    public void editProduct(Product product, int adminId, int productId, MultipartFile image) throws IOException {
        boolean adminPresent = adminDao.findById(adminId).isPresent();
        if (adminPresent)
        {
            boolean productExists = productDao.existsById(productId);
            if (productExists)
            {
                Product product1 = productDao.getProductById(productId);
                Admin admin = adminDao.findById(adminId).get();
                Optional<AdminProduct> adminProduct = adminProductDao.findByAdminAndProduct(admin, product1);

                if(adminProduct.isPresent())
                {
                    AdminProduct adminProduct1 = adminProduct.get();

                    product1.setImageName(image.getOriginalFilename());
                    product1.setImageType(image.getContentType());
                    product1.setImageData(image.getBytes());
                    product1.setQuantity(product.getQuantity());
                    product1.setName(product.getName());
                    product1.setDescription(product.getDescription());
                    product1.setPrice(product.getPrice());
                    product1.setBrand(product.getBrand());

                    adminProduct1.setProduct(product1);
                    adminProduct1.setQuantity(product1.getQuantity());
                    adminProductDao.save(adminProduct1);

                    productDao.save(product1);
                }
                else
                    throw new IllegalArgumentException("Admin with ID " + adminId + " does not have product with ID "
                    + productId + ".");

            }
            else
                throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");

        }
        else
            throw new IllegalArgumentException("Admin with ID " + adminId + " does not exist.");

    }
//
//    public AdminDTO findByLogin(String login)
//    {
//        Admin admin = adminDao.findByLogin(login)
//                .orElseThrow(() -> new AppException("Unknown Admin", HttpStatus.NOT_FOUND));
//        return adminMapper.toAdminDTO(admin);
//    }
//
//    public AdminDTO login(CredentialsDto credentialsDto)
//    {
//        Admin admin = adminDao.findByLogin(credentialsDto.getLogin())
//                .orElseThrow(() -> new AppException("Unknown Admin", HttpStatus.NOT_FOUND));
//        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), admin.getPassword()))
//        {
//            return adminMapper.toAdminDTO(admin);
//        }
//        throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
//    }
//
//    public AdminDTO register(SignUpDto adminDto)
//    {
//        Optional<Admin> optionalAdmin = adminDao.findByLogin(adminDto.getLogin());
//        if(optionalAdmin.isPresent())
//        {
//            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
//        }
//
//        Admin admin = adminMapper.signUpToAdmin(adminDto);
//
//
//        admin.setPassword(passwordEncoder.encode(CharBuffer.wrap(adminDto.getPassword())));
//
//        Admin savedAdmin = adminDao.save(admin);
//        return adminMapper.toAdminDTO(savedAdmin);
//    }

    @Transactional
    public AdminDTO register(SignUpDto signUpDto) {
        // Check if login already exists
        if (adminDao.findByLogin(signUpDto.getLogin()).isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        else if (!signUpDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

            throw new IllegalArgumentException("Invalid email format.");
        }
        else
        {
            Admin admin = adminMapper.signUpToAdmin(signUpDto);

            admin.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

            Admin savedAdmin = adminDao.save(admin);
            return adminMapper.toAdminDTO(savedAdmin);
        }
    }

    public AdminDTO login(CredentialsDto credentialsDto) {
        Admin admin = adminDao.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown Admin", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialsDto.getPassword(), admin.getPassword())) {
            return adminMapper.toAdminDTO(admin);
        }

        throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    public AdminDTO findByLogin(String login) {
        Admin admin = adminDao.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown Admin", HttpStatus.NOT_FOUND));
        return adminMapper.toAdminDTO(admin);
    }

}
