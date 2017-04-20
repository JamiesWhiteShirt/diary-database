package diary.lib.property.entity;

import diary.lib.database.TableDatabaseEngine;
import diary.lib.entityclass.EntityClass;
import diary.lib.entityclass.EntityException;
import diary.lib.property.PropertyValueException;

import java.util.Collections;

public class BoundEntityProperty<KeyType, Type, EntityClassType extends EntityClass<KeyType>> extends EntityProperty<KeyType, Type> {
    private final EntityClassType entityClass;
    private final TableDatabaseEngine engine;
    private final KeyType key;

    public BoundEntityProperty(EntityClassType entityClass, EntityClass<KeyType>.PrimaryTableBinding.Attribute<Type>.Value attributeValue, TableDatabaseEngine engine, KeyType key) {
        super(attributeValue);
        this.entityClass = entityClass;
        this.engine = engine;
        this.key = key;
    }

    @Override
    public void setValue(Type value) throws PropertyValueException {
        super.setValue(value);
        try {
            entityClass.update(engine, Collections.singletonList(attributeValue), key);
        } catch (EntityException e) {
            throw new PropertyValueException(e.getMessage());
        }
    }

    /*@Override
    public Type getValue() {
        try {
            entityClass.select(engine, Collections.singletonList(attributeValue), key).next();
            return super.getValue();
        } catch (EntityException e) {
            throw new RuntimeException(e);
        }
    }*/
}
