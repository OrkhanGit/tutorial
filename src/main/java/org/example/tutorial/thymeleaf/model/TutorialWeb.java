package org.example.tutorial.thymeleaf.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "tutorialWeb")
public class TutorialWeb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", unique = true)
    @Pattern(regexp = "^[a-zA-Z\\s]+$",
            message = "Must contain only letters.")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private Boolean published;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "password")
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$",
            message = "Must be at least 8 characters long, with at least one number and one uppercase letter.")
    private String password;

    @OneToOne(mappedBy = "tutorialWeb", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private TutorialDetailsWeb tutorialDetailsWeb;

    @OneToMany(mappedBy = "tutorialWeb", cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<TextbookWeb> textbookWeb = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tutorial_upload_file_name_web",
            joinColumns = @JoinColumn(name = "tutorial_web_id"),
            inverseJoinColumns = @JoinColumn(name = "file_name_upload_web_id")
    )
    @ToString.Exclude
    private List<UploadFileNameWeb> uploadFileNameWeb = new ArrayList<>();
}
