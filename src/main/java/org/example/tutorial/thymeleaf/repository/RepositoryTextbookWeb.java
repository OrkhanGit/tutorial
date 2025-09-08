package org.example.tutorial.thymeleaf.repository;

import org.example.tutorial.thymeleaf.model.TextbookWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTextbookWeb extends JpaRepository<TextbookWeb, Long> {
}
