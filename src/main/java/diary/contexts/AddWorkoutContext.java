package diary.contexts;

/*import diary.lib.StringMapper;
import diary.lib.context.Context;
import diary.lib.property.StringMappedProperty;
import diary.tables.WorkoutTable;
import diary.lib.context.command.AbstractSimpleCommand;
import diary.lib.context.command.CommandException;
import diary.lib.database.DatabaseException;
import diary.lib.database.Table;
import diary.lib.property.database.ColumnProperty;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class AddWorkoutContext extends Context {
    public AddWorkoutContext(WorkoutTable table) {
        super("add");

        Table.Column<Timestamp>.Property time = table.time.property();
        Table.Column<Integer>.Property duration = table.duration.property();
        Table.Column<Integer>.Property performanceRating = table.performanceRating.property();
        Table.Column<String>.Property notes = table.notes.property();

        addProperty(new StringMappedProperty<>(new ColumnProperty<Timestamp>("time", time), StringMapper.TIMESTAMP));
        addProperty(new StringMappedProperty<>(new ColumnProperty<Integer>("duration", duration), StringMapper.INTEGER));
        addProperty(new StringMappedProperty<>(new ColumnProperty<Integer>("performanceRating", performanceRating), StringMapper.INTEGER));
        addProperty(new ColumnProperty<String>("notes", notes));
        addCommand(new AbstractSimpleCommand("commit", "Commit the workout to database") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                List<Table.Column.Property> properties = Arrays.asList(time, duration, performanceRating, notes);
                Table.Column<Integer>.Property key = table.id.property();
                try {
                    Table.ResultSet resultSet = table.insert(properties, Collections.singletonList(key));
                    if (resultSet.next()) {
                        stack.pop();
                        stack.push(new ViewWorkoutContext(table, key.getValue()));
                    }
                } catch (DatabaseException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }
}*/
