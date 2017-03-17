package diary.lib.property.database;

import diary.lib.database.Table;
import diary.lib.property.AbstractProperty;
import diary.lib.property.PropertyValueException;

public class ColumnProperty<T> extends AbstractProperty<T> {
    private final Table.Column<T>.Property property;

    public ColumnProperty(String name, Table.Column<T>.Property property) {
        super(property.getColumn().getDataType().getType(), name);
        this.property = property;
    }

    @Override
    public void setValue(T value) throws PropertyValueException {
        property.setValue(value);
    }

    @Override
    public T getValue() {
        return property.getValue();
    }
}
