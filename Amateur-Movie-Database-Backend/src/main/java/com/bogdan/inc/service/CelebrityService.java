package com.bogdan.inc.service;

import com.bogdan.inc.model.Celebrity;
import com.bogdan.inc.model.Title;
import com.bogdan.inc.repository.CelebrityRepository;
import com.bogdan.inc.utilities.EntityParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CelebrityService {

    @Autowired
    private CelebrityRepository celebrityRepository;

    public Celebrity findById(String id) {
        Celebrity celebrity = celebrityRepository.findById(id).orElse(null);

        if(celebrity != null) {

            List<String> knownForStrings = celebrityRepository.findKnownForById(id);
            if(knownForStrings != null) {
                celebrity.setKnownFor(EntityParser.parseTitles(knownForStrings));
            }

            celebrity.setProfessions(celebrityRepository.findProfessionsById(id));
        }

        return celebrity;
    }

    public List<Celebrity> findByName(String name) {

        return celebrityRepository.findCelebritiesByName(name);

        /*for(Celebrity celebrity: celebrityList) {

            celebrity.setProfessions(celebrityRepository.findProfessionsById(celebrity.getId()));
            celebrity.setKnownFor(celebrityRepository.findKnownForById(celebrity.getId()));
        }

        System.out.println("WHAT THE HELL");*/
    }
}
