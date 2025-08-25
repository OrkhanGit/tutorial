package org.example.tutorial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Set;

@Data
public class TutorialDto {

    @Pattern(regexp = "^[a-zA-Z\\s]+$",
            message = "Username yalnız hərflərdən ibarət olmalıdır")
    private String title;
    private String description;
    private Boolean published;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$",
            message = "Password minimum 8 simvol olmalı, ən az bir rəqəm və bir böyük hərf içerməlidir")
    private String password;

    private String purpose; // entitiyde olmayan field
    private TutorialDetailsDto tutorialDetails;
    private Set<TextbookDto> textbook;
    private Set<UploadFileNameDto> uploadFileName;



}
