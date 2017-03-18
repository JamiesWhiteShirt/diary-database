package diary.lib.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class TableDatabaseEngine {
    public final class ResultSet {
        private final java.sql.ResultSet resultSet;
        private final List<Table.Column.Property> get;

        private ResultSet(java.sql.ResultSet resultSet, List<Table.Column.Property> get) {
            this.resultSet = resultSet;
            this.get = get;
        }

        public boolean next() throws DatabaseException {
            try {
                if (resultSet.next()) {
                    int columnIndex = 1;
                    for (Table.Column.Property property : get) {
                        property.fetch(resultSet, columnIndex++);
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

    private final Connection connection;

    public TableDatabaseEngine(Connection connection) {
        this.connection = connection;
    }

    private static String fullColumnNamesString(Table table, List<Table.Column.Property> properties) throws DatabaseException {
        if (properties.size() > 0) {
            return properties.stream().map(Table.Column.Property::getColumn).map(it -> table.getName() + "." + it.getName()).collect(Collectors.joining(", "));
        } else {
            throw new DatabaseException("Expecting at least one column");
        }
    }

    private static String columnNamesString(List<Table.Column.Property> properties) throws DatabaseException {
        if (properties.size() > 0) {
            return properties.stream().map(Table.Column.Property::getColumn).map(Table.Column::getName).collect(Collectors.joining(", "));
        } else {
            throw new DatabaseException("Expecting at least one column");
        }
    }

    private static String withWhere(String statementString, List<Table.Column.Property> properties) {
        if (properties.size() > 0) {
            return statementString + " WHERE " + properties.stream().map(Table.Column.Property::getColumn).map(Table.Column::getEqualsCondition).collect(Collectors.joining(" AND "));
        } else {
            return statementString;
        }
    }

    public final <T extends Table> ResultSet select(T table, List<Table.Column.Property> get, List<Table.Column.Property> where) throws DatabaseException {
        try {
            String statementString = withWhere("SELECT " + fullColumnNamesString(table, get) + " FROM " + table.getName(), where);
            System.out.println(statementString);
            PreparedStatement statement = connection.prepareStatement(statementString);
            int parameterIndex = 1;
            for (Table.Column.Property property : where) {
                property.prepare(statement, parameterIndex++);
            }
            return new ResultSet(statement.executeQuery(), get);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public final <T extends Table> int update(T table, List<Table.Column.Property> set, List<Table.Column.Property> where) throws DatabaseException {
        try {
            String statementString = withWhere("UPDATE " + table.getName() + " SET " + columnNamesString(set), where);
            System.out.println(statementString);
            PreparedStatement statement = connection.prepareStatement(statementString);
            int parameterIndex = 1;
            for (Table.Column.Property property : set) {
                property.prepare(statement, parameterIndex++);
            }
            for (Table.Column.Property property : where) {
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

    public final <T extends Table> ResultSet insert(T table, List<Table.Column.Property> values, List<Table.Column.Property> generated) throws DatabaseException {
        try {
            String valuesString = values.stream().map(property -> "?").collect(Collectors.joining(", "));
            String statementString = "INSERT INTO " + table.getName() + " " + "(" + columnNamesString(values) + ") VALUES (" + valuesString + ")";
            System.out.println(statementString);
            PreparedStatement statement = connection.prepareStatement(statementString);
            int parameterIndex = 1;
            for (Table.Column.Property property : values) {
                property.prepare(statement, parameterIndex++);
            }
            statement.executeUpdate();
            return new ResultSet(statement.getGeneratedKeys(), generated);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
