package org.example.tutorial.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tutorial")
@NamedEntityGraph(name = "tutorialWithAll",
attributeNodes = {
        @NamedAttributeNode("tutorialDetails"),
        @NamedAttributeNode("textbook"),
        @NamedAttributeNode("uploadFileName")
})
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

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "tutorial", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private TutorialDetails tutorialDetails;

    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
//    @JsonManagedReference
    private Set<Textbook> textbook = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tutorial_upload_file_name",
            joinColumns = @JoinColumn(name = "tutorial_id"),
            inverseJoinColumns = @JoinColumn(name = "file_name_upload_id")
    )
    @ToString.Exclude
    private Set<UploadFileName> uploadFileName;

}