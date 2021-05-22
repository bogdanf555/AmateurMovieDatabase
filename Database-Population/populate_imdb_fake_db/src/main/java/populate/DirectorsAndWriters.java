package populate;

import utilities.DatabaseHandling;
import utilities.Rutines;
import utilities.SqlStatements;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DirectorsAndWriters {

    public static void fetchDirectorsAndWriters(Connection con, Statement statement) throws FileNotFoundException {

        Scanner scanner = DatabaseHandling.getScanner("D:\\workspace\\IMDb data\\crew.tsv");

        DatabaseHandling.printAndGetFirstLine(scanner);

        Populate.displayTime();

        String[] line;
        String[] directors;
        String[] writers;

        String title_id;

        int count = 0;

        while(scanner.hasNextLine()) {

            count++;

            if(count % 100000 == 0) {

                System.out.println(count);
                Populate.displayTime();
                try {
                    con.commit();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            line = scanner.nextLine().split("\t");

            if(line[0].length() != 10) {
                continue;
            }

            title_id = line[0];

            if(!line[1].equals("\\N")) {

                directors = line[1].split(",");

                try {
                    SqlStatements
                            .insertMultipleRows(
                                    statement,
                                    "directors_of_titles",
                                    "title_id, director_id",
                                    Rutines.getThoseValues(title_id, directors)
                            );
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }

            if(!line[2].equals("\\N")) {

                writers = line[2].split(",");

                try {
                    SqlStatements.insertMultipleRows(
                            statement,
                            "writers_of_titles",
                            "title_id, writer_id",
                            Rutines.getThoseValues(title_id, writers));
                } catch (Exception e) {
                    //e.printStackTrace();
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
