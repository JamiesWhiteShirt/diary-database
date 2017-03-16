package diary.commandcontext.command;

import diary.commandcontext.Context;

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
