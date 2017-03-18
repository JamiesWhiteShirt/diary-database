package diary.lib.property;

import diary.lib.StringMapper;

public final class StringMappedProperty<T> extends AbstractProperty<String> {
    private final AbstractProperty<T> property;
    private final StringMapper<T> mapper;

    public StringMappedProperty(AbstractProperty<T> property, StringMapper<T> mapper) {
        super(String.class, property.getName());
        this.property = property;
        this.mapper = mapper;
    }

    @Override
    public void setValue(String value) throws PropertyValueException {
        if (value != null) {
            try {
                property.setValue(mapper.fromString(value));
            } catch (StringMapperException e) {
                throw new PropertyValueException(e.getMessage());
            }
        } else {
            property.setValue(null);
        }
    }

    @Override
    public String getValue() {
        T value = property.getValue();
        if (value != null) {
            return mapper.toString(value);
        } else {
            return null;
        }
    }
}
