package com.bogdan.inc.utilities;

import com.bogdan.inc.model.Celebrity;
import com.bogdan.inc.model.Title;

import java.util.ArrayList;
import java.util.List;

public class EntityParser {

    public static List<Title> parseTitles(List<String> titlesToParse) {
        List<Title> parsedTitles = new ArrayList<>();

        for (String toParse : titlesToParse) {

            String[] idAndName = toParse.split(",");
            Title title = new Title(idAndName[0], idAndName[1]);
            parsedTitles.add(title);
        }

        return parsedTitles;
    }

    public static List<Celebrity> parseCelebrities(List<String> celebritiesToParse) {
        List<Celebrity> parsedCelebrities = new ArrayList<>();

        for (String toParse: celebritiesToParse) {

            String[] idAndName = toParse.split(",");
            Celebrity celebrity = new Celebrity(idAndName[0], idAndName[1]);
            parsedCelebrities.add(celebrity);
        }

        return parsedCelebrities;
    }
}
