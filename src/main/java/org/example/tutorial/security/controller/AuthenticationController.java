package org.example.tutorial.security.controller;

import lombok.RequiredArgsConstructor;
import org.example.tutorial.security.dto.UserDto;
import org.example.tutorial.security.dto.UserRequest;
import org.example.tutorial.security.dto.UserResponse;
import org.example.tutorial.security.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/save")
    public ResponseEntity<String> save (@RequestBody UserDto userDto) {
        return authenticationService.save(userDto);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserResponse> auth (@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authenticationService.auth(userRequest));
    }

}
