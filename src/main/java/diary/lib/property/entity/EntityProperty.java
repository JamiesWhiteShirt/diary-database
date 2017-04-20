package diary.lib.property.entity;

import diary.lib.entityclass.EntityClass;
import diary.lib.property.AbstractProperty;
import diary.lib.property.PropertyValueException;

public class EntityProperty<KeyType, Type> extends AbstractProperty<Type> {
    protected final EntityClass<KeyType>.PrimaryTableBinding.Attribute<Type>.Value attributeValue;

    public EntityProperty(EntityClass<KeyType>.PrimaryTableBinding.Attribute<Type>.Value attributeValue) {
        super(attributeValue.getAttribute().getType(), attributeValue.getAttribute().getName());
        this.attributeValue = attributeValue;
    }

    @Override
    public void setValue(Type value) throws PropertyValueException {
        attributeValue.setValue(value);
    }

    @Override
    public Type getValue() {
        return attributeValue.getValue();
    }
}
