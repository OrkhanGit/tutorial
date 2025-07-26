package org.example.tutorial.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial.exception.NotFoundId;
import org.example.tutorial.exception.NotFoundList;
import org.example.tutorial.exception.NotFoundPublished;
import org.example.tutorial.exception.NotFoundTitle;
import org.example.tutorial.model.Tutorial;
import org.example.tutorial.repository.TutorialRepository;
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
        String cleanedTitle = tutorial.getTitle().replaceAll("\\s+", "").toLowerCase();
        Tutorial founded = tutorialRepository.findByTitle(cleanedTitle);
        if (founded != null) {
            if (cleanedTitle.equals(founded.getTitle())) {
                log.info("Tutorial title already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body("\"" + cleanedTitle + "\"" + " already exists");
            }
        }
        tutorial.setPublished(false);
        tutorial.setTitle(cleanedTitle);
        tutorialRepository.save(tutorial);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tutorial created successfully.");
    }

    public ResponseEntity<List<Tutorial>> getAllTutorial() {
        List<Tutorial> founded = tutorialRepository.findAll();
        if (founded.isEmpty()) {
            log.info("Tutorial list is empty.");
            throw new NotFoundList("not found list from db");
        }
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<Tutorial> getTutorialById(Long id) {
        Tutorial founded = tutorialRepository.findById(id);
        if(founded == null || id != founded.getId()) {
            log.info("id not found.");
            throw new NotFoundId("not found id from db");
        }
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<Tutorial> findByTitle(String title) {
        String cleanTitle = title.replaceAll("\\s+", "").toLowerCase();
        Tutorial founded = tutorialRepository.findByTitle(cleanTitle);
        if(founded == null) {
            log.info("title not found.");
            throw new NotFoundTitle("not found title from db");
        }
        return ResponseEntity.status(HttpStatus.OK).body(founded);
    }

    public ResponseEntity<List<Tutorial>> getTutorialByPublished(boolean published) {
        List<Tutorial> founded = tutorialRepository.findByPublished(published);
        if(founded.isEmpty()) {
            log.info("not found published");
            throw new NotFoundPublished("not found published from db");
        }
        return ResponseEntity.status(HttpStatus.OK).body(founded);
    }

    public ResponseEntity<String> updateTutorial(long id, Tutorial tutorial) {
        Tutorial founded = tutorialRepository.findById(id);
        if(founded == null) {
            log.info("id not found.");
            throw new NotFoundId("not found id from db");
        }
        Tutorial foundedTitle = tutorialRepository.findByTitle(tutorial.getTitle().replaceAll("\\s+", "").toLowerCase());
        if (foundedTitle!=null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tutorial title already exists");
        }
        founded.setId(id);
        founded.setTitle(tutorial.getTitle().replaceAll("\\s+", "").toLowerCase());
        founded.setDescription(tutorial.getDescription());
        founded.setPublished(tutorial.getPublished());
        tutorialRepository.update(founded);
        return ResponseEntity.status(HttpStatus.OK).body("Tutorial updated successfully.");
    }

    public ResponseEntity<String> deleteTutorial(long id) {
        Tutorial founded = tutorialRepository.findById(id);
        if(founded == null) {
            log.info("id not found.");
            throw new NotFoundId("not found id from db");
        }
        tutorialRepository.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(founded.getTitle() + " Deleted successfully.");
    }

    public ResponseEntity<String> deleteTutorialByTitle(String title) {
        String cleanTitle = title.replaceAll("\\s+", "").toLowerCase();
        Tutorial founded = tutorialRepository.findByTitle(cleanTitle);
        if(founded == null) {
            log.info("title not found.");
            throw new NotFoundTitle("not found title from db");
        }
        tutorialRepository.deleteByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(title + " deleted successfully.");
    }

    public ResponseEntity<String> deleteAllTutorials() {
        List<Tutorial> founded = tutorialRepository.findAll();
        if(founded.isEmpty()) {
            log.info("Tutorial list is empty.");
            throw new NotFoundList("not found list from db");
        }
        tutorialRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All tutorial deleted successfully.");
    }


    public void getInfo( HttpServletRequest request) {

        System.out.println(request.getMethod());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getQueryString());

    }

}
