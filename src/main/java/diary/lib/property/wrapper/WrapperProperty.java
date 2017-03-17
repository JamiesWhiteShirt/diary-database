package diary.lib.property.wrapper;

import diary.lib.property.AbstractProperty;

public abstract class WrapperProperty<T> extends AbstractProperty<String> {
    private final AbstractProperty<T> property;

    public WrapperProperty(AbstractProperty<T> property) {
        super(String.class, property.getName());
        this.property = property;
    }

    protected AbstractProperty<T> getProperty() {
        return property;
    }
}
