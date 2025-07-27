package com.mosesidowu.user_service.controller;

import com.mosesidowu.user_service.dto.request.LoginRequest;
import com.mosesidowu.user_service.dto.request.UserRegistration;
import com.mosesidowu.user_service.dto.response.LoginResponse;
import com.mosesidowu.user_service.dto.response.UserRegistrationResponse;
import com.mosesidowu.user_service.exception.MicroServiceException;
import com.mosesidowu.user_service.service.UserService;
import com.mosesidowu.user_service.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable String id) {
        return ResponseEntity.ok("User with ID: " + id);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistration userRegistration) {
        try {
            UserRegistrationResponse userRegistrationResponse = userService.register(userRegistration);
            return new ResponseEntity<>(userRegistrationResponse, HttpStatus.CREATED);
        }
        catch (MicroServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = userService.login(loginRequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (MicroServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
