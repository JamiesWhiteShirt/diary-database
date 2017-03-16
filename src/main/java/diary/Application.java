package diary;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {
    Connection connection = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:diary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            setupSchema(statement, "/schema.sql");

            Scanner scanner = new Scanner(System.in);

            rootCommands(scanner);

            /*int intValue;
            while (true) {
                try {
                    intValue = Integer.valueOf(scanner.next());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("That is not an int, dumbass");
                }
            }
            System.out.println(intValue);*/
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

    private void rootCommands(Scanner scanner) {
        while (true) {
            System.out.println("Please enter your command (addworkout, exit)");
            String line = scanner.nextLine();
            if (line.length() == 0) continue;
            String[] splitLine = line.split(" ");
            if (splitLine.length == 1) {
                if ("addworkout".equalsIgnoreCase(splitLine[0])) {
                    addWorkout(scanner);
                    continue;
                } else if ("exit".equalsIgnoreCase(splitLine[0])) {
                    break;
                }
            }

            System.out.println("Unknown command");
        }
    }

    private void addWorkout(Scanner scanner) {
        java.util.Date time = null;
        Integer duration = null;
        Integer performance_rating = null;
        String notes = null;

        while (true) {
            System.out.println("addworkout: Please enter your command (set time, set duration, set performance_rating, set notes, cancel, commit)");
            String line = scanner.nextLine();
            if (line.length() == 0) continue;
            String[] splitLine = line.split(" ");
            if (splitLine.length >= 3) {
                String property = splitLine[1];
                String[] valueStrings = new String[splitLine.length - 2];
                System.arraycopy(splitLine, 2, valueStrings, 0, valueStrings.length);
                String value = String.join(" ", (CharSequence[]) valueStrings);
                if ("set".equalsIgnoreCase(splitLine[0])) {
                    if ("time".equalsIgnoreCase(property)) {
                        try {
                            time = dateFormat.parse(value);
                            continue;
                        } catch (ParseException e) {
                            System.out.println("Invalid time");
                        }
                    } else if ("duration".equalsIgnoreCase(property)) {
                        try {
                            duration = Integer.parseInt(value);
                            continue;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid duration");
                        }
                    } else if ("performance_rating".equalsIgnoreCase(property)) {
                        try {
                            performance_rating = Integer.parseInt(value);
                            continue;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid performance_rating");
                        }
                    } else if ("notes".equalsIgnoreCase(property)) {
                        notes = value;
                        continue;
                    }
                } else if ("exit".equalsIgnoreCase(splitLine[0])) {
                    break;
                }
            } else if (splitLine.length == 2) {
                if ("get".equalsIgnoreCase(splitLine[0])) {
                    String property = splitLine[1];
                    if ("time".equalsIgnoreCase(property)) {
                        if (time != null) {
                            System.out.println(dateFormat.format(time));
                        } else {
                            System.out.println("null");
                        }
                        continue;
                    }
                }
            } else if (splitLine.length == 1) {
                if ("cancel".equalsIgnoreCase(splitLine[0])) {
                    break;
                } else if ("commit".equalsIgnoreCase(splitLine[0])) {
                    if (time == null) {
                        System.out.println("time is unset");
                        continue;
                    }
                    if (duration == null) {
                        System.out.println("duration is unset");
                        continue;
                    }

                    try {
                        Statement statement = connection.createStatement();
                        statement.setQueryTimeout(30);

                        int id = statement.executeUpdate(
                                "INSERT INTO completed_workout" +
                                "(time, duration, performance_rating, notes)" +
                                "VALUES (" + time.getTime() + ", " + duration + ", " + performance_rating + ", \"" + notes + "\")"
                        );
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                }
            }

            System.out.println("Unknown command");
        }
    }
}
