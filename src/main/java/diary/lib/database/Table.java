package diary.lib.database;

import diary.Application;
import diary.Nullability;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Table {
    public final class Column<T> {
        public final class Property {
            private T value;

            private Property(T value) {
                this.value = value;
            }

            public Column<T> getColumn() {
                return Column.this;
            }

            public T getValue() {
                return value;
            }

            public void setValue(T value) {
                this.value = value;
            }

            private void assertNullability() {
                nullability.assertValue(value);
            }

            private void fetch(java.sql.ResultSet rs, int columnIndex) throws SQLException {
                setValue(getDataType().fetch(rs, columnIndex));
                assertNullability();
            }

            private void prepare(PreparedStatement statement, int parameterIndex) throws SQLException {
                assertNullability();
                getDataType().prepare(statement, parameterIndex, getValue());
            }
        }

        private final String name;
        private final DataType<T> dataType;
        private Nullability nullability;

        public Column(String name, DataType<T> dataType, Nullability nullability) {
            this.name = name;
            this.dataType = dataType;
            this.nullability = nullability;
        }

        public String getName() {
            return name;
        }

        public DataType<T> getDataType() {
            return dataType;
        }

        public Property property(T value) {
            return new Property(value);
        }

        public Property property() {
            return property(null);
        }

        public T get(List<Column.Property> where) throws DatabaseException {
            Property property = property();
            if (select(Collections.singletonList(property), where).next()) {
                return property.getValue();
            } else {
                throw new DatabaseException("No column value found");
            }
        }

        public void set(List<Column.Property> where, T value) throws DatabaseException {
            Property property = property(value);
            if (update(Collections.singletonList(property), where) <= 0) {
                throw new DatabaseException("No column value found");
            }
        }
    }

    public final class ResultSet {
        private final java.sql.ResultSet rs;
        private final List<Column.Property> get;

        private ResultSet(java.sql.ResultSet rs, List<Column.Property> get) {
            this.rs = rs;
            this.get = get;
        }

        public boolean next() throws DatabaseException {
            try {
                if (rs.next()) {
                    int columnIndex = 1;
                    for (Column.Property property : get) {
                        property.fetch(rs, columnIndex++);
                    }
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
    }

    private final String name;
    private final List<Column> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    protected void addColumn(Column column) {
        columns.add(column);
    }

    public final String getName() {
        return name;
    }

    public final List<Column> getColumns() {
        return columns;
    }

    public final ResultSet select(List<Column.Property> get) throws DatabaseException {
        String selectString = get.stream().map(property -> property.getColumn().getName()).collect(Collectors.joining(", "));
        try {
            String statementString =
                    "SELECT " + selectString + " " +
                    "FROM " + getName();
            System.out.println(statementString);
            PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(statementString);
            return new ResultSet(statement.executeQuery(), get);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public final ResultSet select(List<Column.Property> get, List<Column.Property> where) throws DatabaseException {
        String selectString = get.stream().map(property -> property.getColumn().getName()).collect(Collectors.joining(", "));
        String whereString = where.stream().map(columnValue -> columnValue.getColumn().getName() + " = ?").collect(Collectors.joining(" AND "));
        try {
            String statementString =
                    "SELECT " + selectString + " " +
                    "FROM " + getName() + " " +
                    "WHERE " + whereString;
            System.out.println(statementString);
            PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(statementString);
            int parameterIndex = 1;
            for (Column.Property property : where) {
                property.prepare(statement, parameterIndex++);
            }
            return new ResultSet(statement.executeQuery(), get);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public final int update(List<Column.Property> set, List<Column.Property> where) throws DatabaseException {
        String setString = set.stream().map(property -> property.getColumn().getName() + " = ?").collect(Collectors.joining(", "));
        String whereString = where.stream().map(property -> property.getColumn().getName() + " = ?").collect(Collectors.joining(" AND "));
        try {
            String statementString =
                    "UPDATE " + getName() + " SET " + setString + " " +
                    "WHERE " + whereString;
            System.out.println(statementString);
            PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(statementString);
            int parameterIndex = 1;
            for (Column.Property property : set) {
                property.prepare(statement, parameterIndex++);
            }
            for (Column.Property property : where) {
                property.prepare(statement, parameterIndex++);
            }
            statement.execute();
            int result = statement.getUpdateCount();
            statement.close();
            return result;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public final ResultSet insert(List<Column.Property> values, List<Column.Property> generated) throws DatabaseException {
        String columnsString = values.stream().map(property -> property.getColumn().getName()).collect(Collectors.joining(", "));
        String valuesString = values.stream().map(property -> "?").collect(Collectors.joining(", "));
        try {
            String statementString =
                    "INSERT INTO " + getName() + " " +
                    "(" + columnsString + ") " +
                    "VALUES (" + valuesString + ")";
            System.out.println(statementString);
            PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(statementString);
            int parameterIndex = 1;
            for (Column.Property property : values) {
                property.prepare(statement, parameterIndex++);
            }
            statement.executeUpdate();
            return new ResultSet(statement.getGeneratedKeys(), generated);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
