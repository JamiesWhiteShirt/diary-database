package diary.commandcontext;

import diary.Application;
import diary.commandcontext.command.AbstractSimpleCommand;
import diary.commandcontext.command.CommandException;
import diary.commandcontext.command.EnterContextCommand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

public class ExercisesContext extends Context {
    public ExercisesContext() {
        super("exercises");
        addCommand(new AbstractSimpleCommand("list", "List all exercises") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                try {
                    Statement statement = Application.INSTANCE.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM exercise");
                    while (resultSet.next()) {
                        System.out.println(
                                resultSet.getString("name") + " " +
                                resultSet.getString("description") + " " +
                                resultSet.getString("type")
                        );
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        addCommand(new EnterContextCommand("create", "Create an exercise") {
            @Override
            protected Context createContext() throws CommandException {
                return new CreateExerciseContext();
            }
        });
    }
}
