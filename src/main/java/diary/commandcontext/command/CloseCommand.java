package diary.commandcontext.command;

import diary.commandcontext.Context;

import java.util.Stack;

public final class CloseCommand extends Command {
    public CloseCommand() {
        super("close");
    }

    @Override
    public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
        stack.pop();
    }

    @Override
    public String getDescription() {
        return "Close the current command context";
    }
}
