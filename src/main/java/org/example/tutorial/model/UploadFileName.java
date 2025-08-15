package org.example.tutorial.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "upload_file_name")
public class UploadFileName {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @CreationTimestamp
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @ManyToMany(mappedBy = "uploadFileName")
    @JsonIgnore
    private List<Tutorial> tutorial;

}
