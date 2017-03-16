package diary.commandcontext.command;

import diary.commandcontext.Context;

import java.util.Stack;

public final class ExitCommand extends Command {
    public ExitCommand() {
        super("exit");
    }

    @Override
    public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
        stack.clear();
    }

    @Override
    public String getDescription() {
        return "Exit the program";
    }
}
