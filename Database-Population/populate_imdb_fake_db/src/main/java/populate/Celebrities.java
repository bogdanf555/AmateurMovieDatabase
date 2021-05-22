package populate;

import utilities.DatabaseHandling;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

import static populate.Populate.displayTime;

public class Celebrities {

    public static void fetchCelebrities(Connection con, Statement statement) {

        try {

            Scanner scanner = DatabaseHandling.getScanner("D:\\workspace\\IMDb data\\celebrities.tsv");

            String[] line = scanner.nextLine().split("\t");

            for(String word : line) {

                System.out.print(word + ' ');
            }

            System.out.println();

            displayTime();

            int count = 0;
            while(scanner.hasNextLine()) {

                count++;

                if(count % 100000 == 0) {

                    System.out.println(count);
                    displayTime();
                    con.commit();
                }

                line = scanner.nextLine().split("\t");

                String title_id = line[0];
                String title_name = line[1].replace("'", "\\'");

                Integer birth = null;
                Integer death = null;

                if(!line[2].equals("\\N") && !line[2].equals("")) {
                    try {
                        birth = Integer.parseInt(line[2]);
                    } catch (Exception e) {
                    }
                }

                if(!line[3].equals("\\N") && !line[3].equals("")) {
                    try {
                        death = Integer.parseInt(line[3]);
                    } catch (Exception e) {
                    }
                }

                String insert_query;
                if(birth != null && death != null){

                    insert_query =
                            "insert into celebrities (celebrity_id, celebrity_name, birth_year, death_year) values ('%s', '%s', %s, %s)";
                    try {
                        statement.execute(String.format(insert_query, title_id, title_name, birth, death));
                    } catch (Exception e) {
                        System.out.println(title_name);
                        System.out.println(String.format(insert_query, title_id, title_name, birth, death));
                        e.printStackTrace();
                    }
                } else if(birth != null) {
                    insert_query =
                            "insert into celebrities (celebrity_id, celebrity_name, birth_year) values ('%s', '%s', %s)";
                    try {
                        statement.execute(String.format(insert_query, title_id, title_name, birth));
                    } catch (Exception e) {
                        System.out.println(title_name);
                        System.out.println(String.format(insert_query, title_id, title_name, birth, death));
                        e.printStackTrace();
                    }
                } else if(death != null) {
                    insert_query =
                            "insert into celebrities (celebrity_id, celebrity_name, death_year) values ('%s', '%s', %s)";
                    try {
                        statement.execute(String.format(insert_query, title_id, title_name, death));
                    } catch (Exception e) {
                        System.out.println(title_name);
                        System.out.println(String.format(insert_query, title_id, title_name, birth, death));
                        e.printStackTrace();
                    }
                } else {
                    insert_query =
                            "insert into celebrities (celebrity_id, celebrity_name) values ('%s', '%s')";

                    try {
                        statement.execute(String.format(insert_query, title_id, title_name));
                    } catch (Exception e) {
                        System.out.println(title_name);
                        System.out.println(String.format(insert_query, title_id, title_name, birth, death));
                        e.printStackTrace();
                    }
                }
            }

            con.commit();
            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
