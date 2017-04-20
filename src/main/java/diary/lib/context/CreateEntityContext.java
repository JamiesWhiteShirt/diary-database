package diary.lib.context;

import diary.lib.context.command.AbstractSimpleCommand;
import diary.lib.context.command.CommandException;
import diary.lib.database.TableDatabaseEngine;
import diary.lib.entityclass.EntityClass;
import diary.lib.entityclass.EntityException;
import diary.lib.property.StringMappedProperty;
import diary.lib.property.entity.EntityProperty;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CreateEntityContext<T> extends Context {
    private List<EntityClass<T>.TableBinding.Attribute<?>.Value> attributeValues;

    public CreateEntityContext(TableDatabaseEngine engine, EntityClass<T> entityClass) {
        super("Create " + entityClass.getName());
        this.attributeValues = entityClass.getNonGeneratedAttributeValues();

        attributeValues.forEach(attributeValue -> {
            addProperty(new StringMappedProperty(new EntityProperty(attributeValue), attributeValue.getAttribute().getStringMapper()));
        });

        addCommand(new AbstractSimpleCommand("commit", "Commit the " + entityClass.getName() + " to the database") {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                EntityClass<T>.TableBinding.Attribute<T>.Value keyValue = entityClass.getKeyAttribute().value();
                try {
                    if (entityClass.insert(engine, attributeValues, Collections.singletonList(keyValue)).next()) {
                        T key = keyValue.getValue();
                        stack.pop();
                        stack.push(new ViewEntityContext<T>(engine, entityClass, attributeValues, key));
                    } else {
                        throw new CommandException("Failed to commit");
                    }
                } catch (EntityException e) {
                    throw new CommandException(e.getMessage());
                }
            }
        });
    }
}
