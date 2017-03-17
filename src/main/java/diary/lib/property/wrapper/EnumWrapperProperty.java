package diary.lib.property.wrapper;

import diary.lib.property.AbstractProperty;
import diary.lib.property.PropertyValueException;

public class EnumWrapperProperty<T extends Enum<T>> extends WrapperProperty<T> {
    public EnumWrapperProperty(AbstractProperty<T> property) {
        super(property);
    }

    @Override
    public void setValue(String value) throws PropertyValueException {
        if (value != null) {
            try {
                getProperty().setValue(Enum.valueOf(getProperty().getType(), value));
            } catch (IllegalArgumentException e) {
                throw new PropertyValueException("Invalid value " + value);
            }
        } else {
            getProperty().setValue(null);
        }
    }

    @Override
    public String getValue() {
        T value = getProperty().getValue();
        if (value != null) {
            return value.name();
        } else {
            return null;
        }
    }
}
