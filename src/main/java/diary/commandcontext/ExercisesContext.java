package diary.commandcontext;

import diary.Application;
import diary.commandcontext.command.*;

import java.sql.PreparedStatement;
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
        addCommand(new Command("view", "view <name>") {
            @Override
            public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
                if (parameters.length == 1) {
                    String name = parameters[0];
                    try {
                        PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(
                                "SELECT name FROM exercise WHERE name = ?"
                        );
                        statement.setString(1, name);
                        ResultSet rs = statement.executeQuery();
                        if (rs.next()) {
                            stack.push(new ViewExerciseContext(name));
                        } else {
                            throw new CommandException("No exercise with name " + name);
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
                return "View and edit a task";
            }
        });
    }
}
