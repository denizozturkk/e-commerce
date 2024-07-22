package com.example.demo.users.admin;

import com.example.demo.DTOs.AdminDTO;
import com.example.demo.DTOs.ProductDTO;
import com.example.demo.entities.adminProduct.AdminProduct;
import com.example.demo.product.Product;
import com.example.demo.product.ProductDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/admin")
public class AdminController
{
    private final AdminService adminService;
    private final ProductDao productDao;


    public AdminController(AdminService adminService, ProductDao productDao)
    {
        this.adminService = adminService;
        this.productDao = productDao;
    }

    @GetMapping("/getAdmins")
    public List<AdminDTO> getAdmins()
    {
        List<AdminDTO> admins = new ArrayList<>();
        for(int i = 0; i < adminService.getAdmins().size(); i++)
        {
            AdminDTO adminDTO = new AdminDTO(adminService.getAdmins().get(i));
            adminDTO.setProducts(null);
            admins.add(adminDTO);
        }
        return admins;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin)
    {
        try {
            adminService.registerAdmin(admin);
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deleteAdmin/{id}")
    public void deleteAdminById(@PathVariable("id") int id)
    {
        adminService.deleteAdminById(id);
    }

    @DeleteMapping("deleteAdmin/{email}")
    public void deleteAdminByEmail(@PathVariable String email)
    {
        adminService.deleteAdminByEmail(email);
    }

    @PostMapping(value = "/addProduct/{adminId}/numOfProduct/{numOfProduct}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(@RequestPart("product") Product product, @PathVariable int adminId, @PathVariable int numOfProduct,
                                     @RequestPart("image") MultipartFile image)
    {
        try {
            System.out.println("Adding product: " + product.getName());
            System.out.println("Admin ID: " + adminId);
            System.out.println("Number of products: " + numOfProduct);
            adminService.addProduct(product, adminId, numOfProduct, image);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get/{adminId}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable int adminId)
    {
        Admin admin = adminService.getAdminById(adminId);
        if(admin == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            List<AdminProduct> productList = admin.getProductListA();
            List<ProductDTO> products = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++)
            {
                ProductDTO productDTO = new ProductDTO(productList.get(i).getProduct());
                products.add(productDTO);
            }
            AdminDTO adminDTO = new AdminDTO(admin);
            adminDTO.setProducts(products);
            return new ResponseEntity<>(adminDTO, HttpStatus.OK);
        }
    }
    @PutMapping("/editProduct/{adminId}/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable int adminId, @PathVariable int productId
    , @RequestPart("product") Product product, @RequestPart("image") MultipartFile image)
    {
        try {
            adminService.editProduct(product, adminId, productId, image);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
