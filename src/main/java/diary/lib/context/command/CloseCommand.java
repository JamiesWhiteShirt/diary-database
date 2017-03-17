package diary.lib.context.command;

import diary.lib.context.Context;

import java.util.Stack;

public final class CloseCommand extends AbstractSimpleCommand {
    public CloseCommand() {
        super("close", "Close the current command context");
    }

    @Override
    protected void execute(Stack<Context> stack) throws CommandException {
        stack.pop();
    }
}
