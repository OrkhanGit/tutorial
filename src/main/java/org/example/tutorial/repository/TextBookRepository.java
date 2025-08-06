package org.example.tutorial.repository;

import org.example.tutorial.model.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextBookRepository extends JpaRepository<Textbook, Long> {
}
