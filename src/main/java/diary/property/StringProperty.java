package diary.property;

public class StringProperty extends Property<String> {
    private String value;

    public StringProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "String";
    }

    @Override
    public void setStringValue(String value) throws PropertyValueException {
        setValue(value);
    }

    @Override
    public String getStringValue() {
        return getValue();
    }
}
