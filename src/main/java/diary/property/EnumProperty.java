package diary.property;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumProperty<T extends Enum<T>> extends Property<T> {
    private final Class<T> enumClass;
    private T value;

    public EnumProperty(Class<T> enumClass, String name, T value) {
        super(name);
        this.enumClass = enumClass;
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "Enum(" + Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).collect(Collectors.joining("|")) + ")";
    }

    @Override
    public void setStringValue(String value) throws PropertyValueException {
        if (value != null) {
            try {
                this.value = Enum.valueOf(enumClass, value);
            } catch (IllegalArgumentException e) {
                throw new PropertyValueException("Invalid value " + value);
            }
        } else {
            this.value = null;
        }
    }

    @Override
    public String getStringValue() {
        if (value != null) {
            return value.name();
        } else {
            return null;
        }
    }
}
