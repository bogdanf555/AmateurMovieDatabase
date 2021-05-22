package com.bogdan.inc.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="titles")
public class Title {

    @Id
    @Column(name = "title_id")
    private String id;

    @Column(name = "title_name")
    private String name;
    @Column(name = "poster", columnDefinition = "TEXT")
    private String poster;
    @Column(name = "plot", columnDefinition = "TEXT")
    private String plot;

    private Integer year;
    private Integer votes;
    private Integer duration;

    private Double rating;

    private Boolean adult;

    @Transient
    private List<String> genres;

    @Transient
    private List<Celebrity> directors;

    @Transient
    private List<Celebrity> writers;

    @Transient
    private List<Celebrity> stars;

    public Title() {}

    public Title(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {

        return this.id + ": " + this.name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }


    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Celebrity> getWriters() {
        return writers;
    }

    public void setWriters(List<Celebrity> writers) {
        this.writers = writers;
    }

    public List<Celebrity> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Celebrity> directors) {
        this.directors = directors;
    }

    public List<Celebrity> getStars() {
        return stars;
    }

    public void setStars(List<Celebrity> stars) {
        this.stars = stars;
    }
}
