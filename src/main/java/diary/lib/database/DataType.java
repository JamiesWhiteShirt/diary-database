package diary.lib.database;

import java.sql.*;

public abstract class DataType<T> {
    public static final DataType<Integer> INTEGER = new DataType<Integer>(Integer.class) {
        @Override
        public void prepare(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
            if (value != null) {
                statement.setInt(parameterIndex, value);
            } else {
                statement.setNull(parameterIndex, Types.INTEGER);
            }
        }

        @Override
        public Integer fetch(ResultSet rs, int columnIndex) throws SQLException {
            int value = rs.getInt(columnIndex);
            if (!rs.wasNull()) {
                return value;
            } else {
                return null;
            }
        }
    };

    public static final DataType<String> STRING = new DataType<String>(String.class) {
        @Override
        public void prepare(PreparedStatement statement, int parameterIndex, String value) throws SQLException {
            statement.setString(parameterIndex, value);
        }

        @Override
        public String fetch(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getString(columnIndex);
        }
    };

    public static final DataType<Timestamp> TIMESTAMP = new DataType<Timestamp>(Timestamp.class) {
        @Override
        public void prepare(PreparedStatement statement, int parameterIndex, Timestamp value) throws SQLException {
            statement.setTimestamp(parameterIndex, value);
        }

        @Override
        public Timestamp fetch(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getTimestamp(columnIndex);
        }
    };

    private final Class<T> type;

    private DataType(Class<T> type) {
        this.type = type;
    }

    public abstract void prepare(PreparedStatement statement, int parameterIndex, T value) throws SQLException;

    public abstract T fetch(ResultSet rs, int columnIndex) throws SQLException;

    public Class<T> getType() {
        return type;
    }
}
