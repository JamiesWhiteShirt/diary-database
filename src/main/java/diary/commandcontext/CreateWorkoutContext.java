package diary.commandcontext;

import diary.Application;
import diary.commandcontext.command.AbstractSimpleCommand;
import diary.commandcontext.command.CommandException;
import diary.property.DateProperty;
import diary.property.IntegerProperty;
import diary.property.Property;
import diary.property.StringProperty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Stack;

public class CreateWorkoutContext extends Context {
    private final DateProperty date = new DateProperty("time", null);
    private final IntegerProperty duration = new IntegerProperty("duration", null);
    private final IntegerProperty performanceRating = new IntegerProperty("performanceRating", null);
    private final StringProperty notes = new StringProperty("notes", null);

    public CreateWorkoutContext() {
        super("create");
        addProperty(date);
        addProperty(duration);
        addProperty(performanceRating);
        addProperty(notes);
        addCommand(new AbstractSimpleCommand("commit", "Commit the workout to database") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                Date dateValue = assertValueNotNull(date);
                int durationValue = assertValueNotNull(duration);
                Integer performanceRatingValue = performanceRating.getValue();
                String notesValue = notes.getValue();
                try {
                    PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(
                            "INSERT INTO completed_workout " +
                            "(time, duration, performance_rating, notes) " +
                            "VALUES (?, ?, ?, ?)"
                    );
                    statement.setDate(1, new java.sql.Date(dateValue.getTime()));
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

                    System.out.println("Created new workout with id " + rs.getInt(1));
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
