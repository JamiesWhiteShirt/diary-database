package diary.property.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumDatabaseProperty<T extends Enum<T>> extends DatabaseProperty<T> {
    public EnumDatabaseProperty(Class<T> type, String name, String tableName, String columnName, String where) {
        super(type, name, tableName, columnName, where);
    }

    @Override
    protected void setValue(PreparedStatement statement, int parameterIndex, T value) throws SQLException {
        statement.setString(parameterIndex, value.name());
    }

    @Override
    protected T getValue(ResultSet rs, int columnIndex) throws SQLException {
        return Enum.valueOf(getType(), rs.getString(columnIndex));
    }
}
