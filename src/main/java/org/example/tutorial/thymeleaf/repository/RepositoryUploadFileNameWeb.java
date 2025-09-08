package org.example.tutorial.thymeleaf.repository;

import org.example.tutorial.thymeleaf.model.UploadFileNameWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryUploadFileNameWeb extends JpaRepository<UploadFileNameWeb,Long> {


}
