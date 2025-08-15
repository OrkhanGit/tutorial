package org.example.tutorial.dto;

import lombok.Data;
import org.example.tutorial.model.Textbook;
import org.example.tutorial.model.TutorialDetails;
import org.example.tutorial.model.UploadFileName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class TutorialDto {

    private String title;
    private String description;
    private Boolean published;
    private String password;
    private String name; // entitiyde olmayan field
    private TutorialDetailsDto tutorialDetails;
    private Set<TextbookDto> textbook;
    private Set<UploadFileNameDto> uploadFileName;



}
