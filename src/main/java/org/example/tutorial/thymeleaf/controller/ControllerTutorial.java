package org.example.tutorial.thymeleaf.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tutorial.thymeleaf.model.TextbookWeb;
import org.example.tutorial.thymeleaf.model.TutorialWeb;
import org.example.tutorial.thymeleaf.model.UploadFileNameWeb;
import org.example.tutorial.thymeleaf.service.ServiceTutorial;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ControllerTutorial {

    private final ServiceTutorial serviceTutorial;

    @GetMapping("/index")
    public String viewHomePage(Model model) {
        model.addAttribute("alltutlist", serviceTutorial.getAllTutorialWeb());
        return "index";
    }

    @GetMapping("/main")
    public String homePage() {
        return "main";
    }

    @GetMapping("/tutorial/{id}")
    public String getTutorialWeb(@PathVariable Long id, Model model) {
        TutorialWeb tutorialWeb = serviceTutorial.getTutorialWebById(id);
        model.addAttribute("tutorialWeb", tutorialWeb);
        return "tutorial-view";
    }

    @GetMapping("/tutorials/create")
    public String showCreateForm(Model model) {
        TutorialWeb tutorialWeb = new TutorialWeb();
        tutorialWeb.getTextbookWeb().add(new TextbookWeb());
        tutorialWeb.getUploadFileNameWeb().add(new UploadFileNameWeb());
        model.addAttribute("tutorialWeb", new TutorialWeb());
        return "create-tutorial";
    }

    @PostMapping("/tutorials/create")
    public String createTutorialWeb(@Valid @ModelAttribute("tutorialWeb") TutorialWeb tutorialWeb,
                                    BindingResult bindingResult,
                                    Model model) {

        if (bindingResult.hasErrors()) {
            return "create-tutorial";
        }
        if (tutorialWeb.getTutorialDetailsWeb() != null) {
            tutorialWeb.getTutorialDetailsWeb().setTutorialWeb(tutorialWeb);
        }
        if (tutorialWeb.getTextbookWeb() != null) {
            tutorialWeb.getTextbookWeb().forEach(textbookWeb -> textbookWeb.setTutorialWeb(tutorialWeb));
        }
        serviceTutorial.createTutorialWeb(tutorialWeb);
        return "redirect:/index";
    }

    @DeleteMapping("/tutorial/delete/{id}")
    public String deleteTutorialWeb(@PathVariable Long id) {
        serviceTutorial.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/tutorials/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Optional<TutorialWeb> tutorialWeb = serviceTutorial.findById(id);
        if (tutorialWeb.isPresent()) {
            model.addAttribute("tutorialWeb", tutorialWeb.get());
            return "tutorial-update"; // Update səhifəsi
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/tutorials/update/{id}")
    public String updateTutorial(@PathVariable("id") long id,
                                 @ModelAttribute("tutorialWeb") TutorialWeb tutorialWeb,
                                 RedirectAttributes redirectAttributes) {
        try {
            serviceTutorial.updateTutorial(id, tutorialWeb);
            redirectAttributes.addFlashAttribute("successMessage", "Tutorial updated successfully!");
            return "redirect:/tutorial/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating tutorial: " + e.getMessage());
            return "redirect:/tutorial/" + id;
        }
    }




}
