package com.bogdan.inc.service;

import com.bogdan.inc.model.Title;
import com.bogdan.inc.repository.TitleRepository;
import com.bogdan.inc.utilities.EntityParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {

    @Autowired
    private TitleRepository titleRepository;

    public Title findById(String id) {
        Title title = titleRepository.findById(id).orElse(null);

        if(title != null) {
            title.setGenres(titleRepository.findGenresByTitleId(id));

            List<String> directorStrings = titleRepository.findDirectorsById(id);
            if(directorStrings != null) {
                title.setDirectors(EntityParser.parseCelebrities(directorStrings));
            }

            List<String> writerStrings = titleRepository.findWritersById(id);
            if(writerStrings != null) {
                title.setWriters(EntityParser.parseCelebrities(writerStrings));
            }

            List<String> starStrings = titleRepository.findStarsById(id);
            if(starStrings != null) {
                title.setStars(EntityParser.parseCelebrities(starStrings));
            }
        }

        return title;
    }

    public List<Title> findByName(String name) {
        return titleRepository.findTitleByName(name);
    }
}
