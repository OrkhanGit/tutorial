package org.example.tutorial.mapper;

import org.example.tutorial.dto.TextbookDto;
import org.example.tutorial.model.Textbook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperTextbook {

    TextbookDto toDto(Textbook textbook);

}
