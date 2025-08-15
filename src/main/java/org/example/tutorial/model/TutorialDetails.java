package org.example.tutorial.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "tutorial_details")
@Data
public class TutorialDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @Column(name = "prerequisites")
    private String prerequisites;

    @OneToOne
    @JoinColumn(name = "tutorial_id")
    @ToString.Exclude
    @JsonBackReference
    private Tutorial tutorial;

}
