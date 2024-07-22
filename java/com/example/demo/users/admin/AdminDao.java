package com.example.demo.users.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer>
{
    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByLogin(String login);
}
