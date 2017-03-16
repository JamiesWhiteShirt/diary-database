package diary.commandcontext;

import diary.commandcontext.command.EnterContextCommand;

public class MainContext extends Context {
    public MainContext() {
        super("");
        addCommand(new EnterContextCommand("workouts", "Access workouts") {
            @Override
            protected Context createContext() {
                return new WorkoutsContext();
            }
        });
        addCommand(new EnterContextCommand("exercises", "Access exercises") {
            @Override
            protected Context createContext() {
                return new ExercisesContext();
            }
        });
    }
}
