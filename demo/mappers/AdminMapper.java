package com.example.demo.mappers;

import com.example.demo.DTOs.ClientDTO;
import com.example.demo.DTOs.SignUpDto;
import com.example.demo.users.admin.Admin;
import com.example.demo.users.client.Client;
import com.example.demo.users.client.ClientService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.demo.DTOs.AdminDTO;


@Mapper(componentModel = "spring")
public interface AdminMapper
{
    AdminDTO toAdminDTO(Admin admin);

    @Mapping(target = "password", ignore = false)
    @Mapping(target = "login", ignore = false)
    @Mapping(target = "name", ignore = false)
    @Mapping(target = "id", ignore = false)
    @Mapping(target = "email", ignore = false)
    @Mapping(target = "phone", ignore = false)
    Admin signUpToAdmin(SignUpDto adminDto);



    @Mapping(target = "productList", ignore = true)
    ClientDTO toClientDTO(Client client);


    Client signUpToClient(SignUpDto signUpDto);

}

