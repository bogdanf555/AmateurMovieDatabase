package populate;

import org.apache.commons.text.WordUtils;
import utilities.DatabaseHandling;
import utilities.Rutines;
import utilities.SqlStatements;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfessionsAndKnownFor {

    public static void fetchProfessionsAndKnownFor(Connection con, Statement statement)
            throws FileNotFoundException, SQLException {

        final char[] delimiters = { ' ', '_', ',' };

        String[] line;

        String celeb_id;
        String[] professions;
        String[] knownFor;

        int count = 0;

        Map<String, Integer> professionMap = new HashMap<>();

        ResultSet result = statement.executeQuery("select * from professions");

        while(result.next()) {

            String name = result.getString("profession_name");
            Integer id = result.getInt("profession_id");
            professionMap.put(name, id);
        }

        Scanner scanner = DatabaseHandling.getScanner("D:\\workspace\\IMDb data\\celebrities.tsv");

        DatabaseHandling.printAndGetFirstLine(scanner);

        Populate.displayTime();

        while (scanner.hasNextLine()) {

            count++;

            if(count % 100000 == 0) {

                Rutines.commitAndShowMessage(con, count);
            }

            line = scanner.nextLine().split("\t");

            celeb_id = line[0];

            if(!line[4].equals("\\N") && !line[4].equals("")) {

                line[4] = line[4].replace("_", " ");
                line[4] = WordUtils.capitalizeFully(line[4], delimiters);

                professions = line[4].split(",");
                Integer[] prof = new Integer[professions.length];

                //Rutines.printArray(professions);

                for(int i = 0; i < professions.length; i++) {
                    try {
                        prof[i] = professionMap.get(professions[i]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    SqlStatements.insertMultipleRows(
                            statement,
                            "professions_of_celebrities",
                            "celebrity_id, profession_id",
                            Rutines.getThoseValues(celeb_id, prof));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(!line[5].equals("\\N") && !line[5].equals("")) {

                knownFor = line[5].split(",");

                //Rutines.printArray(knownFor);

                try {
                    SqlStatements.insertMultipleRows(
                            statement,
                            "known_for",
                            "celebrity_id, title_id",
                            Rutines.getThoseValues(celeb_id, knownFor));
                } catch (Exception e) {
                    //e.printStackTrace();
                    //System.out.println(Rutines.getThoseValues(celeb_id, knownFor));
                }
            }
        }

        try {
            con.commit();
            DatabaseHandling.closeConnection(con, statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(count);
        Populate.displayTime();
    }
}
