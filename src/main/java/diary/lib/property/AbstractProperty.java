package diary.lib.property;

public abstract class AbstractProperty<T> {
    private final Class<T> type;
    private final String name;

    public AbstractProperty(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }

    public Class<T> getType() {
        return type;
    }

    public final String getName() {
        return name;
    }

    public abstract void setValue(T value) throws PropertyValueException;

    public abstract T getValue();

    public final String getDescription() {
        return getType().getSimpleName() + " " + getName();
    }

    @Override
    public String toString() {
        T value = getValue();
        return getName() + " = " + (value != null ? value.toString() : "null");
    }
}
