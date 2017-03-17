package diary.lib.context.command;

import diary.lib.context.Context;

import java.util.Stack;

public abstract class AbstractSimpleCommand extends Command {
    private final String description;

    public AbstractSimpleCommand(String name, String description) {
        super(name);
        this.description = description;
    }

    protected abstract void execute(Stack<Context> stack) throws CommandException;

    @Override
    public final void execute(Stack<Context> stack, String[] parameters) throws CommandException {
        if (parameters.length == 0) {
            execute(stack);
        } else {
            throw new UnexpectedParametersException();
        }
    }

    @Override
    public final String getDescription() {
        return description;
    }
}
