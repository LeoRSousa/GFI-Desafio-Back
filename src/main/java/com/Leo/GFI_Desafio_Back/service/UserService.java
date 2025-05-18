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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(userRecordDto.password()));
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

    //UPDATE EMAIL
    @Transactional
    public String updateEmail(UUID id, String email) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) return "Email not updated.";

        UserModel updatedUser = user.get();
        updatedUser.setEmail(email);
        userRepository.save(updatedUser);
        return "Email updated successfully.";
    }

    //UPDATE PASSWORD
    @Transactional
    public String updatePassword(UUID id, String password) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) return "Password not updated.";

        UserModel updatedUser = user.get();
        updatedUser.setPassword(passwordEncoder.encode(password));
        return "Password updated successfully.";
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
