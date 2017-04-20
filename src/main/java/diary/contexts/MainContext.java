package diary.contexts;

import diary.Application;
//import diary.contexts.WorkoutsContext;
import diary.entityclasses.WorkoutEntityClass;
import diary.lib.context.Context;
import diary.lib.context.EntityContext;
import diary.lib.context.command.CommandException;
import diary.lib.database.TableDatabaseEngine;
import diary.tables.WorkoutTable;
import diary.lib.context.command.EnterContextCommand;

public class MainContext extends Context {
    public MainContext() {
        super("");
        addCommand(new EnterContextCommand("workouts", "workouts") {
            @Override
            protected Context createContext() throws CommandException {
                return new EntityContext<>(new TableDatabaseEngine(Application.INSTANCE.getConnection()), new WorkoutEntityClass(new WorkoutTable()));
            }
        });
        /*addCommand(new EnterContextCommand("workouts", "Access workouts") {
            @Override
            protected Context createContext() {
                return new WorkoutsContext(new WorkoutTable());
            }
        });*/
        /*addCommand(new EnterContextCommand("exercises", "Access exercises") {
            @Override
            protected Context createContext() {
                return new ExercisesContext();
            }
        });*/
    }
}
