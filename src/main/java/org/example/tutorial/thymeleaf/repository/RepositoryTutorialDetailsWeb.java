package org.example.tutorial.thymeleaf.repository;

import org.example.tutorial.thymeleaf.model.TutorialDetailsWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTutorialDetailsWeb extends JpaRepository<TutorialDetailsWeb, Long> {
}
