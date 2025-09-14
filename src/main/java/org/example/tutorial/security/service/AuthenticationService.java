package org.example.tutorial.security.service;

import lombok.RequiredArgsConstructor;
import org.example.tutorial.security.dto.UserDto;
import org.example.tutorial.security.dto.UserRequest;
import org.example.tutorial.security.dto.UserResponse;
import org.example.tutorial.security.enums.Roles;
import org.example.tutorial.security.model.Users;
import org.example.tutorial.security.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> save(UserDto userDto) {

        Users user = Users.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .gender(userDto.getGender())
                .role(Roles.ADMIN)
                .build();

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");

    }

    public UserResponse auth(UserRequest userRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        Users user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).build();

    }
}
