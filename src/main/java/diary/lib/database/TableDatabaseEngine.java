package diary.lib.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class TableDatabaseEngine {
    public final class ResultSet {
        private final java.sql.ResultSet resultSet;
        private final List<Table.Column<?>.Value> get;

        private ResultSet(java.sql.ResultSet resultSet, List<Table.Column<?>.Value> get) {
            this.resultSet = resultSet;
            this.get = get;
        }

        public boolean next() throws DatabaseException {
            try {
                if (resultSet.next()) {
                    int columnIndex = 1;
                    for (Table.Column.Value value : get) {
                        value.fetch(resultSet, columnIndex++);
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

    private static String fullColumnNamesString(Table table, List<Table.Column<?>.Value> properties) throws DatabaseException {
        if (properties.size() > 0) {
            return properties.stream().map(Table.Column.Value::getColumn).map(it -> table.getName() + "." + it.getName()).collect(Collectors.joining(", "));
        } else {
            throw new DatabaseException("Expecting at least one column");
        }
    }

    private static String columnNamesString(List<Table.Column<?>.Value> properties) throws DatabaseException {
        if (properties.size() > 0) {
            return properties.stream().map(Table.Column.Value::getColumn).map(Table.Column::getName).collect(Collectors.joining(", "));
        } else {
            throw new DatabaseException("Expecting at least one column");
        }
    }

    private static String setColumnNamesString(List<Table.Column<?>.Value> properties) throws DatabaseException {
        if (properties.size() > 0) {
            return properties.stream().map(Table.Column.Value::getColumn).map(column -> column.getName() + " = ?").collect(Collectors.joining(", "));
        } else {
            throw new DatabaseException("Expecting at least one column");
        }
    }

    private static String withWhere(String statementString, List<Table.Column<?>.Value> properties) {
        if (properties.size() > 0) {
            return statementString + " WHERE " + properties.stream().map(Table.Column.Value::getColumn).map(Table.Column::getEqualsCondition).collect(Collectors.joining(" AND "));
        } else {
            return statementString;
        }
    }

    public final ResultSet select(Table table, List<Table.Column<?>.Value> get, List<Table.Column<?>.Value> where) throws DatabaseException {
        try {
            String statementString = withWhere("SELECT " + fullColumnNamesString(table, get) + " FROM " + table.getName(), where);
            System.out.println(statementString);
            PreparedStatement statement = connection.prepareStatement(statementString);
            int parameterIndex = 1;
            for (Table.Column.Value value : where) {
                value.prepare(statement, parameterIndex++);
            }
            return new ResultSet(statement.executeQuery(), get);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public final int update(Table table, List<Table.Column<?>.Value> set, List<Table.Column<?>.Value> where) throws DatabaseException {
        try {
            String statementString = withWhere("UPDATE " + table.getName() + " SET " + setColumnNamesString(set), where);
            System.out.println(statementString);
            PreparedStatement statement = connection.prepareStatement(statementString);
            int parameterIndex = 1;
            for (Table.Column.Value value : set) {
                value.prepare(statement, parameterIndex++);
            }
            for (Table.Column.Value value : where) {
                value.prepare(statement, parameterIndex++);
            }
            statement.execute();
            int result = statement.getUpdateCount();
            statement.close();
            return result;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public final int delete(Table table, List<Table.Column<?>.Value> where) throws DatabaseException {
        try {
            String statementString = withWhere("DELETE FROM " + table.getName(), where);
            System.out.println(statementString);
            PreparedStatement statement = connection.prepareStatement(statementString);
            int parameterIndex = 1;
            for (Table.Column.Value value : where) {
                value.prepare(statement, parameterIndex++);
            }
            statement.execute();
            int result = statement.getUpdateCount();
            statement.close();
            return result;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public final ResultSet insert(Table table, List<Table.Column<?>.Value> values, List<Table.Column<?>.Value> generated) throws DatabaseException {
        try {
            String valuesString = values.stream().map(value -> "?").collect(Collectors.joining(", "));
            String statementString = "INSERT INTO " + table.getName() + " " + "(" + columnNamesString(values) + ") VALUES (" + valuesString + ")";
            System.out.println(statementString);
            PreparedStatement statement = connection.prepareStatement(statementString);
            int parameterIndex = 1;
            for (Table.Column.Value value : values) {
                value.prepare(statement, parameterIndex++);
            }
            statement.executeUpdate();
            return new ResultSet(statement.getGeneratedKeys(), generated);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
