package diary.commandcontext;

import diary.commandcontext.command.CommandException;
import diary.commandcontext.command.EnterContextCommand;
import diary.commandcontext.command.UnexpectedParametersException;

public class MainContext extends Context {
    public MainContext() {
        super("");
        addCommand(new EnterContextCommand("addWorkout") {
            @Override
            protected Context createCommandContext(String[] parameters) throws CommandException {
                if (parameters.length == 0) {
                    return new AddWorkoutContext();
                } else {
                    throw new UnexpectedParametersException();
                }
            }

            @Override
            public String getDescription() {
                return "Add a workout";
            }
        });
    }
}
