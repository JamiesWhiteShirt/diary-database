package diary.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProperty extends Property<Date> {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private Date value;

    public DateProperty(String name, Date value) {
        super(name);
        this.value = value;
    }

    @Override
    public Date getValue() {
        return value;
    }

    @Override
    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "Date";
    }

    @Override
    public void setStringValue(String value) throws PropertyValueException {
        if (value != null) {
            try {
                this.value = dateFormat.parse(value);
            } catch (ParseException e) {
                throw new PropertyValueException("Invalid date value");
            }
        } else {
            this.value = null;
        }
    }

    @Override
    public String getStringValue() {
        if (value != null) {
            return dateFormat.format(value);
        } else {
            return null;
        }
    }
}
