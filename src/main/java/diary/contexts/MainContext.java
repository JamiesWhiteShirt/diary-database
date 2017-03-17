package diary.contexts;

import diary.contexts.WorkoutsContext;
import diary.lib.context.Context;
import diary.tables.WorkoutTable;
import diary.lib.context.command.EnterContextCommand;

public class MainContext extends Context {
    public MainContext() {
        super("");
        addCommand(new EnterContextCommand("workouts", "Access workouts") {
            @Override
            protected Context createContext() {
                return new WorkoutsContext(new WorkoutTable());
            }
        });
        /*addCommand(new EnterContextCommand("exercises", "Access exercises") {
            @Override
            protected Context createContext() {
                return new ExercisesContext();
            }
        });*/
    }
}
