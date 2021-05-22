package populate;

import utilities.DatabaseHandling;
import utilities.Rutines;
import utilities.SqlStatements;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Episodes {

    public static void fetchEpisodes(Connection con, Statement statement) throws FileNotFoundException {

        Scanner scanner = DatabaseHandling.getScanner("D:\\workspace\\IMDb data\\episodes.tsv");

        DatabaseHandling.printAndGetFirstLine(scanner);

        String[] line;
        String episodeId;
        String parentId;
        Integer episodeNr, seasonNr;

        int count = 0;
        Populate.displayTime();

        boolean flag = false;

        while(scanner.hasNextLine()) {

            count++;

            if(count % 100000 == 0) {

                Rutines.commitAndShowMessage(con, count);
            }

            line = scanner.nextLine().split("\t");

            episodeId = line[0];
            parentId = line[1];

            try {
                seasonNr = Integer.parseInt(line[2]);
            } catch (Exception e) {
                seasonNr = null;
            }

            try {
                episodeNr = Integer.parseInt(line[3]);
            } catch (Exception e) {
                episodeNr = null;
            }

            String columns;
            String values;

            if(seasonNr != null && episodeNr != null) {

                columns = "episode_id, parent_id, season_nr, episode_nr";
                values = "('" + episodeId + "', '" + parentId + "', " + seasonNr + ", " + episodeNr + ")";
            } else if(seasonNr != null) {

                columns = "episode_id, parent_id, season_nr";
                values = "('" + episodeId + "', '" + parentId + "', " + seasonNr + ")";
            } else if(episodeNr != null) {

                columns = "episode_id, parent_id, episode_nr";
                values = "('" + episodeId + "', '" + parentId + "', " + episodeNr + ")";
            } else {

                columns = "episode_id, parent_id";
                values = "('" + episodeId + "', '" + parentId + "')";
            }

            try {
                SqlStatements.insertMultipleRows(
                        statement,
                        "episodes",
                        columns,
                        values
                );
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        System.out.println(count);

        try {
            con.commit();
            DatabaseHandling.closeConnection(con, statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Populate.displayTime();
    }
 }
