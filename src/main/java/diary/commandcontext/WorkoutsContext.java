package diary.commandcontext;

import diary.Application;
import diary.commandcontext.command.AbstractSimpleCommand;
import diary.commandcontext.command.CommandException;
import diary.commandcontext.command.EnterContextCommand;

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
                                resultSet.getDate("time") + " " +
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
        addCommand(new EnterContextCommand("create", "Create a workout") {
            @Override
            protected Context createContext() throws CommandException {
                return new CreateWorkoutContext();
            }
        });
    }
}
