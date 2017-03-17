package diary.lib.context.command;

import diary.lib.context.Context;

import java.util.Stack;

public abstract class EnterContextCommand extends AbstractSimpleCommand {
    public EnterContextCommand(String name, String description) {
        super(name, description);
    }

    protected abstract Context createContext() throws CommandException;

    @Override
    public void execute(Stack<Context> stack) throws CommandException {
        stack.push(createContext());
    }
}
