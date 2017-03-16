package diary.property.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimestampDatabaseProperty extends DatabaseProperty<Timestamp> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public TimestampDatabaseProperty(String name, String tableName, String columnName, String where) {
        super(Timestamp.class, name, tableName, columnName, where);
    }

    @Override
    protected void setValue(PreparedStatement statement, int parameterIndex, Timestamp value) throws SQLException {
        statement.setTimestamp(parameterIndex, value);
    }

    @Override
    protected Timestamp getValue(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getTimestamp(columnIndex);
    }
}
