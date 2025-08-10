package org.example.tutorial.mapper;

import org.example.tutorial.dto.UploadFileNameDto;
import org.example.tutorial.model.UploadFileName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperUploadFileName {

    UploadFileNameDto toDto(UploadFileName uploadFileName);

}
