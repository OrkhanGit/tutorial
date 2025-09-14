package org.example.tutorial.thymeleaf.repository;

import org.example.tutorial.thymeleaf.model.UsersWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryUsersWeb extends JpaRepository<UsersWeb, Long> {

    boolean existsByEmail(String email);
    Optional<UsersWeb> findByEmail(String email);

}
