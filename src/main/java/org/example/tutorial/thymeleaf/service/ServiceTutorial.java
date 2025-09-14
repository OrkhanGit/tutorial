package org.example.tutorial.thymeleaf.service;

import lombok.RequiredArgsConstructor;
import org.example.tutorial.exception.NotFound;
import org.example.tutorial.thymeleaf.model.TutorialWeb;
import org.example.tutorial.thymeleaf.model.UsersWeb;
import org.example.tutorial.thymeleaf.repository.RepositoryTutorial;
import org.example.tutorial.thymeleaf.repository.RepositoryUploadFileNameWeb;
import org.example.tutorial.thymeleaf.repository.RepositoryUsersWeb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceTutorial {
    private final RepositoryTutorial repositoryTutorial;
    private final RepositoryUploadFileNameWeb repositoryUploadFileNameWeb;
    private final RepositoryUsersWeb repositoryUsersWeb;
    private final PasswordEncoder passwordEncoder;

    public List<TutorialWeb> getAllTutorialWeb() {
        return repositoryTutorial.findAll();
    }

    public TutorialWeb getTutorialWebById(Long id) {
        return repositoryTutorial.findById(id).orElseThrow(()-> new NotFound("not found id"));
    }

    public Optional<TutorialWeb> findById(long id) {
        return repositoryTutorial.findById(id);
    }

    public void createTutorialWeb(TutorialWeb tutorialWeb) {
        repositoryTutorial.save(tutorialWeb);
    }

    public void deleteById(Long id) {
        repositoryTutorial.deleteById(id);
    }

    @Transactional
    public void updateTutorial(long id, TutorialWeb tutorialWeb) {
        TutorialWeb found = repositoryTutorial.findById(id).orElseThrow(()-> new NotFound("not found id"));
        found.setId(id);
        found.setTitle(tutorialWeb.getTitle());
        found.setDescription(tutorialWeb.getDescription());

        found.setTutorialDetailsWeb(tutorialWeb.getTutorialDetailsWeb());
        found.setTextbookWeb(tutorialWeb.getTextbookWeb());
        found.setUploadFileNameWeb(tutorialWeb.getUploadFileNameWeb());

        repositoryTutorial.save(found);

    }

    @Transactional
    public ResponseEntity<String> createUser(UsersWeb usersWeb) {

        if(repositoryUsersWeb.existsByEmail(usersWeb.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        usersWeb.setPassword(passwordEncoder.encode(usersWeb.getPassword()));

        repositoryUsersWeb.save(usersWeb);

        return ResponseEntity.status(HttpStatus.CREATED).body(" Account created successfully.");
    }

}