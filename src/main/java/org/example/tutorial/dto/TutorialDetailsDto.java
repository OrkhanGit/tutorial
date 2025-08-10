package org.example.tutorial.dto;

import lombok.Data;
import org.example.tutorial.model.Tutorial;

@Data
public class TutorialDetailsDto {

    private String difficultyLevel;
    private String prerequisites;


}
