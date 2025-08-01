package org.example.tutorial.repository;

import org.example.tutorial.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    @Query(value = "SELECT * FROM tutorial WHERE LOWER(REPLACE((title),' ','')) = ?1 LIMIT 1",nativeQuery = true)
    Optional<Tutorial> findByTitle(String title);
    List<Tutorial> findByPublished(boolean published);
    Optional<Tutorial> findByTitleAndIdNot(String title, Long id);

}