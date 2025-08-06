package org.example.tutorial.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "tutorial")
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private Boolean published;

    @Column(name="createdAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "tutorial", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private TutorialDetails tutorialDetails;

    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    @JsonProperty("textBook")
    @ToString.Exclude
    private List<Textbook> textbook;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tutorial_upload_file_name",
            joinColumns = @JoinColumn(name = "tutorial_id"),
            inverseJoinColumns = @JoinColumn(name = "upload_file_name_id")
    )
    @JsonProperty("uploadFileName")
    @ToString.Exclude
    private List<UploadFileName> uploadFileName;

}