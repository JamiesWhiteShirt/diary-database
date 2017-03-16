package diary.property.database;

import diary.Application;
import diary.property.AbstractProperty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseProperty<T> extends AbstractProperty<T> {
    private final String tableName;
    private final String columnName;
    private final String where;

    public DatabaseProperty(Class<T> type, String name, String tableName, String columnName, String where) {
        super(type, name);
        this.tableName = tableName;
        this.columnName = columnName;
        this.where = where;
    }

    @Override
    public final void setValue(T value) {
        try {
            PreparedStatement statement = Application.INSTANCE.getConnection().prepareStatement(
                    "UPDATE " + tableName + " SET " + columnName + " = ? " +
                    "WHERE " + where
            );
            setValue(statement, 1, value);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    protected abstract void setValue(PreparedStatement statement, int parameterIndex, T value) throws SQLException;

    @Override
    public final T getValue() {
        T result = null;
        try {
            Statement statement = Application.INSTANCE.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT " + columnName + " FROM " + tableName + " " +
                    "WHERE " + where
            );
            if (rs.next()) {
                result = getValue(rs, 1);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

        return result;
    }

    protected abstract T getValue(ResultSet rs, int columnIndex) throws SQLException;
}
