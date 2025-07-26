package org.example.tutorial.repository;

import lombok.RequiredArgsConstructor;
import org.example.tutorial.model.Tutorial;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor()
public class TutorialRepositoryJDBC implements TutorialRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(Tutorial tutorial) {
        return jdbcTemplate.update("INSERT INTO tutorial(title,description,published) VALUES (?,?,?)",
                new Object[]{tutorial.getTitle(),tutorial.getDescription(),tutorial.getPublished()});
    }

    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.query("SELECT * FROM tutorial",
                BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public Tutorial findByTitle(String title) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM tutorial WHERE title=?",
                    new BeanPropertyRowMapper<>(Tutorial.class),
                    title);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public Tutorial findById(Long id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM tutorial WHERE id = ?",
                    new BeanPropertyRowMapper<>(Tutorial.class),
                    id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        try{
            return jdbcTemplate.query("SELECT * FROM tutorial WHERE published = ?",
                    new BeanPropertyRowMapper<>(Tutorial.class),
                    published);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public int update(Tutorial tutorial) {
        return jdbcTemplate.update("UPDATE tutorial SET title = ? , description = ?, published = ? WHERE id = ?",
                new Object[]{tutorial.getTitle(),tutorial.getDescription(),tutorial.getPublished(),tutorial.getId()}
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM tutorial WHERE id = ?",id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM tutorial");
    }

    @Override
    public void deleteByTitle(String title) {
        jdbcTemplate.update("DELETE FROM tutorial WHERE title = ?",title);
    }
}
