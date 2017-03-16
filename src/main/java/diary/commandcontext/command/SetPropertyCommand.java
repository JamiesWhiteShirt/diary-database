package diary.commandcontext.command;

import diary.commandcontext.Context;
import diary.property.Property;
import diary.property.PropertyValueException;

import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class SetPropertyCommand extends Command {
    protected Map<String, Property> properties;

    public SetPropertyCommand(Map<String, Property> properties) {
        super("set", "set <property> <value>...");
        this.properties = properties;
    }

    @Override
    public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
        if (parameters.length >= 2) {
            String propertyName = parameters[0];
            Property property = properties.get(propertyName.toLowerCase());
            if (property != null) {
                String[] valueStrings = new String[parameters.length - 1];
                System.arraycopy(parameters, 1, valueStrings, 0, valueStrings.length);
                String valueString = String.join(" ", (CharSequence[])valueStrings);

                try {
                    if (!"null".equalsIgnoreCase(valueString)) {
                        property.setStringValue(valueString);
                    } else {
                        property.setStringValue(null);
                    }
                } catch (PropertyValueException e) {
                    throw new CommandException(e.getMessage() + " for property " + propertyName);
                }
            } else {
                throw new CommandException("Invalid property " + propertyName);
            }
        } else {
            throw new CommandException("Expected property and value");
        }
    }

    @Override
    public String getDescription() {
        return "Set a property\n" + properties.values().stream().map(Property::getDescription).collect(Collectors.joining(", "));
    }
}
