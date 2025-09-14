package org.example.tutorial.thymeleaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
@Table(name = "users_web")
public class UsersWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Email(message = "Entered incorrectly")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

}
