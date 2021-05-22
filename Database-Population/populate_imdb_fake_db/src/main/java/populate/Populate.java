package populate;

import utilities.DatabaseHandling;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class Populate {

    public static void displayTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
    }

    public static void main(String[] argv) throws FileNotFoundException {

        try {
            Connection con = DatabaseHandling.getConnection();
            Statement statement = DatabaseHandling.getStatement(con);

            //Celebrities.fetchCelebrities(con, statement);

            //DirectorsAndWriters.fetchDirectorsAndWriters(con, statement);

            //ProfessionsAndKnownFor.fetchProfessionsAndKnownFor(con, statement);

            //Episodes.fetchEpisodes(con, statement);

            //Stars.fetchStars(con, statement);

            PlotsAndPosters.fetchPlotsAndPosters(con, statement);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MAIN BODY");
        }
    }
}