package com.bogdan.inc.repository;

import com.bogdan.inc.model.Celebrity;
import com.bogdan.inc.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, String> {

    @Query(value =  "SELECT genres.genre_name " +
                    "FROM genres JOIN genres_of_titles " +
                    "ON genres.genre_id = genres_of_titles.genre_id " +
                    "WHERE genres_of_titles.title_id = :id", nativeQuery = true)
    List<String> findGenresByTitleId(@Param("id") String id);

    @Query(value =  "SELECT director_id, celebrity_name " +
                    "FROM celebrities, directors_of_titles " +
                    "WHERE title_id = :id " +
                    "and director_id = celebrity_id " +
                    "ORDER BY director_id " +
                    "LIMIT 4", nativeQuery = true)
    List<String> findDirectorsById(@Param("id") String id);

    @Query(value =  "SELECT writer_id, celebrity_name " +
                    "FROM celebrities, writers_of_titles " +
                    "WHERE title_id = :id " +
                    "and celebrity_id = writer_id " +
                    "ORDER BY writer_id " +
                    "LIMIT 4", nativeQuery = true)
    List<String> findWritersById(@Param("id") String id);

    @Query(value =  "SELECT celebrities.celebrity_id, celebrity_name " +
                    "FROM celebrities, stars " +
                    "WHERE title_id = :id " +
                    "and celebrities.celebrity_id = stars.celebrity_id", nativeQuery = true)
    List<String> findStarsById(@Param("id") String id);

    @Query(value =  "SELECT * " +
                    "FROM titles " +
                    "WHERE title_name regexp :name " +
                    "and votes > 100 " +
                    "ORDER BY votes DESC " +
                    "LIMIT 4", nativeQuery = true)
    List<Title> findTitleByName(@Param("name") String name);
}
