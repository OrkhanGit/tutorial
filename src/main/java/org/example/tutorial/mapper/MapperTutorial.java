package org.example.tutorial.mapper;

import org.example.tutorial.dto.TutorialDto;
import org.example.tutorial.model.Tutorial;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperTutorial {

    TutorialDto toDto(Tutorial tutorial);
    Tutorial toEntity(TutorialDto dto);

}
