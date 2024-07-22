package com.example.demo.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CredentialsDto
{
    private String login;
    private String password;
}
