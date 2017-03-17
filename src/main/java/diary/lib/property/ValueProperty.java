package diary.lib.property;

public class ValueProperty<T> extends AbstractProperty<T> {
    private T value;

    public ValueProperty(Class<T> type, String name, T value) {
        super(type, name);
        this.value = value;
    }

    @Override
    public void setValue(T value) throws PropertyValueException {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
