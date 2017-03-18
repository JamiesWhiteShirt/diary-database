package diary.contexts;

/*import diary.lib.StringMapper;
import diary.lib.context.Context;
import diary.lib.property.StringMappedProperty;
import diary.tables.WorkoutTable;
import diary.lib.database.Table;
import diary.lib.property.AbstractProperty;
import diary.lib.property.database.BoundColumnProperty;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class ViewWorkoutContext extends Context {
    public ViewWorkoutContext(WorkoutTable table, int id) {
        super("view " + id);

        List<Table.Column.Property> where = Collections.singletonList(table.id.property(id));
        AbstractProperty<Timestamp> time = new BoundColumnProperty<>("time", table.time, where);
        AbstractProperty<Integer> duration = new BoundColumnProperty<>("duration", table.duration, where);
        AbstractProperty<Integer> performanceRating = new BoundColumnProperty<>("performanceRating", table.performanceRating, where);
        AbstractProperty<String> notes = new BoundColumnProperty<>("notes", table.notes, where);

        addProperty(new StringMappedProperty<>(time, StringMapper.TIMESTAMP));
        addProperty(new StringMappedProperty<>(duration, StringMapper.INTEGER));
        addProperty(new StringMappedProperty<>(performanceRating, StringMapper.INTEGER));
        addProperty(notes);
    }
}*/
