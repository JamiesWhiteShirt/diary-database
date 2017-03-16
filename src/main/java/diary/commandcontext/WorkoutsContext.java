package diary.commandcontext;

import diary.Application;
import diary.commandcontext.command.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

public class WorkoutsContext extends Context {
    public WorkoutsContext() {
        super("workouts");
        addCommand(new AbstractSimpleCommand("list", "List all workouts") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                try {
                    Statement statement = Application.INSTANCE.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM completed_workout");
                    while (resultSet.next()) {
                        System.out.println(
                                resultSet.getInt("id") + " " +
                                resultSet.getTimestamp("time") + " " +
                                resultSet.getInt("duration") + " " +
                                resultSet.getInt("performance_rating") + " " +
                                resultSet.getString("notes")
                        );
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        addCommand(new EnterContextCommand("add", "Add a workout") {
            @Override
            protected Context createContext() throws CommandException {
                return new AddWorkoutContext();
            }
        });
        addCommand(new Command("view", "view <id>") {
            @Override
            public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
                if (parameters.length == 1) {
                    int id;
                    try {
                        id = Integer.parseInt(parameters[0]);
                    } catch (NumberFormatException e) {
                        throw new CommandException("Not an integer");
                    }
                    try {
                        PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(
                                "SELECT id FROM completed_workout WHERE id = ?"
                        );
                        statement.setInt(1, id);
                        ResultSet rs = statement.executeQuery();
                        if (rs.next()) {
                            stack.push(new ViewWorkoutContext(id));
                        } else {
                            throw new CommandException("No workout with id " + id);
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    throw new UnexpectedParametersException();
                }
            }

            @Override
            public String getDescription() {
                return "View and edit a workout";
            }
        });
    }
}
