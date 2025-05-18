package com.Leo.GFI_Desafio_Back.controllers;

import com.Leo.GFI_Desafio_Back.dto.*;
import com.Leo.GFI_Desafio_Back.models.UserModel;
import com.Leo.GFI_Desafio_Back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<AuthRecordDto> login(@RequestBody UserRecordDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(userRecordDto));
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id){
        Optional<UserResponseRecordDto> user = userService.getUser(id);
        if (user.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(user);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponseDto<String>("User not found.", "NOT_FOUND"));
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<UserResponseRecordDto> createUser(@RequestBody UserRecordDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRecordDto));
    }

    //PUT
    @PutMapping("/update/email/{id}")
    public ResponseEntity<GenericResponseDto<String>> updateEmail(@PathVariable UUID id, @RequestBody @Valid UpdateEmailDto dto) {
        String response = userService.updateEmail(id, dto.email());
        if (response.equals("Email updated successfully."))
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<String>(response, "OK"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponseDto<String>(response, "NOT_FOUND"));
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<GenericResponseDto<String>> updatePassword(@PathVariable UUID id, @RequestBody @Valid UpdatePasswordDto dto) {
        String response = userService.updatePassword(id, dto.password());
        if (response.equals("Password updated successfully."))
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<String>(response, "OK"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponseDto<String>(response, "NOT_FOUND"));
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            Optional<UserResponseRecordDto> del = userService.deleteUser(id);
            if (del.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto<String>("User deleted successfully.", del.get().email()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponseDto<String>("User not found or deleted.", "NOT_FOUND"));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
