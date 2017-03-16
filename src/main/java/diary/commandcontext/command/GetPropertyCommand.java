package diary.commandcontext.command;

import diary.property.AbstractProperty;
import diary.commandcontext.Context;

import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class GetPropertyCommand extends Command {
    protected Map<String, AbstractProperty<String>> properties;

    public GetPropertyCommand(Map<String, AbstractProperty<String>> properties) {
        super("get", "get [property]");
        this.properties = properties;
    }

    @Override
    public void execute(Stack<Context> context, String[] parameters) throws CommandException {
        if (parameters.length == 1) {
            String propertyName = parameters[0];
            AbstractProperty<String> property = properties.get(propertyName.toLowerCase());
            if (property != null) {
                System.out.println(property);
            } else {
                throw new CommandException("Invalid property " + propertyName);
            }
        } else if (parameters.length == 0) {
            properties.values().forEach(System.out::println);
        } else {
            throw new UnexpectedParametersException();
        }
    }

    @Override
    public String getDescription() {
        return "Get a property\n" + properties.values().stream().map(AbstractProperty::getDescription).collect(Collectors.joining(", "));
    }
}
