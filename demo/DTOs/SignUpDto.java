package com.example.demo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDto
{
    private int id;
    private String name;
    private String email;
    private String phone;
    private String login;
    private String address;
    private String password;

}
