package org.example.tutorial.thymeleaf.repository;

import org.example.tutorial.thymeleaf.model.TutorialWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTutorial extends JpaRepository<TutorialWeb, Long> {
}
