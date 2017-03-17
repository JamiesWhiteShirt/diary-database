package diary.contexts;

import diary.lib.context.Context;
import diary.tables.WorkoutTable;
import diary.lib.context.command.AbstractSimpleCommand;
import diary.lib.context.command.CommandException;
import diary.lib.database.DatabaseException;
import diary.lib.database.Table;
import diary.lib.property.database.ColumnProperty;
import diary.lib.property.wrapper.TimestampWrapperProperty;
import diary.lib.property.wrapper.IntegerWrapperProperty;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class AddWorkoutContext extends Context {
    public AddWorkoutContext(WorkoutTable table) {
        super("add");

        Table.Column<Timestamp>.Property time = table.getTime().property();
        Table.Column<Integer>.Property duration = table.getDuration().property();
        Table.Column<Integer>.Property performanceRating = table.getPerformanceRating().property();
        Table.Column<String>.Property notes = table.getNotes().property();

        addProperty(new TimestampWrapperProperty(new ColumnProperty<Timestamp>("time", time)));
        addProperty(new IntegerWrapperProperty(new ColumnProperty<Integer>("duration", duration)));
        addProperty(new IntegerWrapperProperty(new ColumnProperty<Integer>("performanceRating", performanceRating)));
        addProperty(new ColumnProperty<String>("notes", notes));
        addCommand(new AbstractSimpleCommand("commit", "Commit the workout to database") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                List<Table.Column.Property> properties = Arrays.asList(time, duration, performanceRating, notes);
                Table.Column<Integer>.Property key = table.getId().property();
                try {
                    Table.ResultSet resultSet = table.insert(properties, Collections.singletonList(key));
                    if (resultSet.next()) {
                        stack.pop();
                        stack.push(new ViewWorkoutContext(table, key.getValue()));
                    }
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
