package diary.commandcontext;

import diary.property.AbstractProperty;
import diary.property.database.IntegerDatabaseProperty;
import diary.property.database.StringDatabaseProperty;
import diary.property.database.TimestampDatabaseProperty;
import diary.property.wrapper.IntegerWrapperProperty;
import diary.property.wrapper.TimestampWrapperProperty;

import java.sql.Timestamp;

public class ViewWorkoutContext extends Context {
    public ViewWorkoutContext(int id) {
        super("view " + id);
        String tableName = "completed_workout";
        String where = "id = \"" + id + "\"";
        AbstractProperty<Timestamp> time = new TimestampDatabaseProperty("time", tableName, "time", where);
        AbstractProperty<Integer> duration = new IntegerDatabaseProperty("duration", tableName, "duration", where);
        AbstractProperty<Integer> performanceRating = new IntegerDatabaseProperty("performanceRating", tableName, "performance_rating", where);
        AbstractProperty<String> notes = new StringDatabaseProperty("notes", tableName, "notes", where);
        addProperty(new TimestampWrapperProperty(time));
        addProperty(new IntegerWrapperProperty(duration));
        addProperty(new IntegerWrapperProperty(performanceRating));
        addProperty(notes);
    }
}
