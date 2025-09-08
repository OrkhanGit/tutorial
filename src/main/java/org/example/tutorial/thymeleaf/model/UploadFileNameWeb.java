package org.example.tutorial.thymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "upload_file_name_web")
public class UploadFileNameWeb {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @CreationTimestamp
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @ManyToMany(mappedBy = "uploadFileNameWeb")
    @JsonIgnore
    private List<TutorialWeb> tutorialWeb;
}
