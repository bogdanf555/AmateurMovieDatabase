package populate;

import utilities.DatabaseHandling;
import utilities.Rutines;
import utilities.SqlStatements;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Stars {

    public static void fetchStars(Connection con, Statement statement) throws FileNotFoundException {

        Scanner scanner = DatabaseHandling.getScanner("D:\\workspace\\IMDb data\\principals.tsv");

        DatabaseHandling.printAndGetFirstLine(scanner);

        String[] line;

        String titleId;
        String celebId;
        String category;
        Integer ordering;

        String lastTitle;
        String values = "";

        boolean placed = false;

        line = scanner.nextLine().split("\t");

        lastTitle = titleId = line[0];

        ordering = Integer.parseInt(line[1]);
        celebId = line[2];
        category = line[3];

        if((category.equals("actor") || category.equals("actress")) && ordering <= 4) {

            values += "('" + titleId + "', " + ordering + ", '" + celebId + "')";
            placed = true;
        }

        int count = 0;
        Populate.displayTime();

        while(scanner.hasNextLine()) {

            count++;

            if (count % 100000 == 0) {

                Rutines.commitAndShowMessage(con, count);
            }

            line = scanner.nextLine().split("\t");

            titleId = line[0];

            if(!titleId.equals(lastTitle)) {

                if(!values.equals("")) {

                    if(values.charAt(values.length() - 2) == ',') {
                        values = values.substring(0, values.length() - 2);
                    }

                    try {
                        SqlStatements.insertMultipleRows(
                                statement,
                                "stars",
                                "title_id, ordering, celebrity_id",
                                values
                        );
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    //System.out.println(values);

                    values = "";
                }

            } else {

                if(placed)
                    values += ", ";
            }

            placed = false;

            ordering = Integer.parseInt(line[1]);
            celebId = line[2];
            category = line[3];

            if((category.equals("actor") || category.equals("actress")) && ordering <= 4) {

                values += "('" + titleId + "', " + ordering + ", '" + celebId + "')";
                placed = true;
            }

            lastTitle = titleId;
        }

        try {
            con.commit();
            DatabaseHandling.closeConnection(con, statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Populate.displayTime();
        System.out.println(count);
    }
}
