package diary.property;

public class IntegerProperty extends Property {
    private Integer value;

    public IntegerProperty(String name, Integer value) {
        super(name);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "Integer";
    }

    @Override
    public void setStringValue(String value) throws PropertyValueException {
        if (value != null) {
            try {
                this.value = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                throw new PropertyValueException("Invalid integer value");
            }
        } else {
            this.value = null;
        }
    }

    @Override
    public String getStringValue() {
        if (value != null) {
            return Integer.toString(value);
        } else {
            return null;
        }
    }
}
