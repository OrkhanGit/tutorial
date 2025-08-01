package org.example.tutorial.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial.exception.NotFound;
import org.example.tutorial.model.Tutorial;
import org.example.tutorial.repository.TutorialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;

    public ResponseEntity<String> createdTutorial(Tutorial tutorial) {
        String cleanTitle = tutorial.getTitle().replaceAll("\\s+","").toLowerCase();
        Optional<Tutorial> found = tutorialRepository.findByTitle(cleanTitle);
        if (found.isPresent()) {
            log.info("Tutorial title already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("\"" + tutorial.getTitle() + "\"" + " already exists");
        }
        tutorial.setPublished(false);
        tutorial.setTitle(tutorial.getTitle());
        tutorialRepository.save(tutorial);
        log.info("create new tutorial");
        return ResponseEntity.status(HttpStatus.CREATED).body("Tutorial created successfully.");
    }

    public ResponseEntity<List<Tutorial>> getAllTutorial() {
        List<Tutorial> found = tutorialRepository.findAll();
        if (found.isEmpty()) {
            log.info("Tutorial list is empty.");
        }
        log.info("found all tutorial");
        return ResponseEntity.ok(found);
    }

    public ResponseEntity<Tutorial> getTutorialById(Long id) {
        Tutorial found = tutorialRepository.findById(id)
                .orElseThrow(()-> new NotFound("not found id from db"));
        log.info("found id");
        return ResponseEntity.ok(found);
    }

    public ResponseEntity<Tutorial> findByTitle(String title) {
        String cleanTitle = title.replaceAll("\\s+","").toLowerCase();
        Tutorial found = tutorialRepository.findByTitle(cleanTitle)
                .orElseThrow(()-> new NotFound("not found title from db"));
        log.info("found title");
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    public ResponseEntity<List<Tutorial>> getTutorialByPublished(boolean published) {
        List<Tutorial> found = tutorialRepository.findByPublished(published);
        if(found.isEmpty()) {
            log.info("not found published");
            throw new NotFound("not found published from db");
        }
        log.info("found published");
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    public ResponseEntity<String> updateTutorial(long id, Tutorial tutorial) {
        Tutorial found = tutorialRepository.findById(id)
                .orElseThrow(()-> new NotFound("not found id from db"));
        Optional<Tutorial> existing = tutorialRepository.findByTitleAndIdNot(tutorial.getTitle(), id);
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Another tutorial with the same title already exists.");
        }
        found.setId(id);
        found.setTitle(tutorial.getTitle());
        found.setDescription(tutorial.getDescription());
        found.setPublished(tutorial.getPublished());
        tutorialRepository.save(found);
        log.info("Tutorial updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body("Tutorial updated successfully.");
    }

    public ResponseEntity<String> deleteTutorial(long id) {
        Tutorial found = tutorialRepository.findById(id)
                .orElseThrow(()-> new NotFound("not found id from db"));
        tutorialRepository.deleteById(id);
        log.info("deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(found.getTitle() + " Deleted successfully.");
    }

    public ResponseEntity<String> deleteTutorialByTitle(String title) {
        String cleanTitle = title.replaceAll("\\s+","").toLowerCase();
        Tutorial found = tutorialRepository.findByTitle(cleanTitle)
                .orElseThrow(()-> new NotFound("not found title from db"));
        tutorialRepository.deleteById(found.getId());
        log.info("deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(title + " deleted successfully.");
    }

    public ResponseEntity<String> deleteAllTutorials() {
        List<Tutorial> found = tutorialRepository.findAll();
        if(found.isEmpty()) {
            log.info("Tutorial list is empty.");
            throw new NotFound("not found list from db");
        }
        tutorialRepository.deleteAll();
        log.info("All tutorial deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body("All tutorial deleted successfully.");
    }

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file) throws IOException {
        String path = "upload/";
        List<String> endOfFile = List.of(".jpg",".pdf",".png");
        Path fullPath = Paths.get(path + file.getOriginalFilename());
        Files.createDirectories(fullPath.getParent());
        if(file.isEmpty()){
            log.info("file is empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File is empty");
        } else if (Files.exists(fullPath)) {
            log.info("file already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("File already exists");
        } else if (file.getOriginalFilename() == null || endOfFile.stream().noneMatch(f -> file.getOriginalFilename().toLowerCase().endsWith(f))) {
            log.info("Invalid file extension.");
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Invalid file extension.");
        }
        Files.write(fullPath,file.getBytes());
        log.info("File uploaded successfully");
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
    }
}