package diary.commandcontext;

import diary.commandcontext.command.*;
import diary.property.AbstractProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class Context {
    private final String name;
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, AbstractProperty<String>> properties = new HashMap<>();

    public Context(String name) {
        this.name = name;
        addCommand(new HelpCommand(commands));
        addCommand(new CloseCommand());
        addCommand(new ExitCommand());
        addCommand(new SetPropertyCommand(properties));
        addCommand(new GetPropertyCommand(properties));
    }

    public final String getName() {
        return name;
    }

    protected final void addCommand(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    protected final void addProperty(AbstractProperty<String> property) {
        properties.put(property.getName().toLowerCase(), property);
    }

    public final void read(Stack<Context> stack, String commandString) {
        String[] splitCommandString = commandString.split(" ");
        if (splitCommandString.length >= 1) {
            String commandName = splitCommandString[0];
            Command command = commands.get(commandName.toLowerCase());
            if (command != null) {
                String[] parameters = new String[splitCommandString.length - 1];
                System.arraycopy(splitCommandString, 1, parameters, 0, parameters.length);
                try {
                    command.execute(stack, parameters);
                } catch (CommandException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Unknown command. Type \"help\" for help");
            }
        }
    }
}
