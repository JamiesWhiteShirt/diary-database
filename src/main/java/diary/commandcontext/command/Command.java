package diary.commandcontext.command;

import diary.commandcontext.Context;

import java.util.Stack;

public abstract class Command {
    private final String name;
    private final String usage;

    public Command(String name, String usage) {
        this.name = name;
        this.usage = usage;
    }

    public Command(String name) {
        this(name, name);
    }

    public final String getName() {
        return name;
    }

    public final String getUsage() {
        return usage;
    }

    public abstract void execute(Stack<Context> stack, String[] parameters) throws CommandException;

    public abstract String getDescription();

    @Override
    public String toString() {
        return getUsage() + " : " + getDescription();
    }
}
