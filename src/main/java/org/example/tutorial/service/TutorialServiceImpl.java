package org.example.tutorial.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial.dto.TutorialDto;
import org.example.tutorial.dto.UploadFileNameDto;
import org.example.tutorial.exception.NotFound;
import org.example.tutorial.mapper.MapperTutorial;
import org.example.tutorial.model.Textbook;
import org.example.tutorial.model.Tutorial;
import org.example.tutorial.model.UploadFileName;
import org.example.tutorial.repository.TutorialRepository;
import org.example.tutorial.repository.UploadFileNameRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;
    private final UploadFileNameRepository uploadFileNameRepository;
    private final MapperTutorial mapperTutorial;
    private final MessageSource messageSource;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<String> createdTutorial(TutorialDto tutorialDto) {

        String cleanTitle = tutorialDto.getTitle().replaceAll("\\s+", "").toLowerCase();
        Optional<Tutorial> found = tutorialRepository.findByTitle(cleanTitle);
        if (found.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("\"" + tutorialDto.getTitle() + "\"" + " already exists");
        }

        Tutorial tutorial = mapperTutorial.toEntity(tutorialDto);
        tutorial.setPublished(false);

        boolean notFound = uploadFileNameRepository.findAll()
                .stream()
                .allMatch(fileDto-> uploadFileNameRepository.findById(fileDto.getId()).isEmpty());

        if(notFound){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found upload file");
        }

        Set<UploadFileName> collect = tutorialDto.getUploadFileName().stream()
                .map(fileDto -> uploadFileNameRepository.findById(fileDto.getId())
                        .orElseThrow(() -> new NotFound("UploadFile not found")))
                .collect(Collectors.toSet());
        tutorial.setUploadFileName(collect);

        if (tutorial.getTutorialDetails() != null) {
            tutorial.getTutorialDetails().setTutorial(tutorial);
        }

        if (tutorial.getTextbook() != null) {
            tutorial.getTextbook().forEach(book -> book.setTutorial(tutorial));
        }

        tutorialRepository.save(tutorial);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tutorial created successfully.");

    }

    /** asaqida gosterilmish kodda entity-de olmayan filed dto-da var ve yalniz sorqu zamani hemin field gorsenir. db-de olmayacaq. **/
    public ResponseEntity<List<TutorialDto>> getAllTutorialsDto() {
        List<TutorialDto> found = tutorialRepository.findAll()
                .stream()
                .map(tutorial -> {
                    TutorialDto dto = mapperTutorial.toDto(tutorial);
                    if (dto.getPassword() != null) {
                        dto.setPassword(dto.getPassword().replaceAll(".", "*"));
                    }
                    Locale locale = LocaleContextHolder.getLocale();
                    dto.setPurpose(messageSource.getMessage(
                            "tutorial.java.purpose", null, locale
                    ));
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(found);
    }

    public ResponseEntity<Tutorial> getTutorialById(Long id) {
        Tutorial found = tutorialRepository.findById(id)
                .orElseThrow(()-> new NotFound("not found id from db"));
        return ResponseEntity.ok(found);
    }

    public ResponseEntity<Tutorial> findByTitle(String title) {
        String cleanTitle = title.replaceAll("\\s+","").toLowerCase();
        Tutorial found = tutorialRepository.findByTitle(cleanTitle)
                .orElseThrow(()-> new NotFound("not found title from db"));
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }

    public ResponseEntity<List<Tutorial>> getTutorialByPublished(boolean published) {
        List<Tutorial> found = tutorialRepository.findByPublished(published);
        if(found.isEmpty()) {
            throw new NotFound("not found published from db");
        }
        return ResponseEntity.status(HttpStatus.OK).body(found);
    }


    public ResponseEntity<String> updateTutorial(long id, Tutorial tutorial) {
        Tutorial found = tutorialRepository.findById(id)
                .orElseThrow(()-> new NotFound("not found id from db"));
        Optional<Tutorial> existing = tutorialRepository.findByTitleAndIdNot(tutorial.getTitle(), id);
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Another tutorial with the same title already exists.");
        }
        found.setId(id);
        found.setTitle(tutorial.getTitle());
        found.setDescription(tutorial.getDescription());
        found.setPublished(tutorial.getPublished());
        found.getTutorialDetails().setPrerequisites(tutorial.getTutorialDetails().getPrerequisites());
        found.getTutorialDetails().setDifficultyLevel(tutorial.getTutorialDetails().getDifficultyLevel());
        found.setUpdatedAt(LocalDateTime.now());

        found.getTextbook().clear();
        if(tutorial.getTextbook() != null){
            for(Textbook textbook : tutorial.getTextbook()){
                textbook.setTutorial(found);
                found.getTextbook().add(textbook);
            }
        }

        found.getUploadFileName().clear();
        if(tutorial.getUploadFileName() != null){
            UploadFileName uploadFileNameEntity;
            for(UploadFileName uploadFileName : tutorial.getUploadFileName()){
                if(uploadFileName.getId() != null){
                    uploadFileNameEntity = uploadFileNameRepository.findById(uploadFileName.getId()).orElseThrow(()-> new NotFound("not found file from db"));
                }else {
                    uploadFileNameEntity =uploadFileName;
                }
                found.getUploadFileName().add(uploadFileNameEntity);
            }
        }
        tutorialRepository.save(found);
        return ResponseEntity.status(HttpStatus.OK).body("Tutorial updated successfully.");
    }

    public ResponseEntity<String> deleteTutorial(long id) {
        Tutorial found = tutorialRepository.findById(id)
                .orElseThrow(()-> new NotFound("not found id from db"));
        tutorialRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(found.getTitle() + " Deleted successfully.");
    }

    public ResponseEntity<String> deleteTutorialByTitle(String title) {
        String cleanTitle = title.replaceAll("\\s+","").toLowerCase();
        Tutorial found = tutorialRepository.findByTitle(cleanTitle)
                .orElseThrow(()-> new NotFound("not found title from db"));
        tutorialRepository.deleteById(found.getId());
        return ResponseEntity.status(HttpStatus.OK).body(title + " deleted successfully.");
    }

    public ResponseEntity<String> deleteAllTutorials() {
        List<Tutorial> found = tutorialRepository.findAll();
        if(found.isEmpty()) {
            throw new NotFound("not found list from db");
        }
        tutorialRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All tutorial deleted successfully.");
    }

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file) throws IOException {
        List<String> endOfFile = List.of(".jpg",".pdf",".png");
        Path fullPath = Paths.get("upload/" + file.getOriginalFilename());
        Files.createDirectories(fullPath.getParent());
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File is empty");
        } else if (Files.exists(fullPath)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("File already exists");
        } else if (file.getOriginalFilename() == null || endOfFile.stream().noneMatch(f -> file.getOriginalFilename().toLowerCase().endsWith(f))) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Invalid file extension.");
        }
        uploadFileNameRepository.saveFileName(file.getOriginalFilename());
        Files.write(fullPath,file.getBytes());
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
    }

    @Override
    public ResponseEntity<Resource> getImage() throws IOException {
        Path path = Path.of("upload/PHOTO.jpg").normalize();
        Resource resource = new UrlResource(path.toUri());
        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
                .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}