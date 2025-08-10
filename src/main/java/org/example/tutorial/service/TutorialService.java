package org.example.tutorial.service;

import org.example.tutorial.dto.TutorialDto;
import org.example.tutorial.model.Tutorial;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TutorialService {

    ResponseEntity<String> createdTutorial(Tutorial tutorial);
    ResponseEntity<List<TutorialDto>> getAllTutorialsDto();
//    ResponseEntity<List<Tutorial>> getAllTutorials();
    ResponseEntity<Tutorial> getTutorialById(Long id);
    ResponseEntity<Tutorial> findByTitle(String title);
    ResponseEntity<List<Tutorial>> getTutorialByPublished(boolean published);
    ResponseEntity<String> updateTutorial(long id, Tutorial tutorial);
    ResponseEntity<String> deleteTutorial(long id);
    ResponseEntity<String> deleteTutorialByTitle(String title);
    ResponseEntity<String> deleteAllTutorials();
    ResponseEntity<String> uploadFile(MultipartFile file) throws IOException;
}
