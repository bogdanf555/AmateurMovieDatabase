package com.bogdan.inc.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "celebrities")
public class Celebrity {

    @Id
    @Column(name = "celebrity_id")
    private String id;
    @Column(name = "celebrity_name")
    private String fullName;
    private String description;
    @Column(name = "poster", columnDefinition = "TEXT")
    private String poster;

    @Column(name = "birth_year")
    private Integer birthYear;
    @Column(name = "death_year")
    private Integer deathYear;

    @Transient
    private List<String> professions;
    @Transient
    private List<Title> knownFor;

    public Celebrity() {}

    public Celebrity(String id, String name) {
        this.id = id;
        this.fullName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<String> getProfessions() {
        return professions;
    }

    public void setProfessions(List<String> professions) {
        this.professions = professions;
    }

    public List<Title> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<Title> knownFor) {
        this.knownFor = knownFor;
    }
}
