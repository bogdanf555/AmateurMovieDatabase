package utilities;

import populate.Populate;

import javax.management.ObjectName;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Rutines {

    public static void printArray(Object[] array) {

        for(Object item: array) {
            if(item != null)
                System.out.print(item.toString() + " *** ");
        }
        System.out.println();
    }

    public static void commitAndShowMessage(Connection con, int count) {

        System.out.println(count);
        Populate.displayTime();

        try {
            con.commit();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getThoseValues(String title, String[] infoList) {

        String value = "('" + title + "', '%s')";

        return valuesToString(value, infoList);
    }

    public static String getThoseValues(String title, Integer[] infoList) {

        String value = "('" + title + "', %s)";

        return valuesToString(value, infoList);
    }

    public static String valuesToString(String value, Object[] infoList) {

        String values = "";

        for(int x = 0; x < infoList.length - 1; x++) {

            values += String.format(value, infoList[x]) + ", ";
        }

        values += String.format(value, infoList[infoList.length - 1]) + ";";

        return values;
    }
}
