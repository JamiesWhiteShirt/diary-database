package diary.lib.property.database;

import diary.lib.database.Table;
import diary.lib.database.TableDatabaseEngine;
import diary.lib.property.AbstractProperty;
import diary.lib.property.PropertyValueException;

import java.util.List;

/*public class BoundColumnProperty<T, U extends Table> extends AbstractProperty<T> {
    private final TableDatabaseEngine engine;
    private final U table;
    private final Table<U>.Column<T> column;
    private final List<Table.Column.Property> where;

    public BoundColumnProperty(String name, TableDatabaseEngine engine, U table, Table<U>.Column<T> column, List<Table.Column.Property> where) {
        super(column.getDataType().getType(), name);
        this.engine = engine;
        this.table = table;
        this.column = column;
        this.where = where;
    }

    @Override
    public void setValue(T value) throws PropertyValueException {
    }

    @Override
    public T getValue() {
    }
}*/
