package org.example.tutorial.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tutorial.dto.TutorialDto;
import org.example.tutorial.model.Tutorial;
import org.example.tutorial.service.TutorialService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tutorial")
@RequiredArgsConstructor
public class TutorialController {

    private final TutorialService tutorialService;

    @PostMapping
    public ResponseEntity<String> createdTutorial (@Valid @RequestBody TutorialDto tutorialDto){
        return tutorialService.createdTutorial(tutorialDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TutorialDto>>  getAllTutorialsDto() {
        return tutorialService.getAllTutorialsDto();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable Long id) {
        return tutorialService.getTutorialById(id);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Tutorial> findByTitle(@PathVariable String title) {
        return tutorialService.findByTitle(title);
    }

    @GetMapping("/published")
    public ResponseEntity<List<Tutorial>> getTutorialByPublished(@RequestParam boolean published) {
        return tutorialService.getTutorialByPublished(published);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable long id , @RequestBody Tutorial tutorial) {
        return tutorialService.updateTutorial(id,tutorial);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteTutorial(@PathVariable long id) {
        return tutorialService.deleteTutorial(id);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllTutorials() {
        return tutorialService.deleteAllTutorials();
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<String> deleteTutorialByTitle(@PathVariable String title) {
        return tutorialService.deleteTutorialByTitle(title);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return tutorialService.uploadFile(file);
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage() throws IOException{
        return tutorialService.getImage();
    }


}
