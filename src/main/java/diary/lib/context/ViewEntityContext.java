package diary.lib.context;

import diary.lib.database.TableDatabaseEngine;
import diary.lib.entityclass.EntityClass;
import diary.lib.property.StringMappedProperty;
import diary.lib.property.entity.BoundEntityProperty;

import java.util.List;

public class ViewEntityContext<T> extends Context {
    public ViewEntityContext(TableDatabaseEngine engine, EntityClass<T> entityClass, List<EntityClass<T>.TableBinding.Attribute<?>.Value> attributeValues, T key) {
        super("View " + entityClass.getName() + " (" + key + ")");

        attributeValues.forEach(attributeValue -> {
            addProperty(new StringMappedProperty(new BoundEntityProperty(entityClass, attributeValue, engine, key), attributeValue.getAttribute().getStringMapper()));
        });
    }
}
