package diary.property.wrapper;

import diary.property.AbstractProperty;
import diary.property.PropertyValueException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimestampWrapperProperty extends WrapperProperty<Timestamp> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public TimestampWrapperProperty(AbstractProperty<Timestamp> property) {
        super(property);
    }

    @Override
    public void setValue(String value) throws PropertyValueException {
        if (value != null) {
            try {
                getProperty().setValue(new Timestamp(dateFormat.parse(value).getTime()));
            } catch (ParseException e) {
                throw new PropertyValueException("Invalid timestamp value");
            }
        } else {
            getProperty().setValue(null);
        }
    }

    @Override
    public String getValue() {
        Timestamp value = getProperty().getValue();
        if (value != null) {
            return dateFormat.format(value);
        } else {
            return null;
        }
    }
}
