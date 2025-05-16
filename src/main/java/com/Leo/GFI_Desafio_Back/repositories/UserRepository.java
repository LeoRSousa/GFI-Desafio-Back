package com.Leo.GFI_Desafio_Back.repositories;

import com.Leo.GFI_Desafio_Back.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByEmail(String login);
}
