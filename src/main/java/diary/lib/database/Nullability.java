package diary.lib.database;

public enum Nullability {
    NULLABLE {
        @Override
        public void assertValue(Object value) { }
    },
    NOT_NULL {
        @Override
        public void assertValue(Object value) throws NullabilityException {
            if (value == null) {
                throw new NullabilityException("Cannot be null");
            }
        }
    };

    public abstract void assertValue(Object value) throws NullabilityException;
}
