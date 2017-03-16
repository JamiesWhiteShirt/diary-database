package diary.property.wrapper;

import diary.property.AbstractProperty;
import diary.property.PropertyValueException;

public class IntegerWrapperProperty extends WrapperProperty<Integer> {
    public IntegerWrapperProperty(AbstractProperty<Integer> property) {
        super(property);
    }

    @Override
    public void setValue(String value) throws PropertyValueException {
        if (value != null) {
            try {
                getProperty().setValue(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new PropertyValueException("Invalid integer value");
            }
        } else {
            getProperty().setValue(null);
        }
    }

    @Override
    public String getValue() {
        Integer value = getProperty().getValue();
        if (value != null) {
            return Integer.toString(value);
        } else {
            return null;
        }
    }
}
