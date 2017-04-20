package diary.lib.context;

import diary.lib.context.command.*;
import diary.lib.database.TableDatabaseEngine;
import diary.lib.entityclass.EntityClass;
import diary.lib.entityclass.EntityException;
import diary.lib.property.StringMapperException;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class EntityContext<T> extends Context {
    public EntityContext(TableDatabaseEngine engine, EntityClass<T> entityClass) {
        super(entityClass.getName() + " overview");
        addCommand(new AbstractSimpleCommand("list", "List all instances of " + entityClass.getName()) {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                try {
                    List<EntityClass<T>.TableBinding.Attribute<?>.Value> attributeValues = entityClass.getAttributeValues();
                    EntityClass.ResultSet resultSet = entityClass.select(engine, attributeValues);
                    while (resultSet.next()) {
                        System.out.println(attributeValues.stream().map(EntityClass.TableBinding.Attribute.Value::getStringValue).map(it -> it != null ? it : "null").collect(Collectors.joining(" ")));
                    }
                } catch (EntityException e) {
                    throw new CommandException(e.getMessage());
                }
            }
        });
        addCommand(new Command("view", "view <id>") {
            @Override
            public void execute(Stack<Context> stack, String[] parameters) throws CommandException {
                if (parameters.length == 1) {
                    EntityClass<T>.TableBinding.Attribute<T> keyAttribute = entityClass.getKeyAttribute();
                    T key;
                    try {
                        key = keyAttribute.getStringMapper().fromString(parameters[0]);
                    } catch (StringMapperException e) {
                        throw new CommandException(e.getMessage());
                    }
                    List<EntityClass<T>.TableBinding.Attribute<?>.Value> attributeValues = entityClass.getNonGeneratedAttributeValues();
                    try {
                        if (entityClass.select(engine, attributeValues, key).next()) {
                            stack.push(new ViewEntityContext<T>(engine, entityClass, attributeValues, key));
                        } else {
                            throw new CommandException("Invalid id");
                        }
                    } catch (EntityException e) {
                        throw new CommandException(e.getMessage());
                    }
                } else {
                    throw new UnexpectedParametersException();
                }
            }

            @Override
            public String getDescription() {
                return "View and edit an instance of " + entityClass.getName();
            }
        });
        addCommand(new EnterContextCommand("create", "Create a new " + entityClass.getName()) {
            @Override
            protected Context createContext() throws CommandException {
                return new CreateEntityContext<>(engine, entityClass);
            }
        });
    }
}
