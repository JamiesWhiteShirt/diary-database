package diary.contexts;

import diary.Application;
import diary.ExerciseType;
import diary.lib.context.Context;
import diary.lib.context.command.AbstractSimpleCommand;
import diary.lib.context.command.CommandException;
import diary.lib.property.AbstractProperty;
import diary.lib.property.ValueProperty;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Stack;

/*public class CreateExerciseContext extends Context {
    private final AbstractProperty<String> name = new ValueProperty<>(String.class, "name", null);
    private final AbstractProperty<String> description = new ValueProperty<>(String.class, "description", null);
    private final AbstractProperty<ExerciseType> type = new ValueProperty<>(ExerciseType.class, "type", null);

    public CreateExerciseContext() {
        super("create");
        addProperty(name);
        addProperty(description);
        addProperty(new EnumWrapperProperty<>(type));
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

            private <T> T assertValueNotNull(AbstractProperty<T> property) throws CommandException {
                T value = property.getValue();
                if (value == null) {
                    throw new CommandException(property.getName() + " cannot be null");
                }
                return value;
            }
        });
    }
}*/
