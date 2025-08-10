package org.example.tutorial.dto;

import lombok.Data;
import org.example.tutorial.model.Textbook;
import org.example.tutorial.model.TutorialDetails;
import org.example.tutorial.model.UploadFileName;

import java.util.List;
@Data
public class TutorialDto {

    private String title;
    private String description;
    private Boolean published;
    private TutorialDetailsDto tutorialDetails;
    private List<TextbookDto> textbook;
    private List<UploadFileNameDto> uploadFileName;



}
