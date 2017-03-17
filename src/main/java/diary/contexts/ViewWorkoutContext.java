package diary.contexts;

import diary.lib.context.Context;
import diary.tables.WorkoutTable;
import diary.lib.database.Table;
import diary.lib.property.AbstractProperty;
import diary.lib.property.database.BoundColumnProperty;
import diary.lib.property.wrapper.IntegerWrapperProperty;
import diary.lib.property.wrapper.TimestampWrapperProperty;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class ViewWorkoutContext extends Context {
    public ViewWorkoutContext(WorkoutTable table, int id) {
        super("view " + id);

        List<Table.Column.Property> where = Collections.singletonList(table.getId().property(id));
        AbstractProperty<Timestamp> time = new BoundColumnProperty<>("time", table.getTime(), where);
        AbstractProperty<Integer> duration = new BoundColumnProperty<>("duration", table.getDuration(), where);
        AbstractProperty<Integer> performanceRating = new BoundColumnProperty<>("performanceRating", table.getPerformanceRating(), where);
        AbstractProperty<String> notes = new BoundColumnProperty<>("notes", table.getNotes(), where);

        addProperty(new TimestampWrapperProperty(time));
        addProperty(new IntegerWrapperProperty(duration));
        addProperty(new IntegerWrapperProperty(performanceRating));
        addProperty(notes);
    }
}
