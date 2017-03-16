package diary;

import diary.commandcontext.Context;
import diary.commandcontext.MainContext;

import java.sql.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public enum Application {
    INSTANCE;

    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        INSTANCE.run();
    }

    private void run() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:diary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            setupSchema(statement, "/schema.sql");

            Scanner scanner = new Scanner(System.in);

            Stack<Context> stack = new Stack<>();
            stack.push(new MainContext());

            while (!stack.isEmpty()) {
                System.out.println(stack.stream().map(Context::getName).collect(Collectors.joining(" > ", "", " > ")));
                stack.peek().read(stack, scanner.nextLine().trim());
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

    private void setupSchema(Statement statement, String schema) throws SQLException {
        Scanner scanner = new Scanner(Application.class.getResourceAsStream(schema));
        scanner.useDelimiter(";");
        while (scanner.hasNext()) {
            String statementString = scanner.next();
            System.out.println(statementString);
            statement.executeUpdate(statementString);
        }
    }
}
