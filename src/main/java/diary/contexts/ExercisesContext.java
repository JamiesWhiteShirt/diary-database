package diary.contexts;

import diary.Application;
import diary.lib.context.Context;
import diary.lib.context.command.*;
import diary.lib.database.DatabaseException;
import diary.lib.database.Table;
import diary.tables.ExerciseTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ExercisesContext extends Context {
    public ExercisesContext(ExerciseTable table) {
        super("exercises");
        /*addCommand(new AbstractSimpleCommand("list", "List all exercises") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                Table.Column<String>.Property name = table.name.property();
                Table.Column<String>.Property description = table.description.property();
                Table.Column<String>.Property type = table.type.property();

                List<Table.Column.Property> get = Arrays.asList(name, description, type);

                try {
                    Table.ResultSet resultSet = table.select(get, Collections.emptyList());
                    while (resultSet.next()) {
                        System.out.println(get.stream().map(Table.Column.Property::getValue).map(it -> it != null ? it.toString() : "null").collect(Collectors.joining(" ")));
                    }
                } catch (DatabaseException e) {
                    System.out.println(e.getMessage());
                }
            }
        });*/
        /*addCommand(new EnterContextCommand("create", "Create an exercise") {
            @Override
            protected Context createContext() throws CommandException {
                return new CreateExerciseContext(table);
            }
        });
        addCommand(new Command("view", "view <name>") {
            @Override
            public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
                if (parameters.length == 1) {
                } else {
                    throw new UnexpectedParametersException();
                }
            }

            @Override
            public String getDescription() {
                return "View and edit an exercise";
            }
        });*/
    }
}
