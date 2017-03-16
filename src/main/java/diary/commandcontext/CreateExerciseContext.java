package diary.commandcontext;

import diary.Application;
import diary.ExerciseType;
import diary.commandcontext.command.AbstractSimpleCommand;
import diary.commandcontext.command.CommandException;
import diary.property.EnumProperty;
import diary.property.Property;
import diary.property.StringProperty;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Stack;

public class CreateExerciseContext extends Context {
    private final StringProperty name = new StringProperty("name", null);
    private final StringProperty description = new StringProperty("description", null);
    private final EnumProperty<ExerciseType> type = new EnumProperty<>(ExerciseType.class, "type", null);

    public CreateExerciseContext() {
        super("create");
        addProperty(name);
        addProperty(description);
        addProperty(type);
        addCommand(new AbstractSimpleCommand("commit", "Commit the exercise to database") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                String nameValue = assertValueNotNull(name);
                String descriptionValue = assertValueNotNull(description);
                ExerciseType typeValue = assertValueNotNull(type);
                try {
                    PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(
                            "INSERT INTO exercise " +
                            "(name, description, type) " +
                            "VALUES (?, ?, ?)"
                    );
                    statement.setString(1, nameValue);
                    statement.setString(2, descriptionValue);
                    statement.setString(3, typeValue.name());

                    statement.executeUpdate();

                    System.out.println("Created new exercise with name " + nameValue);
                    stack.pop();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            private <T> T assertValueNotNull(Property<T> property) throws CommandException {
                T value = property.getValue();
                if (value == null) {
                    throw new CommandException(property.getName() + " cannot be null");
                }
                return value;
            }
        });
    }
}
