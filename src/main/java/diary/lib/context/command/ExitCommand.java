package diary.lib.context.command;

import diary.lib.context.Context;

import java.util.Stack;

public final class ExitCommand extends AbstractSimpleCommand {
    public ExitCommand() {
        super("exit", "Exit the program");
    }

    @Override
    protected void execute(Stack<Context> stack) throws CommandException {
        stack.clear();
    }
}
