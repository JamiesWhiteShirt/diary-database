package diary;

public enum Nullability {
    NULLABLE {
        @Override
        public void assertValue(Object value) { }
    },
    NOT_NULL {
        @Override
        public void assertValue(Object value) {
            if (value == null) {
                throw new IllegalArgumentException("Cannot be null");
            }
        }
    };

    public abstract void assertValue(Object value);
}
