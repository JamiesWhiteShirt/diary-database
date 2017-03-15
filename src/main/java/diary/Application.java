package diary;

import java.sql.*;

public class Application {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:diary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS test (" +
                    "test_col INTEGER" +
                    ")"
            );
            statement.executeUpdate(
                    "INSERT INTO test VALUES (1)"
            );
            statement.executeUpdate(
                    "INSERT INTO test VALUES (2)"
            );
            ResultSet rs = statement.executeQuery("SELECT * FROM test");
            while (rs.next()) {
                int value = rs.getInt("test_col");
                System.out.println(value);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
