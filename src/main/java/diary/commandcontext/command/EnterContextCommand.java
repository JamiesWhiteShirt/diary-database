package diary.commandcontext.command;

import diary.commandcontext.Context;

import java.util.Stack;

public abstract class EnterContextCommand extends Command {
    public EnterContextCommand(String name, String usage) {
        super(name, usage);
    }

    public EnterContextCommand(String name) {
        super(name);
    }

    protected abstract Context createCommandContext(String[] parameters) throws CommandException;

    @Override
    public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
        stack.push(createCommandContext(parameters));
    }
}
