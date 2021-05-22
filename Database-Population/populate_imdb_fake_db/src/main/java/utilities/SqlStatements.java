package utilities;

import java.sql.SQLException;
import java.sql.Statement;

public class SqlStatements {

    public static void insertMultipleRows(Statement statement, String table_name,
                                          String columns, String values) throws SQLException {

        String insertStatement = "INSERT INTO %s ( %s ) values %s";
        statement.execute(String.format(insertStatement, table_name, columns, values));
    }
}
