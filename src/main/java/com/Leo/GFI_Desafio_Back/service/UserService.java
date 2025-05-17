package com.Leo.GFI_Desafio_Back.service;

import com.Leo.GFI_Desafio_Back.dto.AuthRecordDto;
import com.Leo.GFI_Desafio_Back.dto.InvestmentResponseRecordDto;
import com.Leo.GFI_Desafio_Back.dto.UserRecordDto;
import com.Leo.GFI_Desafio_Back.dto.UserResponseRecordDto;
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
import java.util.stream.Collectors;

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
        var user = (UserModel) auth.getPrincipal();
        var token = tokenService.genToken(user);
        return new AuthRecordDto(token, user.getId());
    }

    //CREATE
    @Transactional
    public UserResponseRecordDto createUser(UserRecordDto userRecordDto) {
        UserModel user = new UserModel();
        user.setEmail(userRecordDto.email());
        user.setPassword(new BCryptPasswordEncoder().encode(userRecordDto.password()));
        UserModel created = userRepository.save(user);
        return new UserResponseRecordDto(created.getId(), created.getEmail(),
                created.getInvestments()
                    .stream()
                    .map(InvestmentResponseRecordDto::fromModel)
                    .collect(Collectors.toSet()));
    }

    //READ (by ID)
    public Optional<UserResponseRecordDto> getUser(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent()) {
            UserResponseRecordDto userResponseRecordDto = new UserResponseRecordDto(user.get().getId(), user.get().getEmail(),
                    user.get().getInvestments()
                            .stream()
                            .map(InvestmentResponseRecordDto::fromModel)
                            .collect(Collectors.toSet())
            );
            return Optional.of(userResponseRecordDto);
        }
        return Optional.empty();
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
    public Optional<UserResponseRecordDto> deleteUser(UUID id) {
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isPresent()) userRepository.deleteById(id);
        return Optional.of(new UserResponseRecordDto(user.get().getId(), user.get().getEmail(),
                user.get().getInvestments()
                        .stream()
                        .map(InvestmentResponseRecordDto::fromModel)
                        .collect(Collectors.toSet())));
    }
}
