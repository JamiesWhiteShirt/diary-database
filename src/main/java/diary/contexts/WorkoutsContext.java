package diary.contexts;

/*import diary.lib.context.Context;
import diary.tables.WorkoutTable;
import diary.lib.context.command.*;
import diary.lib.database.DatabaseException;
import diary.lib.database.Table;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class WorkoutsContext extends Context {
    public WorkoutsContext(WorkoutTable table) {
        super("workouts");
        addCommand(new AbstractSimpleCommand("list", "List all workouts") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                Table.Column<Integer>.Property id = table.id.property();
                Table.Column<Timestamp>.Property time = table.time.property();
                Table.Column<Integer>.Property duration = table.duration.property();
                Table.Column<Integer>.Property performanceRating = table.performanceRating.property();
                Table.Column<String>.Property notes = table.notes.property();

                List<Table.Column.Property> get = Arrays.asList(id, time, duration, performanceRating, notes);

                try {
                    Table.ResultSet resultSet = table.select(get, Collections.emptyList());
                    while (resultSet.next()) {
                        System.out.println(get.stream().map(Table.Column.Property::getValue).map(it -> it != null ? it.toString() : "null").collect(Collectors.joining(" ")));
                    }
                } catch (DatabaseException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        addCommand(new EnterContextCommand("add", "Add a workout") {
            @Override
            protected Context createContext() throws CommandException {
                return new AddWorkoutContext(table);
            }
        });
        addCommand(new Command("view", "view <id>") {
            @Override
            public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
                if (parameters.length == 1) {
                    Table.Column<Integer>.Property id;
                    try {
                        id = table.id.property(Integer.parseInt(parameters[0]));
                    } catch (NumberFormatException e) {
                        throw new CommandException("Not a valid id");
                    }
                    try {
                        if (table.select(Collections.singletonList(id), Collections.singletonList(id)).next()) {
                            stack.push(new ViewWorkoutContext(table, id.getValue()));
                        } else {
                            throw new CommandException("No workout with id " + id.getValue());
                        }
                    } catch (DatabaseException e) {
                        throw new CommandException(e.getMessage());
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
}*/
