package com.example.demo.users.client;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer>
{

    Client findByEmail(String email);
    Client getById(int id);
    boolean existsByEmail(String email);

    Optional<Client> findByLogin(String login);
}
