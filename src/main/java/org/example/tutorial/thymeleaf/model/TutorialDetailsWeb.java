package org.example.tutorial.thymeleaf.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tutorial_details_web")
@Getter
@Setter
public class TutorialDetailsWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @Column(name = "prerequisites")
    private String prerequisites;

    @OneToOne
    @JoinColumn(name = "tutorial_web_id")
    @ToString.Exclude
    @JsonBackReference
    private TutorialWeb tutorialWeb;
}
