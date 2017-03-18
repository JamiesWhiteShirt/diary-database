package diary.lib.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Table<TableType extends Table> {
    public final class Column<Type> {
        public final class Property {
            private Type value;

            private Property(Type value) {
                this.value = value;
            }

            public Column<Type> getColumn() {
                return Column.this;
            }

            public Type getValue() {
                return value;
            }

            public void setValue(Type value) {
                this.value = value;
            }

            private void assertNullability() throws DatabaseException {
                try {
                    nullability.assertValue(value);
                } catch (NullabilityException e) {
                    throw new DatabaseException(getName() + " " + e.getMessage());
                }
            }

            void fetch(java.sql.ResultSet rs, int columnIndex) throws SQLException, DatabaseException {
                setValue(getDataType().fetch(rs, columnIndex));
                assertNullability();
            }

            void prepare(PreparedStatement statement, int parameterIndex) throws SQLException, DatabaseException {
                assertNullability();
                getDataType().prepare(statement, parameterIndex, getValue());
            }
        }

        private final String name;
        private final DataType<Type> dataType;
        private Nullability nullability;

        public Column(String name, DataType<Type> dataType, Nullability nullability) {
            this.name = name;
            this.dataType = dataType;
            this.nullability = nullability;
        }

        public String getName() {
            return name;
        }

        public DataType<Type> getDataType() {
            return dataType;
        }

        public Property property(Type value) {
            return new Property(value);
        }

        public Property property() {
            return property(null);
        }

        public String getEqualsCondition() {
            return getName() + " = ?";
        }
    }

    private final String name;
    private final List<Column> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    protected void addColumn(Column<?> column) {
        columns.add(column);
    }

    public final String getName() {
        return name;
    }

    public final List<Column> getColumns() {
        return columns;
    }

}
