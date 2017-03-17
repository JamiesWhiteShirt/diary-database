package diary.lib.property.database;

import diary.lib.database.DatabaseException;
import diary.lib.database.Table;
import diary.lib.property.AbstractProperty;
import diary.lib.property.PropertyValueException;

import java.util.List;

public class BoundColumnProperty<T> extends AbstractProperty<T> {
    private final Table.Column<T> column;
    private final List<Table.Column.Property> where;

    public BoundColumnProperty(String name, Table.Column<T> column, List<Table.Column.Property> where) {
        super(column.getDataType().getType(), name);
        this.column = column;
        this.where = where;
    }

    @Override
    public void setValue(T value) throws PropertyValueException {
        try {
            column.set(where, value);
        } catch (DatabaseException e) {
            throw new PropertyValueException(e.getMessage());
        }
    }

    @Override
    public T getValue() {
        try {
            return column.get(where);
        } catch (DatabaseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
