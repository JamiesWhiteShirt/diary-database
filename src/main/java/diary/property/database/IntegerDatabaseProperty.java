package diary.property.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IntegerDatabaseProperty extends DatabaseProperty<Integer> {
    public IntegerDatabaseProperty(String name, String tableName, String columnName, String where) {
        super(Integer.class, name, tableName, columnName, where);
    }

    @Override
    protected void setValue(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            statement.setInt(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.INTEGER);
        }
    }

    @Override
    protected Integer getValue(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        if (!rs.wasNull()) {
            return value;
        } else {
            return null;
        }
    }
}
