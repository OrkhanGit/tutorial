package org.example.tutorial.thymeleaf.service;

import lombok.RequiredArgsConstructor;
import org.example.tutorial.exception.NotFound;
import org.example.tutorial.repository.UploadFileNameRepository;
import org.example.tutorial.thymeleaf.model.TextbookWeb;
import org.example.tutorial.thymeleaf.model.TutorialDetailsWeb;
import org.example.tutorial.thymeleaf.model.TutorialWeb;
import org.example.tutorial.thymeleaf.model.UploadFileNameWeb;
import org.example.tutorial.thymeleaf.repository.RepositoryTutorial;
import org.example.tutorial.thymeleaf.repository.RepositoryUploadFileNameWeb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceTutorial {
    private final RepositoryTutorial repositoryTutorial;
    private final RepositoryUploadFileNameWeb repositoryUploadFileNameWeb;

    public List<TutorialWeb> getAllTutorialWeb() {
        return repositoryTutorial.findAll();
    }

    public TutorialWeb getTutorialWebById(Long id) {
        return repositoryTutorial.findById(id).orElseThrow(()-> new NotFound("not found id"));
    }

    public Optional<TutorialWeb> findById(long id) {
        return repositoryTutorial.findById(id);
    }

    public void createTutorialWeb(TutorialWeb tutorialWeb) {
        repositoryTutorial.save(tutorialWeb);
    }

    public void deleteById(Long id) {
        repositoryTutorial.deleteById(id);
    }

    @Transactional
    public void updateTutorial(long id, TutorialWeb tutorialWeb) {
        TutorialWeb found = repositoryTutorial.findById(id).orElseThrow(()-> new NotFound("not found id"));
        found.setId(id);
        found.setTitle(tutorialWeb.getTitle());
        found.setDescription(tutorialWeb.getDescription());

        found.setTutorialDetailsWeb(tutorialWeb.getTutorialDetailsWeb());
        found.setTextbookWeb(tutorialWeb.getTextbookWeb());
        found.setUploadFileNameWeb(tutorialWeb.getUploadFileNameWeb());

        repositoryTutorial.save(found);

    }
}