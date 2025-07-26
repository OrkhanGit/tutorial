package org.example.tutorial.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.tutorial.model.Tutorial;
import org.example.tutorial.service.TutorialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorial")
@RequiredArgsConstructor
public class TutorialController {

    private final TutorialService tutorialService;

    @PostMapping
    public ResponseEntity<String> createdTutorial (@RequestBody Tutorial tutorial){
        return tutorialService.createdTutorial(tutorial);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tutorial>>  getAllTutorials() {
        return tutorialService.getAllTutorial();
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

    @GetMapping("/info")
    public void info(HttpServletRequest request){
        tutorialService.getInfo(request);
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<String> deleteTutorialByTitle(@PathVariable String title) {
        return tutorialService.deleteTutorialByTitle(title);
    }


}
