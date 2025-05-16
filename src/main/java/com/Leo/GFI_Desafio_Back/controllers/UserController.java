package com.Leo.GFI_Desafio_Back.controllers;

import com.Leo.GFI_Desafio_Back.dto.AuthRecordDto;
import com.Leo.GFI_Desafio_Back.dto.UserDeleteRecordDto;
import com.Leo.GFI_Desafio_Back.dto.UserRecordDto;
import com.Leo.GFI_Desafio_Back.models.UserModel;
import com.Leo.GFI_Desafio_Back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

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
        Optional<UserModel> user = userService.getUser(id);
        if (user.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(user);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserRecordDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRecordDto));
    }

    //PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserRecordDto userRecordDto) {
        Optional<UserModel> user = userService.updateUser(id, userRecordDto);
        if (user.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(user);
        return ResponseEntity.status(HttpStatus.OK).body("User not found or updated.");
    }

    //DELETE
    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteUser(@RequestBody @Valid UUID id) {
        try {
            boolean del = userService.deleteUser(id).isPresent();
            if (del) return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or deleted.");
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (DefaultHandlerExceptionResolver exceptionResolver) {

        }
    }
}
