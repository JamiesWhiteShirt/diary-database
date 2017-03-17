package diary.lib.context.command;

import diary.lib.context.Context;
import diary.lib.property.AbstractProperty;
import diary.lib.property.PropertyValueException;

import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class SetPropertyCommand extends Command {
    protected Map<String, AbstractProperty<String>> properties;

    public SetPropertyCommand(Map<String, AbstractProperty<String>> properties) {
        super("set", "set <property> <value>...");
        this.properties = properties;
    }

    @Override
    public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
        if (parameters.length >= 2) {
            String propertyName = parameters[0];
            AbstractProperty<String> property = properties.get(propertyName.toLowerCase());
            if (property != null) {
                String[] valueStrings = new String[parameters.length - 1];
                System.arraycopy(parameters, 1, valueStrings, 0, valueStrings.length);
                String valueString = String.join(" ", (CharSequence[])valueStrings);

                try {
                    if (!"null".equalsIgnoreCase(valueString)) {
                        property.setValue(valueString);
                    } else {
                        property.setValue(null);
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
        return "Set a property\n" + properties.values().stream().map(AbstractProperty::getDescription).collect(Collectors.joining(", "));
    }
}
