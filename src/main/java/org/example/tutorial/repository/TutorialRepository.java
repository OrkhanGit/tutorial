package org.example.tutorial.repository;

import org.example.tutorial.model.Tutorial;

import java.util.List;

public interface TutorialRepository {

    int save(Tutorial tutorial);
    List<Tutorial> findAll();
    Tutorial findById(Long id);
    List<Tutorial> findByPublished(boolean published);
    int update(Tutorial tutorial);
    void delete(Long id);
    void deleteAll();
    Tutorial findByTitle(String title);
    void deleteByTitle(String title);


}
