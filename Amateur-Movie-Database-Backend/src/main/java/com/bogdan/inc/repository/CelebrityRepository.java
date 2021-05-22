package com.bogdan.inc.repository;

import com.bogdan.inc.model.Celebrity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CelebrityRepository extends JpaRepository<Celebrity, String> {

    @Query(value =  "SELECT * FROM celebrities " +
                    "WHERE celebrity_name REGEXP :celebrity " +
                    "ORDER BY celebrity_id " +
                    "LIMIT 4", nativeQuery = true)
    List<Celebrity> findCelebritiesByName(@Param("celebrity") String name);

    @Query(value =  "SELECT professions.profession_name FROM professions " +
                    "JOIN professions_of_celebrities poc " +
                    "ON professions.profession_id = poc.profession_id " +
                    "WHERE poc.celebrity_id = :id", nativeQuery = true)
    List<String> findProfessionsById(@Param("id") String id);

    @Query(value =  "SELECT titles.title_id, title_name FROM titles, known_for " +
                    "WHERE celebrity_id = :id and known_for.title_id = titles.title_id", nativeQuery = true)
    List<String> findKnownForById(@Param("id") String id);
}
