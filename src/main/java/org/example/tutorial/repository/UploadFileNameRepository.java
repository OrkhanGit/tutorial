package org.example.tutorial.repository;

import org.example.tutorial.model.UploadFileName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UploadFileNameRepository extends JpaRepository<UploadFileName, Long> {

    @Transactional//update, delete, insert zamani lazimdir
    @Modifying//update, delete, insert zamani lazimdir
    @Query(value = "INSERT INTO upload_file_name (file_name,upload_time) VALUES (?1,CURRENT_TIMESTAMP )",nativeQuery = true)
    void saveFileName(String filename);

}
