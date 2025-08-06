package org.example.tutorial.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "textbook")
public class Textbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "book")
    private String book;

    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "tutorial_id")
    @JsonBackReference
    @ToString.Exclude
    private Tutorial tutorial;


}
