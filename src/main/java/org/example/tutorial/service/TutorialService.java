package org.example.tutorial.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial.model.Tutorial;
import org.example.tutorial.repository.TutorialRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorialService {

    private final TutorialRepository tutorialRepository;


    public ResponseEntity<String> createdTutorial(Tutorial tutorial) {
        try{
            tutorial.setPublished(false);
            String cleanedTitle = tutorial.getTitle().replaceAll("\\s+", "").toLowerCase();
            tutorial.setTitle(cleanedTitle);
            tutorialRepository.save(tutorial);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tutorial created successfully.");
        } catch (Exception e) {
            log.error("Tutorial creation failed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tutorial creation failed.");
        }
    }

    public ResponseEntity<List<Tutorial>> getAllTutorial() {
        try{
            List<Tutorial> founded = tutorialRepository.findAll();
            if(founded.isEmpty()) {
                log.info("Tutorial list is empty.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(founded);
        }catch (Exception e){
            log.error("Internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Tutorial> getTutorialById(Long id) {
        try{
            Tutorial founded = tutorialRepository.findById(id);
            if(founded == null || id != founded.getId()) {
                log.info("id not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(founded);
        }
        catch (EmptyResultDataAccessException e){
            log.error("Not found id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error("Internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public ResponseEntity<List<Tutorial>> getTutorialByPublished(boolean published) {
        try{
            List<Tutorial> founded = tutorialRepository.findByPublished(published);
            if(founded.isEmpty()) {
                log.info("not found published");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(founded);
        }catch (EmptyResultDataAccessException e){
            log.error("Not found Tutorial with published");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            log.error("Internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public ResponseEntity<String> updateTutorial(long id, Tutorial tutorial) {
        try{
            Tutorial founded = tutorialRepository.findById(id);
            if(founded == null || founded.getId() != id) {
                log.info("id not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            founded.setId(id);
            founded.setTitle(tutorial.getTitle().toLowerCase());
            founded.setDescription(tutorial.getDescription());
            founded.setPublished(tutorial.getPublished());
            tutorialRepository.update(founded);
            return ResponseEntity.status(HttpStatus.OK).body("Tutorial updated successfully.");
        }catch (EmptyResultDataAccessException e){
            log.error("Not found id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutorial not found.");
        }catch (Exception e){
            log.error("Internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tutorial update failed.");
        }
    }

    public ResponseEntity<String> deleteTutorial(long id) {
        try {
            Tutorial founded = tutorialRepository.findById(id);
            if(founded == null || id != founded.getId()) {
                log.info("id not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutorial not found.");
            }
            tutorialRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(founded.getTitle() + " Deleted successfully.");
        }catch (EmptyResultDataAccessException e){
            log.error("Not found id in Tutorial DB");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tutorial not found.");
        }catch (Exception e){
            log.error("Internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tutorial deletion failed.");
        }

    }

    public ResponseEntity<String> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).body("All tutorial deleted successfully.");
        }catch (Exception e){
            log.error("Internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tutorial deletion failed.");
        }

    }

    public ResponseEntity<Tutorial> findByTitle(String title) {
        try {
            Tutorial founded = tutorialRepository.findByTitle(title.toLowerCase());
            if(founded == null || !title.equals(founded.getTitle())) {
                log.info("title not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(founded);
        }catch (EmptyResultDataAccessException e){
            log.error("Not found Tutorial with title {}.", title);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            log.error("Internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    public void getInfo( HttpServletRequest request) {

        System.out.println(request.getMethod());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getQueryString());

    }
}
