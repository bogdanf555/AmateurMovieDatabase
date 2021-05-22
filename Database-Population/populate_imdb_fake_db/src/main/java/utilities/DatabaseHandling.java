package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class DatabaseHandling {

    public static void closeConnection(Connection con, Statement statement) throws SQLException {

        statement.close();
        con.close();
    }

    public static void printAndGetFirstLine(Scanner scanner) {

        String[] line = scanner.nextLine().split("\t");

        for(String word : line) {

            System.out.print(word + ' ');
        }

        System.out.println();
    }

    public static Scanner getScanner(String path) throws FileNotFoundException {

        File celeb_files = new File(path);
        return new Scanner(celeb_files);
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        //com.mysql.jdbc.Driver
        Class.forName("org.mariadb.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/imdb_fake", "root",
                "root");
        con.setAutoCommit(false);

        return con;
    }

    public static Statement getStatement(Connection con) throws SQLException {

        return con.createStatement();
    }
}
