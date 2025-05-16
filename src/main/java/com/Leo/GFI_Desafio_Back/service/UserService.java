package com.Leo.GFI_Desafio_Back.service;

import com.Leo.GFI_Desafio_Back.dto.AuthRecordDto;
import com.Leo.GFI_Desafio_Back.dto.UserRecordDto;
import com.Leo.GFI_Desafio_Back.models.UserModel;
import com.Leo.GFI_Desafio_Back.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //LOGIN
    public AuthRecordDto login(UserRecordDto userRecordDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userRecordDto.email(), userRecordDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.genToken((UserModel) auth.getPrincipal());
        return new AuthRecordDto(token);
    }

    //CREATE
    @Transactional
    public UserModel createUser(UserRecordDto userRecordDto) {
        UserModel user = new UserModel();
        user.setEmail(userRecordDto.email());
        user.setPassword(new BCryptPasswordEncoder().encode(userRecordDto.password()));

        return userRepository.save(user);
    }

    //READ (by ID)
    public Optional<UserModel> getUser(UUID id) {
        return userRepository.findById(id);
    }

    //UPDATE
    @Transactional
    public Optional<UserModel> updateUser(UUID id, UserRecordDto updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setEmail(updatedUser.email());
            existingUser.setPassword(updatedUser.password());
            return userRepository.save(existingUser);
        });
    }

    //DELETE
    public Optional<UserModel> deleteUser(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent())userRepository.deleteById(id);
        return user;
    }
}
