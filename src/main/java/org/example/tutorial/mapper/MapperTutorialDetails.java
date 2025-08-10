package org.example.tutorial.mapper;

import org.example.tutorial.dto.TutorialDetailsDto;
import org.example.tutorial.model.TutorialDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperTutorialDetails {

    TutorialDetailsDto toDto(TutorialDetails tutorialDetails);

}
