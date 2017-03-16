package diary.property;

public abstract class Property {
    private final String name;

    public Property(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public abstract String getType();

    public abstract void setStringValue(String value) throws PropertyValueException;

    public abstract String getStringValue();

    public final String getDescription() {
        return getType() + " " + getName();
    }

    @Override
    public String toString() {
        String value = getStringValue();
        return getName() + " = " + (value != null ? value : "null");
    }
}
