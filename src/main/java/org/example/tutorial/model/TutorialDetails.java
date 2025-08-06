package org.example.tutorial.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "tutorialDetails")
@Data
public class TutorialDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "difficultyLevel")
    private String difficultyLevel;

    @Column(name = "prerequisites")
    private String prerequisites;

    @OneToOne
    @JoinColumn(name = "tutorial_id")
    @JsonBackReference
    @ToString.Exclude
    private Tutorial tutorial;

}
