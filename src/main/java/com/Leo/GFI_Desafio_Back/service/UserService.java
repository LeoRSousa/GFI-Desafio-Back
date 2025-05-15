package com.Leo.GFI_Desafio_Back.service;

import com.Leo.GFI_Desafio_Back.dto.UserRecordDto;
import com.Leo.GFI_Desafio_Back.models.UserModel;
import com.Leo.GFI_Desafio_Back.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //CREATE
    @Transactional
    public UserModel createUser(UserRecordDto userRecordDto) {
        UserModel user = new UserModel();
        user.setEmail(userRecordDto.email());
        user.setPassword(userRecordDto.password());

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
