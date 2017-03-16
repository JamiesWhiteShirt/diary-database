package diary.commandcontext;

import diary.Application;
import diary.commandcontext.command.AbstractSimpleCommand;
import diary.commandcontext.command.CommandException;
import diary.property.ValueProperty;
import diary.property.wrapper.TimestampWrapperProperty;
import diary.property.wrapper.IntegerWrapperProperty;
import diary.property.AbstractProperty;

import java.sql.*;
import java.util.Date;
import java.util.Stack;

public class AddWorkoutContext extends Context {
    private final AbstractProperty<Timestamp> time = new ValueProperty<>(Timestamp.class, "time", null);
    private final AbstractProperty<Integer> duration = new ValueProperty<>(Integer.class, "duration", null);
    private final AbstractProperty<Integer> performanceRating = new ValueProperty<>(Integer.class, "performanceRating", null);
    private final AbstractProperty<String> notes = new ValueProperty<>(String.class, "notes", null);

    public AddWorkoutContext() {
        super("add");
        addProperty(new TimestampWrapperProperty(time));
        addProperty(new IntegerWrapperProperty(duration));
        addProperty(new IntegerWrapperProperty(performanceRating));
        addProperty(notes);
        addCommand(new AbstractSimpleCommand("commit", "Commit the workout to database") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                Date timeValue = assertValueNotNull(time);
                int durationValue = assertValueNotNull(duration);
                Integer performanceRatingValue = performanceRating.getValue();
                String notesValue = notes.getValue();
                try {
                    PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(
                            "INSERT INTO completed_workout " +
                            "(time, duration, performance_rating, notes) " +
                            "VALUES (?, ?, ?, ?)"
                    );
                    statement.setDate(1, new java.sql.Date(timeValue.getTime()));
                    statement.setInt(2, durationValue);
                    if (performanceRatingValue != null) {
                        statement.setInt(3, performanceRatingValue);
                    } else {
                        statement.setNull(3, Types.INTEGER);
                    }
                    if (notesValue != null) {
                        statement.setString(4, notesValue);
                    } else {
                        statement.setNull(4, Types.VARCHAR);
                    }

                    statement.executeUpdate();
                    ResultSet rs = statement.getGeneratedKeys();
                    rs.next();
                    int id = rs.getInt(1);

                    System.out.println("Created new workout with id " + id);
                    stack.pop();
                    stack.push(new ViewWorkoutContext(id));
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
}
