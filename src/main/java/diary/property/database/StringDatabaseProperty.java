package diary.property.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringDatabaseProperty extends DatabaseProperty<String> {
    public StringDatabaseProperty(String name, String tableName, String columnName, String where) {
        super(String.class, name, tableName, columnName, where);
    }

    @Override
    protected void setValue(PreparedStatement statement, int parameterIndex, String value) throws SQLException {
        statement.setString(parameterIndex, value);
    }

    @Override
    protected String getValue(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }
}
