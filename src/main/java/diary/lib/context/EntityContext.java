package diary.lib.context;

import diary.lib.context.command.AbstractSimpleCommand;
import diary.lib.context.command.CommandException;
import diary.lib.database.TableDatabaseEngine;
import diary.lib.entityclass.EntityClass;
import diary.lib.entityclass.EntityException;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class EntityContext<T> extends Context {
    public EntityContext(String name, TableDatabaseEngine engine, EntityClass<T> entityClass) {
        super(name);
        addCommand(new AbstractSimpleCommand("list", "List all " + name) {
            @Override
            protected void execute(Stack<Context> stack) throws CommandException {
                try {
                    EntityClass<T>.PrimaryTableBinding<?, T> primaryTableBinding = entityClass.getPrimaryTableBinding();
                    List<EntityClass.TableBinding.Attribute> attributes = primaryTableBinding.getAttributes();
                    List<EntityClass.TableBinding.Attribute.Property> properties = attributes.stream().map(EntityClass.TableBinding.Attribute::property).collect(Collectors.toList());
                    EntityClass.ResultSet resultSet = primaryTableBinding.select(engine, properties);
                    while (resultSet.next()) {
                        System.out.println(properties.stream().map(EntityClass.TableBinding.Attribute.Property::getStringValue).map(it -> it != null ? it : "null").collect(Collectors.joining(" ")));
                    }
                } catch (EntityException e) {
                    throw new CommandException(e.getMessage());
                }
            }
        });
    }
}
