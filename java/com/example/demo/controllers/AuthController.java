package com.example.demo.controllers;

import com.example.demo.DTOs.AdminDTO;
import com.example.demo.DTOs.ClientDTO;
import com.example.demo.DTOs.CredentialsDto;
import com.example.demo.DTOs.SignUpDto;
import com.example.demo.JWTdecoder.JwtDecoder;
import com.example.demo.security.config.UserAuthenticationEntryPoint;
import com.example.demo.security.config.UserAuthenticationProvider;
import com.example.demo.users.admin.AdminService;
import com.example.demo.users.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController
{
    private final AdminService adminService;
    private final UserAuthenticationProvider userAuthProvider;
    private final ClientService clientService;

    private final JwtDecoder jwtDecoder;


    @PostMapping("/loginAdmin")
    public ResponseEntity<AdminDTO> login(@RequestBody CredentialsDto credentialsDto)
    {
        AdminDTO adminDTO = adminService.login(credentialsDto);
        String token = userAuthProvider.createToken(adminDTO.getLogin(), adminDTO.getRole());
        adminDTO.setToken(token);

        System.out.println(adminDTO.getToken());

        return ResponseEntity.ok(adminDTO);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<AdminDTO> register(@RequestBody SignUpDto signUpDto)
    {
        AdminDTO adminDTO = adminService.register(signUpDto);
        String token = userAuthProvider.createToken(adminDTO.getLogin(), adminDTO.getRole());
        adminDTO.setToken(token);

        return ResponseEntity.created(URI.create("/api/v1/admin/" + adminDTO.getId()))
                .body(adminDTO);
    }


    @PostMapping("/loginClient")
    public ResponseEntity<ClientDTO> loginC(@RequestBody CredentialsDto credentialsDto)
    {
        ClientDTO clientDTO = clientService.login(credentialsDto);
        clientDTO.setToken(userAuthProvider.createToken(clientDTO.getLogin(), clientDTO.getRole()));
        return ResponseEntity.ok(clientDTO);
    }



    @PostMapping("/registerClient")
    public ResponseEntity<ClientDTO> registerC(@RequestBody SignUpDto signUpDto)
    {
        ClientDTO clientDTO = clientService.register(signUpDto);
        clientDTO.setToken(userAuthProvider.createToken(clientDTO.getLogin(), clientDTO.getRole()));
        return ResponseEntity.created(URI.create("/api/v1/client/" + clientDTO.getId()))
                .body(clientDTO);
    }

    @GetMapping("/protected")
    public String protectedTest() {
        return "This is a protected resource";
    }

}
