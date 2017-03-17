package diary.lib.context.command;

import diary.lib.context.Context;

import java.util.Map;
import java.util.Stack;

public class HelpCommand extends Command {
    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        super("help", "help [command]");
        this.commands = commands;
    }

    @Override
    public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
        if (parameters.length == 1) {
            String commandName = parameters[0];
            Command command = commands.get(commandName);
            if (command != null) {
                System.out.println(command);
            } else {
                System.out.println(this);
            }
        } else if (parameters.length == 0) {
            commands.values().forEach(System.out::println);
        }
    }

    @Override
    public String getDescription() {
        return "Get help for commands";
    }
}
