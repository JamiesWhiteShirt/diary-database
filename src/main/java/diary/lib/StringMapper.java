package diary.lib;

import diary.lib.property.StringMapperException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class StringMapper<T> {
    public static final StringMapper<Integer> INTEGER = new StringMapper<Integer>() {
        @Override
        public Integer fromString(String string) throws StringMapperException {
            try {
                return Integer.valueOf(string);
            } catch (NumberFormatException e) {
                throw new StringMapperException("Invalid number format");
            }
        }

        @Override
        public String toString(Integer value) {
            return Integer.toString(value);
        }
    };

    public static final StringMapper<Timestamp> TIMESTAMP = new StringMapper<Timestamp>() {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        @Override
        public Timestamp fromString(String string) throws StringMapperException {
            try {
                return new Timestamp(dateFormat.parse(string).getTime());
            } catch (ParseException e) {
                throw new StringMapperException("Timestamp must be in the format dd.MM.yyyy HH:mm");
            }
        }

        @Override
        public String toString(Timestamp value) {
            return dateFormat.format(value);
        }
    };

    public static final StringMapper<String> STRING = new StringMapper<String>() {
        @Override
        public String fromString(String string) throws StringMapperException {
            return string;
        }

        @Override
        public String toString(String value) {
            return value;
        }
    };

    public static <U extends Enum<U>> StringMapper<U> forEnum(Class<U> enumClass) {
        return new StringMapper<U>() {
            @Override
            public U fromString(String string) throws StringMapperException {
                try {
                    return Enum.valueOf(enumClass, string);
                } catch (IllegalArgumentException e) {
                    throw new StringMapperException("Invalid enum value");
                }
            }

            @Override
            public String toString(U value) {
                return value.name();
            }
        };
    }

    private StringMapper() { }

    public abstract T fromString(String string) throws StringMapperException;

    public abstract String toString(T value);
}
