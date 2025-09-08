package org.example.tutorial.thymeleaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "textbook_web")
public class TextbookWeb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "book")
    private String book;

    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "tutorialWeb_id")
    @ToString.Exclude
    private TutorialWeb tutorialWeb;
}
